package uk.co.pantasoft.fhr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uk.co.pantasoft.fhr.client.FoodHygieneRatingApi;
import uk.co.pantasoft.fhr.client.Ratings.FSARatingItemResponse;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthority;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthorityList;
import uk.co.pantasoft.fhr.client.establishments.FSAEstablishmentItem;
import uk.co.pantasoft.fhr.client.establishments.FSAEstablishments;
import uk.co.pantasoft.fhr.service.model.Authority;
import uk.co.pantasoft.fhr.service.model.AuthorityRatingItem;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    static final Logger LOGGER = LoggerFactory.getLogger(RatingService.class);

    private FoodHygieneRatingApi client;

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

    @Cacheable(value = "authorityRatings", key = "#authorityId")
    public List<AuthorityRatingItem> retrieveAuthorityRatings(int authorityId) {

        FSAEstablishments restRes = client.retrieveEstablishmentRatingsByAutority(authorityId);

        return client.retrieveRatings().getRatings().stream()
                .map((FSARatingItemResponse r) -> new AuthorityRatingItem(r.getRatingName(), ratingPercentage(restRes, r.getRatingName())))
                .collect(Collectors.toList());
    }

    private long getTotRating(FSAEstablishments establishments, int starsRating) {
        return establishments.getEstablishments().stream()
                .filter((e) -> e.getRatingValue().matches("\\d+"))  // Only select Digits
                .mapToInt((FSAEstablishmentItem e) -> Integer.parseInt(e.getRatingValue()))  // Convert to Integers
                .filter(r -> r == starsRating)
                .count();
    }

    private long ratingPercentage(FSAEstablishments establishments, String ratingName) {

        var ratingSize = ratingTypeCount(establishments, ratingName);

        var establishmentSize = establishments.getEstablishments().size();

        LOGGER.debug("Rating - {}: {} ", ratingName, ratingSize);

        var percentage = (ratingSize * 100) / establishments.getEstablishments().size();

        LOGGER.debug("(ratingSize:{} * 100) / establishmentSize:{}", ratingSize, establishmentSize);


        return percentage;
    }

    private long ratingTypeCount(FSAEstablishments establishments, String ratingName) {
        return establishments.getEstablishments().stream()
                .map(FSAEstablishmentItem::getRatingValue) // Convert to Integers
                .filter(e -> e.equalsIgnoreCase(ratingName))
                .count();
    }
}
