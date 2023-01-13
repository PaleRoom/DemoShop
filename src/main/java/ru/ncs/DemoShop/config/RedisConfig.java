package ru.ncs.DemoShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import ru.ncs.DemoShop.service.idempotence.Idempotent;

@Configuration
public class RedisConfig {
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);

        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Idempotent> redisTemplate() {
        final RedisTemplate<String, Idempotent> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        return template;
    }

    @Bean
    public HashOperations<String, String, String> hashOperations(RedisTemplate<String, Idempotent> redisTemplate) {
        return redisTemplate.opsForHash();
    }
}
