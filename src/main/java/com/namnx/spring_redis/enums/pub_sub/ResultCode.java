package com.namnx.spring_redis.enums.pub_sub;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("200"), FAIL("500");

    private final String value;
}
