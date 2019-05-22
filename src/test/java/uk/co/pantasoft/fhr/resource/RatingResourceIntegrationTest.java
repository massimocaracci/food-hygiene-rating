package uk.co.pantasoft.fhr.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RatingResourceIntegrationTest {

    static final Logger LOGGER = LoggerFactory.getLogger(RatingResourceIntegrationTest.class);

    private final static String CONTENT_TYPE_UTF8 = "application/json;charset=UTF-8";


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private Faker faker = new Faker();

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }


    @Test
    public void retrieveAuthorities() throws Exception{

        mvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE_UTF8))
                .andExpect(jsonPath("$",  Matchers.is(not(empty()))))
                .andExpect(jsonPath("$[0].id",  Matchers.is(not(empty()))))
                .andExpect(jsonPath("$[0].name", Matchers.is(not(empty()))));
    }

    @Test
    public void retrieveAuthority() {
    }

//    @Test
//    public void addPass() throws Exception {
//
//        var customerId = givenCustomer();
//
//        var addPass = new AddPassRequest();
//        addPass.setVendorId("V-1234");
//        addPass.setPassLength(7);
//        addPass.setPassCity("Berlin");
//        addPass.setPassId("P-1234");
//
//        var requestJson = objectMapper.writeValueAsString(addPass);
//        mvc.perform(post("/customer/" + customerId + "/pass/add")
//                .content(requestJson)
//                .contentType(CONTENT_TYPE_UTF8))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(CONTENT_TYPE_UTF8))
//                .andExpect(jsonPath("$.id", Matchers.is(not(empty()))))
//                .andExpect(jsonPath("$.city", Matchers.is("Berlin")))
//                .andExpect(jsonPath("$.createdDate", Matchers.is(not(empty()))))
//                .andExpect(jsonPath("$.passRefId", Matchers.is("P-1234")))
//                .andExpect(jsonPath("$.vendorId", Matchers.is("V-1234")))
//                .andExpect(jsonPath("$.vendorValidated", Matchers.is(false)))
//                .andExpect(jsonPath("$.cancelled", Matchers.is(false)))
//                .andExpect(jsonPath("$.length", Matchers.is(7)));
//    }

}