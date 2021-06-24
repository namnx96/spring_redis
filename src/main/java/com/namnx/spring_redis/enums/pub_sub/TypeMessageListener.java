package com.namnx.spring_redis.enums.pub_sub;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeMessageListener {
    SYSTEM("systemMessage"), CLIENT("clientMessage");

    //this prop mapping with method name in Receiver class
    private final String nameOfMethodListener;
}
