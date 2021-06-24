package com.namnx.spring_redis.service.cache;

import com.namnx.spring_redis.enums.RedisCacheKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Function;

@Component
@Slf4j
public class RedisCacheUtils<E, R extends RedisAccessor & RedisOperations<String, E>> {

    @Autowired
    private R redisTemplate;

    public <HK> void buildCacheAllData(Collection<E> entities,
                                       RedisCacheKey redisCacheKey,
                                       Function<E, HK> funcGetHashKey) {
        redisTemplate.delete(redisCacheKey.toString());
        for (E entity : entities) {
            redisTemplate
                    .opsForHash()
                    .put(redisCacheKey.toString(), funcGetHashKey.apply(entity).toString(), entity);
        }
    }

    public Collection<E> getAllFromCache(RedisCacheKey redisCacheKey) {
        HashOperations<String, String, E> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(redisCacheKey.toString());
    }

    public <HK> E findBySpecialKey(RedisCacheKey redisCacheKey, HK hashKey,
                                   Function<HK, E> funcGetObjFromDB) {
        HashOperations<String, String, E> hashOperations = redisTemplate.opsForHash();
        E e = hashOperations.get(redisCacheKey.toString(), hashKey.toString());

        if (e == null) {
            log.info("data with cache key: {}, hashKey: {} return null.", redisCacheKey, hashKey);
            //when not stored in cache, start to find in DB and update cache
            e = funcGetObjFromDB.apply(hashKey);
            if (e != null) {
                log.info("update cache with data from DB. cache key: {}, hashKey: {}", redisCacheKey, hashKey);
                hashOperations.put(redisCacheKey.toString(), hashKey.toString(), e);
            }
        }
        return e;
    }

    public <HK> void deleteFromCache(RedisCacheKey redisCacheKey, HK hashKey) {
        redisTemplate
                .opsForHash()
                .delete(redisCacheKey.toString(), hashKey.toString());
    }

    public <HK> void deleteFromCache(RedisCacheKey redisCacheKey, Collection<HK> hashKeysToDelete) {
        redisTemplate
                .opsForHash()
                .delete(redisCacheKey.toString(),
                        hashKeysToDelete
                                .stream()
                                .map(Object::toString).toArray()
                );
    }

    public <HK> void updateCache(RedisCacheKey redisCacheKey, E e, Function<E, HK> funcGetHashKey) {
        redisTemplate
                .opsForHash()
                .put(redisCacheKey.toString(), funcGetHashKey.apply(e).toString(), e);
    }
}
