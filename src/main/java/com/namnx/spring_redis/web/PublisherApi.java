package com.namnx.spring_redis.web;

import com.namnx.spring_redis.common.pub_sub.CommonResult;
import com.namnx.spring_redis.common.pub_sub.Message;
import com.namnx.spring_redis.service.pub_sub.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherApi {

    @Autowired
    private PublisherService<Message> publisherService;


    /**
     * {
     * "channel": "SYSTEM",
     * "content": "First message content",
     * "id": "1",
     * "title": "First message title"
     * }
     */
    @PostMapping("/publish")
    public ResponseEntity<CommonResult<Message>> publishMessage(@RequestBody Message message) {
        return publisherService.publish(message)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
