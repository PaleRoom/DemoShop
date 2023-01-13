package ru.ncs.DemoShop.service.idempotence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.repository.RedisRepository;

@Component
@AllArgsConstructor
public class IdempotenceServiceImpl implements IdempotenceService {
    private final RedisRepository redisRepository;

    @Override
    public String addIdpKey(String idpKey, String orderId) {
        return redisRepository.addIdpKey(idpKey, orderId);
    }

    @Override
    public String getIdpKey(String id) {
        return redisRepository.getIdpValue(id);
    }
}
