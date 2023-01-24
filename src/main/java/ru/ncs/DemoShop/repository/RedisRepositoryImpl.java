package ru.ncs.DemoShop.repository;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.ncs.DemoShop.service.idempotence.Idempotent;

@Repository
@Component
@AllArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
    private RedisTemplate<String, Idempotent> redisTemplate;
    private HashOperations hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
    }

    @Override
    public String addIdpKey(String idpKey, String orderId) {
        redisTemplate.opsForHash().put("IDP", idpKey, orderId);
        redisTemplate.expire("IDP", 30, TimeUnit.SECONDS);
        hashOperations.put("IDP", idpKey, orderId);
        return orderId;
    }

    @Override
    public String getIdpValue(String id) {
        return (String) hashOperations.get("IDP", id);
    }
}
