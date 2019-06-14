package uk.co.pantasoft.fhr.config;

import io.lettuce.core.ReadFrom;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import static java.lang.Integer.parseInt;

@Configuration
@EnableCaching
public class RedisConfig {

    private Environment env;

    public RedisConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        var clientConfig = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.SLAVE_PREFERRED)
                .build();

        var serverConfig = new RedisStandaloneConfiguration(
                env.getProperty("server.redis.host"),
                parseInt(env.getProperty("server.redis.port")));

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {

        var redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
