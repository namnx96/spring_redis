package com.namnx.spring_redis.service.pub_sub;

import com.google.gson.Gson;
import com.namnx.spring_redis.common.pub_sub.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MessageReceiver {

    public void systemMessage(String message) {
        Gson gson = new Gson();
        Message messageObject = gson.fromJson(message, Message.class);
        log.info("Listen to message from channel: SYSTEM. Message content: {}", messageObject);
    }

    public void clientMessage(String message) {
        Gson gson = new Gson();
        Message messageObject = gson.fromJson(message, Message.class);
        log.info("Listen to message from channel: CLIENT. Message content: {}", messageObject);
    }
}
