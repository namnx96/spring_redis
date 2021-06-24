package com.namnx.spring_redis.service.pub_sub;

import com.google.gson.Gson;
import com.namnx.spring_redis.common.pub_sub.BaseMessage;
import com.namnx.spring_redis.common.pub_sub.CommonResult;
import com.namnx.spring_redis.enums.pub_sub.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PublisherService<T extends BaseMessage> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Optional<CommonResult<T>> publish(T message) {
        if (message == null) {
            return Optional.empty();
        }
        prePublish(message);
        String channelName = message.getChannel().name();
        try {
            //Publish message to specified channel
            //store
            Gson gson = new Gson();
            String json = gson.toJson(message);
            redisTemplate.convertAndSend(channelName, json);
            log.info("Send message to redis chanel: {} successfully!", channelName);

            return Optional.of(new CommonResult<>(ResultCode.SUCCESS,
                    "Sending message to " + channelName + " Channel successfully!",
                    message)
            );
        } catch (Exception e) {
            log.error("exception when trying to publish message: ", e);

            return Optional.of(new CommonResult<>(ResultCode.FAIL,
                    "Sending message to " + channelName + " Channel fail!",
                    message)
            );
        }

    }

    private void prePublish(T message) {
        if (StringUtils.isEmpty(message.getId())) {
            message.setId(UUID.randomUUID().toString());
        }
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
    }
}
