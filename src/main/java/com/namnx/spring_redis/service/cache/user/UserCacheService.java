package com.namnx.spring_redis.service.cache.user;

import com.namnx.spring_redis.enums.RedisCacheKey;
import com.namnx.spring_redis.model.UserEntity;
import com.namnx.spring_redis.repository.UserRepository;
import com.namnx.spring_redis.service.cache.RedisCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserCacheService {

    @Autowired
    private RedisCacheUtils<UserEntity, RedisTemplate<String, UserEntity>> cacheUtils;

    @Autowired
    private UserRepository userRepository;

    private static final List<UserEntity> userEntities = new ArrayList<>();

    @PostConstruct
    void mockData() {
        UserEntity user1 = new UserEntity(1L, "nam@gmail.com", "nam nx", new Date("2020/12/10"));
        UserEntity user2 = new UserEntity(2L, "nam2@gmail.com", "cuong nx", new Date("2010/06/15"));
        userEntities.add(user1);
        userEntities.add(user2);
    }

    public void buildCacheAllDataUser() {
        cacheUtils.buildCacheAllData(userEntities, RedisCacheKey.REDIS_USER_ID_CACHE, UserEntity::getId);
        cacheUtils.buildCacheAllData(userEntities, RedisCacheKey.REDIS_USER_EMAIL_CACHE, UserEntity::getEmail);
        log.info("build cache all data users with size: {} successfully", userEntities.size());
    }

    public Collection<UserEntity> getAllFromCacheKeyId() {
        return cacheUtils.getAllFromCache(RedisCacheKey.REDIS_USER_ID_CACHE);
    }

    public Collection<UserEntity> getAllFromCacheKeyEmail() {
        return cacheUtils.getAllFromCache(RedisCacheKey.REDIS_USER_EMAIL_CACHE);
    }

    public UserEntity getByIdFromCache(Long userId) {
        return cacheUtils.findBySpecialKey(
                RedisCacheKey.REDIS_USER_ID_CACHE, userId,
                id -> userRepository.findById(id)
        );
    }

    public UserEntity getByEmailFromCache(String email) {
        return cacheUtils.findBySpecialKey(
                RedisCacheKey.REDIS_USER_EMAIL_CACHE,
                email,
                emailKey -> userRepository.findByEmail(emailKey)
        );
    }

    public void deleteByEmailFromCache(String email) {
        cacheUtils.deleteFromCache(RedisCacheKey.REDIS_USER_EMAIL_CACHE, email);
    }

    public void updateCacheUser(UserEntity userEntity) {
        cacheUtils.updateCache(RedisCacheKey.REDIS_USER_EMAIL_CACHE, userEntity, UserEntity::getEmail);
        cacheUtils.updateCache(RedisCacheKey.REDIS_USER_ID_CACHE, userEntity, UserEntity::getId);
    }
}
