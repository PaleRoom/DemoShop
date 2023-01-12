package ru.ncs.DemoShop.repository;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.ncs.DemoShop.service.Idempotent;

@Repository
@Component
@AllArgsConstructor
public class RedisRepositoryImpl implements RedisRepository{
    private static final String KEY = "IDP";
    private  RedisTemplate<String, Idempotent> redisTemplate;
    private  HashOperations hashOperations;

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);

    }

    @Override
    public void addIdpKey(String idpKey, String orderId) {
     redisTemplate.opsForHash().put(KEY,idpKey,orderId);
     redisTemplate.expire(KEY,30, TimeUnit.SECONDS);
        hashOperations.put(KEY,idpKey,orderId);

    }

    @Override
    public void deleteIdpKey(String id) {
        hashOperations.delete(KEY, id);
    }

    @Override
    public String getIdpKey(String id) {
         return (String) hashOperations.get(KEY, id);
    }
}
