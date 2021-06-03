package com.namnx.spring_redis.web;

import com.namnx.spring_redis.model.UserEntity;
import com.namnx.spring_redis.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserCacheService userCacheService;

    @GetMapping("/buildAll")
    public ResponseEntity<String> buildCacheUsers() {
        userCacheService.buildCacheAllDataUser();
        return ResponseEntity.ok("Build cache users successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<UserEntity>> getUsersFromCacheKeyId() {
        return ResponseEntity.ok(userCacheService.getAllFromCacheKeyId());
    }

    @GetMapping("/allByEmail")
    public ResponseEntity<Collection<UserEntity>> getUsersFromCacheKeyEmail() {
        return ResponseEntity.ok(userCacheService.getAllFromCacheKeyEmail());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getByIdFromCache(@PathVariable Long id) {
        return ResponseEntity.ok(userCacheService.getByIdFromCache(id));
    }

    @GetMapping("/detail/{email}")
    public ResponseEntity<UserEntity> getByEmailFromCache(@PathVariable String email) {
        return ResponseEntity.ok(userCacheService.getByEmailFromCache(email));
    }

    @PostMapping("/delete/{email}")
    public ResponseEntity<String> deleteFromCacheKeyEmail(@PathVariable String email) {
        userCacheService.deleteByEmailFromCache(email);
        return ResponseEntity.ok(email);
    }

    @PostMapping("/update")
    public ResponseEntity<UserEntity> updateCacheUser(@RequestBody UserEntity u) {
        userCacheService.updateCacheUser(u);
        return ResponseEntity.ok(u);
    }

}
