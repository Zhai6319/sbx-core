package com.sbx.core.redis.enums;

/**
 * @author ye2moe
 * @date 2020-08-18 21:32
 **/
public interface RedisLockKeyEnum {

    /**
     * 分布式锁key前缀
     */
    String getKeyPrefix();

    /**
     * 锁定时间
     * 毫秒
     */
    Long getLockTime();

    /**
     * 等待时间
     * 毫秒
     */
    Long getWaitTime();

    /**
     * key build
     */
    default String buildKey(String... keys) {
        return this.getKeyPrefix() + String.join("_", keys);
    }

}
