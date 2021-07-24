package com.sbx.core.redis.lock;


import com.sbx.core.model.exception.AcquireRedisLockFailedException;
import com.sbx.core.redis.enums.RedisLockKeyEnum;

/**
 * redis lock
 * best practices
 * <pre>
 *         try {
 *             // 申请锁,等待 x 毫秒, 最久锁住 y 毫秒.
 *             redisLocker.tryLock("key", 5000L, 30000L);
 *             // 业务代码处理
 *             // xxx
 *         } catch (BizException e) {
 *             // 业务代码导致的异常, 实现补偿操作
 *             log.error(e.getMessage(), e);
 *             throw e;
 *         } catch (AcquireRedisLockFailedException e) {
 *             // 获取锁失败的异常, 做相应获取锁失败的处理, 或者直接抛出即可, 直接抛出的话可以不写此 catch
 *             throw e;
 *         } finally {
 *             // 解锁
 *             redisLocker.unlock("key");
 *         }
 * </pre>
 *
 * @author ye2moe
 * @date 2020-08-21 14:13
 **/
public interface RedisLocker {

    /**
     * 获取分布式锁
     *
     * @param key      锁key
     * @param waitTime 最长等待锁的时间(毫秒)
     * @param lockTime 最长持有锁的时间(毫秒), 如果不太好把握时间的话可以使用下面的重载方法: tryLock(String, Long)
     *                 传入 -1 时将由 watchdog 保证是否释放锁.
     *                 任务挂了会自己解锁，任务未完成会自己续期.
     * @throws AcquireRedisLockFailedException 获取锁失败,需要自行处理(获取锁失败)异常. 一般情况直接抛出异常就好,无需处理.
     */
    void tryLock(String key, Long waitTime, Long lockTime) throws AcquireRedisLockFailedException;

    /**
     * 重载tryLock方法
     */
    default void tryLock(String key, Long waitTime) throws AcquireRedisLockFailedException {

        this.tryLock(key, waitTime, -1L);
    }

    /**
     * 只获取一次锁, 不希望等待锁, 第一次获取锁失败就抛出异常.
     * 特殊场景使用如: 报名锁,下单锁,付款锁 ..等
     * 多次并发的请求没有意义,直接拦截掉即可.
     */
    default void tryLockOnce(String key) throws AcquireRedisLockFailedException {

        this.tryLockOnce(key, -1L);
    }

    /**
     * 重载 tryLockOnce
     */
    default void tryLockOnce(String key, Long lockTime) throws AcquireRedisLockFailedException {

        this.tryLock(key, 0L, lockTime);
    }

    /**
     * 重载tryLock方法
     * 枚举管理key
     */
    default void tryLock(RedisLockKeyEnum enumLock, String... keyParams) throws AcquireRedisLockFailedException {

        this.tryLock(enumLock.buildKey(keyParams), enumLock.getWaitTime(), enumLock.getLockTime());
    }

    /**
     * 解锁
     */
    void unlock(String key);

    /**
     * 重载unlock方法
     */
    default void unlock(RedisLockKeyEnum enumLock, String... keyParams) {

        this.unlock(enumLock.buildKey(keyParams));
    }

}
