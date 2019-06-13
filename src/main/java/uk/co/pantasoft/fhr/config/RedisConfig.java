package uk.co.pantasoft.fhr.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import uk.co.pantasoft.fhr.client.authorities.FSAAuthorityList;
import uk.co.pantasoft.fhr.service.model.Authority;
import uk.co.pantasoft.fhr.service.model.AuthorityRatingItem;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration());
    }

    @Bean
    RedisTemplate<String, AuthorityRatingItem> redisTemplate() {

        var redisTemplate = new RedisTemplate<String, AuthorityRatingItem>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
