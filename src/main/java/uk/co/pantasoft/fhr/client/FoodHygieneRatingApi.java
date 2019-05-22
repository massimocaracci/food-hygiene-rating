package uk.co.pantasoft.fhr.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthorityClient;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthorityList;
import uk.co.pantasoft.fhr.client.establishments.FSAEstablishments;
import uk.co.pantasoft.fhr.client.establishments.FSAEstablishmentsClient;

@Service
public class FoodHygieneRatingApi {

    private static final int API_VERSION = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodHygieneRatingApi.class);
    private FSAAuthorityClient authorityClient;
    private FSAEstablishmentsClient establishmentsClient;

    public FoodHygieneRatingApi(FSAAuthorityClient authorityClient, FSAEstablishmentsClient establishmentsClient) {
        this.authorityClient = authorityClient;
        this.establishmentsClient = establishmentsClient;
    }

    public FSAAuthorityList retrieveAuthorities() {

        LOGGER.info("Retrieving Authorities...");
        var res = authorityClient.retrieveAuthorities(API_VERSION);

        LOGGER.info("res Status Code: {}", res.getStatusCode());

        return res.getBody();
    }

    public FSAEstablishments retrieveEstablishmentRatingsByAutority(int authorityId) {

        LOGGER.info("Calling the API to retrieve establishment rating given the AuthorityId: {}", authorityId);
        var timeStart = System.currentTimeMillis();
        var res = establishmentsClient.retrieveEstablishmentsByAuthorityId(API_VERSION, authorityId);
        LOGGER.info("API Processing Time : {} seconds.", (System.currentTimeMillis() - timeStart)/1000);
        return res.getBody();
    }
}
