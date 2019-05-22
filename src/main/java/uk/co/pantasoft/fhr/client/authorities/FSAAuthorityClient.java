package uk.co.pantasoft.fhr.client.authorities;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import uk.co.pantasoft.fhr.client.config.FSAFeignConfiguration;

@FeignClient(
        decode404 = true,
        name = "fhr-authority-api",
        url = "${service.client.foodhygieneRating.uri}",
        configuration = FSAFeignConfiguration.class)
public interface FSAAuthorityClient {

    @GetMapping("authorities")
    ResponseEntity<FSAAuthorityList> retrieveAuthorities(@RequestHeader("x-api-version") int apiVersion);
}
