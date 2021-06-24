package com.namnx.spring_redis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String name;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthDay;

}
