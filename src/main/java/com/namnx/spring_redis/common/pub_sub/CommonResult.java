package com.namnx.spring_redis.common.pub_sub;

import com.namnx.spring_redis.enums.pub_sub.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResult<T> {

    private ResultCode code;
    private String msg;
    private T data;

}
