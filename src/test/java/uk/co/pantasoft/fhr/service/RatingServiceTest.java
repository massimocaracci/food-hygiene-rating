package uk.co.pantasoft.fhr.service;

import com.github.javafaker.Faker;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.pantasoft.fhr.client.FoodHygieneRatingApi;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthority;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthorityList;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore("Cache test")
public class RatingServiceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private RatingService ratingService;

    @MockBean
    private FoodHygieneRatingApi client;


    private Faker faker = new Faker();

    @Test
    public void retrieveAuthorities_valid_successfulRetrieved() {

        var a = givenAuthorities();

        when(client.retrieveAuthorities())
                .thenReturn(a);

        var response = ratingService.retrieveAuthorities();

        assertThat(!response.isEmpty(), is(not(a.getFsaAuthorityList().isEmpty())));
//        assertThat(response.size(), is(a.getFsaAuthorityList().size()));
//        assertThat(response.get(0).getId(), is(a.getFsaAuthorityList().get(0).getId()));
//        assertThat(response.get(0).getName(), is(a.getFsaAuthorityList().get(0).getName()));
    }

    private FSAAuthorityList givenAuthorities() {

        var authority = new FSAAuthority();
        authority.setId(faker.number().randomDigit());
        authority.setName(faker.company().name());

        return new FSAAuthorityList(List.of(authority));
    }
}