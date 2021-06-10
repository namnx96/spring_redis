package com.namnx.spring_redis.repository;

import com.namnx.spring_redis.model.UserEntity;
import org.springframework.stereotype.Repository;

//fake repository for getting entity to update cache
@Repository
public class UserRepository {

    public UserEntity findById(Long id) {
        return new UserEntity(id, "namnx4@gmail.com", "nam nx 4", "2021/06/10");
    }

    public UserEntity findByEmail(String email) {
        return new UserEntity(0L, email, "nam nx 5", "2021/06/10");
    }

    //in case hash key is a combination of some value
    //example: id_email, companyId_userId,...
    public UserEntity findByIdAndCompanyId(String userIdAndCompanyIdHashKey) {
        Long userId = Long.parseLong(userIdAndCompanyIdHashKey.split("_")[0]);
        Long companyId = Long.parseLong(userIdAndCompanyIdHashKey.split("_")[1]);
        //repo.findByUserIdAndCompanyId(userId, companyId)
        return new UserEntity(userId, "someemail@gmail.com", "nam nx 5", "2021/06/10");
    }
}
