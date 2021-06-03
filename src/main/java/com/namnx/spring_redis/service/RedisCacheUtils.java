package com.namnx.spring_redis.service;

import com.namnx.spring_redis.enums.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Function;

@Component
public class RedisCacheUtils<E, R extends RedisAccessor & RedisOperations<String, E>> {

    @Autowired
    private R redisTemplate;

    public <HK> void buildCacheAllData(Collection<E> entities,
                                       RedisKey redisKey,
                                       Function<E, HK> funcGetHashKey) {
        redisTemplate.delete(redisKey.toString());
        for (E entity : entities) {
            redisTemplate
                    .opsForHash()
                    .put(redisKey.toString(), funcGetHashKey.apply(entity).toString(), entity);
        }
    }

    public Collection<E> getAllFromCache(RedisKey redisKey) {
        HashOperations<String, String, E> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(redisKey.toString());
    }

    public <HK> E findBySpecialKey(RedisKey redisKey, HK hashKey) {
        HashOperations<String, String, E> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(redisKey.toString(), hashKey.toString());
    }

    public <HK> void deleteFromCache(RedisKey redisKey, HK hashKey) {
        redisTemplate
                .opsForHash()
                .delete(redisKey.toString(), hashKey.toString());
    }

    public <HK> void deleteFromCache(RedisKey redisKey, Collection<HK> hashKeysToDelete) {
        redisTemplate
                .opsForHash()
                .delete(redisKey.toString(),
                        hashKeysToDelete
                                .stream()
                                .map(Object::toString).toArray()
                );
    }

    public <HK> void updateCache(RedisKey redisKey, E e, Function<E, HK> funcGetHashKey) {
        redisTemplate
                .opsForHash()
                .put(redisKey.toString(), funcGetHashKey.apply(e).toString(), e);
    }
}
