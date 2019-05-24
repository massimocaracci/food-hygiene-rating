package uk.co.pantasoft.fhr.client.Ratings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FSARatingListResponse {

    @JsonProperty("ratings")
    private List<FSARatingItemResponse> ratings;

    public List<FSARatingItemResponse> getRatings() {
        return ratings;
    }

    public void setRatings(List<FSARatingItemResponse> ratings) {
        this.ratings = ratings;
    }
}
