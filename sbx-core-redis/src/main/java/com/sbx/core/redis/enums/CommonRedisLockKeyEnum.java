package com.sbx.core.redis.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonRedisLockKeyEnum implements RedisLockKeyEnum {

    EXAMPLE("example:", 3000L, 10000L),
    ;

    private final String keyPrefix;
    private final Long lockTime;
    private final Long waitTime;

}
