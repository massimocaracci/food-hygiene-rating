package uk.co.pantasoft.fhr.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.pantasoft.fhr.service.RatingService;
import uk.co.pantasoft.fhr.service.model.Authority;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RatingResource {

    private static Logger LOGGER = LoggerFactory.getLogger(RatingResource.class);

    private RatingService ratingService;

    public RatingResource(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("")
    public ResponseEntity retrieveAuthorities() {

        List<Authority> authorities = ratingService.retrieveAuthorities();

        return ResponseEntity.ok(authorities);
    }

    @GetMapping("/{authorityId}")
    public ResponseEntity retrieveAuthority(@PathVariable("authorityId") int authorityId) {

        var res = ratingService.retrieveAuthorityRatings(authorityId);

        return ResponseEntity.ok(res);
    }
}
