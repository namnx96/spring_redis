package com.namnx.spring_redis.common.pub_sub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
public class Message extends BaseMessage {
    private String title;
    private String content;
}
