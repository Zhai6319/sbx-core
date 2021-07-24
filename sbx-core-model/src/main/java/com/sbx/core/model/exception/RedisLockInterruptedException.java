package com.sbx.core.model.exception;

/**
 * 锁打断
 *
 * @author ye2moe
 * @date 2020-08-18 21:54
 **/
public class RedisLockInterruptedException extends InterruptedException {

    public RedisLockInterruptedException() {
        super("获取Redis锁失败");
    }
}
