package com.namnx.spring_redis.common.pub_sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.namnx.spring_redis.enums.pub_sub.TypeChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BaseMessage {
    private String id;
    private TypeChannel channel;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date updateTime;
}
