package com.namnx.spring_redis.service;

import com.namnx.spring_redis.enums.RedisKey;
import com.namnx.spring_redis.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserCacheService {

    @Autowired
    private RedisCacheUtils<UserEntity, RedisTemplate<String, UserEntity>> cacheUtils;

    private static final List<UserEntity> userEntities = new ArrayList<>();

    @PostConstruct
    void mockData() {
        UserEntity user1 = new UserEntity(1L, "nam@gmail.com", "nam nx", "2020/12/10");
        UserEntity user2 = new UserEntity(2L, "nam2@gmail.com", "cuong nx", "2010/06/15");
        userEntities.add(user1);
        userEntities.add(user2);
    }

    public void buildCacheAllDataUser() {
        cacheUtils.buildCacheAllData(userEntities, RedisKey.REDIS_USER_ID_CACHE, UserEntity::getId);
        cacheUtils.buildCacheAllData(userEntities, RedisKey.REDIS_USER_EMAIL_CACHE, UserEntity::getEmail);
        log.info("build cache all data users with size: {} successfully", userEntities.size());
    }

    public Collection<UserEntity> getAllFromCacheKeyId() {
        return cacheUtils.getAllFromCache(RedisKey.REDIS_USER_ID_CACHE);
    }

    public Collection<UserEntity> getAllFromCacheKeyEmail() {
        return cacheUtils.getAllFromCache(RedisKey.REDIS_USER_EMAIL_CACHE);
    }

    public UserEntity getByIdFromCache(Long userId) {
        return cacheUtils.findBySpecialKey(RedisKey.REDIS_USER_ID_CACHE, userId);
    }

    public UserEntity getByEmailFromCache(String email) {
        return cacheUtils.findBySpecialKey(RedisKey.REDIS_USER_EMAIL_CACHE, email);
    }

    public void deleteByEmailFromCache(String email) {
        cacheUtils.deleteFromCache(RedisKey.REDIS_USER_EMAIL_CACHE, email);
    }

    public void updateCacheUser(UserEntity userEntity) {
        cacheUtils.updateCache(RedisKey.REDIS_USER_EMAIL_CACHE, userEntity, UserEntity::getEmail);
        cacheUtils.updateCache(RedisKey.REDIS_USER_ID_CACHE, userEntity, UserEntity::getId);
    }
}
