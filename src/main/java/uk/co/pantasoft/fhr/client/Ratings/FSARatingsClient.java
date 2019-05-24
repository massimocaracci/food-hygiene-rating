package uk.co.pantasoft.fhr.client.Ratings;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import uk.co.pantasoft.fhr.client.config.FSAFeignConfiguration;

@FeignClient(
        decode404 = true,
        name = "fhr-ratings-api",
        url = "${service.client.foodhygieneRating.uri}",
        configuration = FSAFeignConfiguration.class)
public interface FSARatingsClient {

    @GetMapping("Ratings")
    ResponseEntity<FSARatingListResponse> retrieveRatings(@RequestHeader("x-api-version") int apiVersion);
}
