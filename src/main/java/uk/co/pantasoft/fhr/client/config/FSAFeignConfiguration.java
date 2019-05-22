package uk.co.pantasoft.fhr.client.config;

import feign.FeignException;
import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextListener;

import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class FSAFeignConfiguration {

    static final Logger LOGGER = LoggerFactory.getLogger(FSAFeignConfiguration.class);

    @Bean
    @Order(0)
    public RequestContextListener requestContextListener() {

        return new RequestContextListener();
    }

    @Bean
    public Retryer feignRetryer() {

        return new Retryer.Default(100, SECONDS.toMillis(1), 2);
    }

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> {
            // Do what you want to do
            LOGGER.info("Do what you want to do");
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {

            if (HttpStatus.BAD_REQUEST.value() == response.status()) {
                LOGGER.error("BAD_REQUEST found");
                return new RetryableException("Feign Auth 400 detected. will retry", null, new Date());
            }

            if (HttpStatus.UNAUTHORIZED.value() == response.status()) {

                LOGGER.error("UNAUTHORIZED found");
                return new RetryableException("Feign Auth 401 detected. will retry", null, new Date());
            }
            return FeignException.errorStatus(methodKey, response);
        };
    }
}
