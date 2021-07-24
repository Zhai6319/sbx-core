package com.sbx.core.redis.lock.impl;


import com.sbx.core.model.exception.AcquireRedisLockFailedException;
import com.sbx.core.model.exception.RedisLockInterruptedException;
import com.sbx.core.redis.lock.RedisLocker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis 锁
 *
 * @author ye2moe
 * @date 2020-08-18 21:28
 **/
@Slf4j
public class RedisNestedLocker implements RedisLocker {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取分布式锁
     *
     * @param key      锁
     * @param waitTime 等待时间(毫秒)
     * @param lockTime 锁定时间(毫秒)  传入 -1 时将由 watchdog 保证是否释放锁，任务挂了会自己解锁，任务未完成会自己续期
     * @throws AcquireRedisLockFailedException 获取锁失败
     */
    public void tryLock(String key, Long waitTime, Long lockTime) throws AcquireRedisLockFailedException {

        RLock lock = redissonClient.getLock(Objects.requireNonNull(key));

        try {

            if (!lock.tryLock(waitTime, lockTime, TimeUnit.MILLISECONDS)) {
                // 这里是等待 waitTime 毫秒时间后,仍未获取到锁,无需解锁
                throw new RedisLockInterruptedException();
            }

            log.debug("[redisLocker] 获取锁key:" + key);

        } catch (RedisLockInterruptedException e) {
            // 获取锁失败
            log.debug("[redisLocker] 锁竞争key: {} ,等待Redis锁{}毫秒后仍未获取到锁", key, waitTime);
            this.interruptThrow();
        } catch (InterruptedException e) {
            // 此异常为等待锁的过程中被打断，设置中断flag让上层感知。
            log.error("[redisLocker] 等待锁时被打断异常key:" + key, e);
            this.interruptThrow();
        }
    }


    private void interruptThrow() {
        Thread.currentThread().interrupt();
        throw new AcquireRedisLockFailedException();
    }

    /**
     * 解锁
     */
    public void unlock(String key) {

        log.debug("[redisLocker] 尝试解锁key:{}  threadStatus:{}", key, Thread.currentThread().isInterrupted());

        // 既然是等待锁被打断了,那就不需要解锁了.
        // Thread.interrupted() 线程中断机制,以此判断是否需要解锁
        // 线程若为(被打断)状态,可视为尚未获取到锁,亦无需解锁
        // 同时此方法会将线程状态恢复为(未打断)
        if (StringUtils.isEmpty(key) || Thread.interrupted()) {
            return;
        }

        log.debug("[redisLocker] 解锁key:" + key);

        RLock lock = redissonClient.getLock(key);

        lock.unlock();

    }

}
