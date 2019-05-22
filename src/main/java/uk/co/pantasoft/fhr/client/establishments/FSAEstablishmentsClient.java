package uk.co.pantasoft.fhr.client.establishments;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import uk.co.pantasoft.fhr.client.config.FSAFeignConfiguration;


@FeignClient(
        decode404 = true,
        name = "fhr-establishments-api",
        url = "${service.client.foodhygieneRating.uri}",
        configuration = FSAFeignConfiguration.class)
public interface FSAEstablishmentsClient {

    @GetMapping("Establishments?localAuthorityId={authorityId}")
    ResponseEntity<FSAEstablishments> retrieveEstablishmentsByAuthorityId(
            @RequestHeader("x-api-version") int apiVersion,
            @PathVariable("authorityId") int authorityId);
}
