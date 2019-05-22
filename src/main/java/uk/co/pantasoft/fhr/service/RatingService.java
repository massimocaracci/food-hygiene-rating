package uk.co.pantasoft.fhr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.co.pantasoft.fhr.client.FoodHygieneRatingApi;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthority;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthorityList;
import uk.co.pantasoft.fhr.client.establishments.FSAEstablishmentItem;
import uk.co.pantasoft.fhr.client.establishments.FSAEstablishments;
import uk.co.pantasoft.fhr.service.model.Authority;
import uk.co.pantasoft.fhr.service.model.AuthorityRatingItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    static final Logger LOGGER = LoggerFactory.getLogger(RatingService.class);

    FoodHygieneRatingApi client;

    public RatingService(FoodHygieneRatingApi client) {
        this.client = client;
    }

    public List<Authority> retrieveAuthorities() {

        FSAAuthorityList authorities = client.retrieveAuthorities();

        return
                authorities.getFsaAuthorityList().stream()
                        .map((FSAAuthority a) -> new Authority(a.getId(), a.getName()))
                        .collect(Collectors.toList());
    }

    public List<AuthorityRatingItem> retrieveAuthorityRatings(int authorityId) {

        FSAEstablishments restRes = client.retrieveEstablishmentRatingsByAutority(authorityId);

        var processorStart = System.currentTimeMillis();

        var totRatings = restRes.getEstablishments().size();

        var rating5 = getTotRating(restRes, 5);
        LOGGER.info("Tot 5-star: {}", rating5);
        var rating4 = getTotRating(restRes, 4);
        LOGGER.info("Tot 4-star: {}", rating4);
        var rating3 = getTotRating(restRes, 3);
        LOGGER.info("Tot 3-star: {}", rating3);
        var rating2 = getTotRating(restRes, 2);
        LOGGER.info("Tot 2-star: {}", rating2);
        var rating1 = getTotRating(restRes, 1);
        LOGGER.info("Tot 1-star: {}", rating1);

        var ratingExempt = restRes.getEstablishments().stream()
                .map(e -> e.getRatingValue())
                .filter(p -> p.equalsIgnoreCase("exempt"))
                .count();
        LOGGER.info("Tot ratingExempt: {}", ratingExempt);

        var ratingOthers = totRatings - (rating5 + rating4 + rating3 + rating2 + rating1 + ratingExempt);
        LOGGER.info("Other ratings: {}", ratingOthers);
        LOGGER.debug("Total ratings check: {} = {}", totRatings, rating5 + rating4 + rating3 + rating2 + rating1 + ratingExempt + ratingOthers);


        LOGGER.info("Data Processing Time : {} milliseconds.", System.currentTimeMillis() - processorStart);

        return
                Arrays.asList(
                        new AuthorityRatingItem("5-star", (rating5 * 100) / totRatings),
                        new AuthorityRatingItem("4-star", (rating4 * 100) / totRatings),
                        new AuthorityRatingItem("3-star", (rating3 * 100) / totRatings),
                        new AuthorityRatingItem("2-star", (rating2 * 100) / totRatings),
                        new AuthorityRatingItem("1-star", (rating1 * 100) / totRatings),
                        new AuthorityRatingItem("Exempt", ((ratingExempt + ratingOthers) * 100) / totRatings)
                );
    }

    private long getTotRating(FSAEstablishments establishments, int starsRating) {
        return establishments.getEstablishments().stream()
                .filter((e) -> e.getRatingValue().matches("\\d+"))  // Only select Digits
                .mapToInt((FSAEstablishmentItem e) -> Integer.parseInt(e.getRatingValue()))  // Convert to Integers
                .filter(r -> r == starsRating)
                .count();
    }
}
