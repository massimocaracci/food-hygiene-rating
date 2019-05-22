package uk.co.pantasoft.fhr.client.establishments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FSAEstablishmentItem {

    @JsonProperty("RatingValue")
    private String ratingValue;

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }
}
