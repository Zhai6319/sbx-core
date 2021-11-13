package com.sbx.core.redis.helper;

import org.redisson.api.*;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.codec.LongCodec;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *     说明：redisson 操作redis助手
 * </p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/11/13
 */
public class RedissonHelper {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 常用KEY VALUE 方式存储redis
     * @param key       redis key
     * @param value     redis value
     * @param cacheTime 缓存时间
     * @param timeUnit  时间单位
     * @param <V>       value对象泛型
     */
    public <V> void set(String key, V value, Long cacheTime, TimeUnit timeUnit) {
        RBucket<V> bucket = redissonClient.getBucket(Objects.requireNonNull(key));
        bucket.set(value,cacheTime,timeUnit);
    }
    /**
     * 常用KEY VALUE 方式存储redis
     * @param key       redis key
     * @param value     redis value
     * @param cacheTime 缓存时间 秒
     * @param <V>       value对象泛型
     */
    public <V> void set(String key, V value, Long cacheTime) {
        RBucket<V> bucket = redissonClient.getBucket(Objects.requireNonNull(key));
        bucket.set(value,cacheTime,TimeUnit.SECONDS);
    }

    /**
     * 常用KEY VALUE 方式存储redis 永久存储
     * @param key       redis key
     * @param value     redis value
     * @param <V>       value对象泛型
     */
    public <V> void set(String key, V value) {
        RBucket<V> bucket = redissonClient.getBucket(Objects.requireNonNull(key));
        bucket.set(value);
    }

    /**
     * 根据redis key 获取redis 值
     * @param key redis key
     * @param <V>   value对象泛型
     * @return  返回value
     */
    public <V> V get(String key) {
        RBucket<V> bucket = redissonClient.getBucket(Objects.requireNonNull(key));
        return bucket.get();
    }

    /**
     * 添加一条map数据到redis
     * @param key       redis key
     * @param mapKey    map key
     * @param value     map value
     * @param cacheTime 缓存时间
     * @param timeUnit  时间单位
     * @param <K>       map key 泛型
     * @param <V>       map value 泛型
     */
    public <K,V> void put(String key, K mapKey, V value, Long cacheTime, TimeUnit timeUnit){
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.put(mapKey,value);
        map.expire(cacheTime,timeUnit);
    }

    /**
     * 添加一条map数据到redis
     * @param key       redis key
     * @param mapKey    map key
     * @param value     map value
     * @param cacheTime 缓存时间 默认秒
     * @param <K>       map key 泛型
     * @param <V>       map value 泛型
     */
    public <K,V> void put(String key, K mapKey, V value, Long cacheTime){
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.put(mapKey,value);
        map.expire(cacheTime,TimeUnit.SECONDS);
    }

    /**
     * 添加一条map数据到redis
     * @param key       redis key
     * @param mapKey    map key
     * @param value     map value
     * @param <K>       map key 泛型
     * @param <V>       map value 泛型
     */
    public <K,V> void put(String key, K mapKey, V value){
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.put(mapKey,value);
    }
    /**
     * 批量添加map数据到redis
     * @param key       redis key
     * @param mapValue    map 数据
     * @param cacheTime 缓存时间
     * @param timeUnit  时间单位
     * @param <K>       map key 泛型
     * @param <V>       map value 泛型
     */
    public <K,V> void putAll(String key, Map<K,V> mapValue, Long cacheTime, TimeUnit timeUnit){
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.putAll(mapValue);
        map.expire(cacheTime,timeUnit);
    }

    /**
     * 批量添加map数据到redis
     * @param key       redis key
     * @param mapValue    map 数据
     * @param cacheTime 缓存时间 默认秒
     * @param <K>       map key 泛型
     * @param <V>       map value 泛型
     */
    public <K,V> void putAll(String key, Map<K,V> mapValue, Long cacheTime){
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.putAll(mapValue);
        map.expire(cacheTime,TimeUnit.SECONDS);
    }

    /**
     * 批量添加map数据到redis
     * @param key       redis key
     * @param mapValue    map 数据
     * @param <K>       map key 泛型
     * @param <V>       map value 泛型
     */
    public <K,V> void putAll(String key, Map<K,V> mapValue){
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.putAll(mapValue);
    }

    /**
     * 获取所有map
     * @param key   redis key
     * @param <K>   map key 泛型
     * @param <V>   map value 泛型
     * @return      返回map数据
     */
    public <K,V> Map<K,V> getAllMap(String key) {
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        return map.readAllMap();
    }

    /**
     * 获取所有map
     * @param key   redis key
     * @param keySet 要获取map数据key集合
     * @param <K>   map key 泛型
     * @param <V>   map value 泛型
     * @return      返回map数据
     */
    public <K,V> Map<K,V> getAllMap(String key, Set<K> keySet) {
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        return map.getAll(keySet);
    }

    /**
     * 删除map value
     * @param key       redis key
     * @param mapKey    map key
     * @param <K>       map key 泛型
     * @param <V>       map value 泛型
     */
    public <K,V> void removeMapValue(String key, K mapKey) {
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.remove(mapKey);
    }

    /**
     * 删除map 值
     * @param key       redis key
     * @param keySet    map key set
     * @param <K>       泛型 map key
     * @param <V>       泛型 map value
     */
    public <K,V> void removeMapValue(String key, Set<K> keySet) {
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        keySet.forEach(map::remove);
    }

    /**
     * 清楚map缓存
     * @param key   redis key
     * @param <K>   泛型 map key
     * @param <V>   泛型 map value
     */
    public <K,V> void clearMap(String key) {
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        map.clear();
    }

    /**
     * 获取一条map值
     * @param key   redis key
     * @param <K>   map key 泛型
     * @param <V>   map value 泛型
     * @return      返回map数据
     */
    public <K,V> V getMapValue(String key,K mapKey) {
        RMap<K, V> map = redissonClient.getMap(Objects.requireNonNull(key));
        return map.get(mapKey);
    }


    /**
     * 添加列表值
     * @param key       redis key
     * @param value     list value
     * @param cacheTime 缓存时间
     * @param timeUnit  时间单位
     * @param <V>       值泛型
     */
    public <V> void addListValue(String key,V value, Long cacheTime, TimeUnit timeUnit){
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        list.add(value);
        list.expire(cacheTime,timeUnit);
    }

    /**
     * 添加列表值
     * @param key       redis key
     * @param value     list value
     * @param cacheTime 缓存时间 秒
     * @param <V>       值泛型
     */
    public <V> void addListValue(String key,V value, Long cacheTime){
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        list.add(value);
        list.expire(cacheTime,TimeUnit.SECONDS);
    }

    /**
     * 添加列表值
     * @param key       redis key
     * @param value     list value
     * @param <V>       值泛型
     */
    public <V> void addListValue(String key,V value){
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        list.add(value);
    }

    /**
     * 添加list到redis缓存
     * @param key       redis key
     * @param values    list values
     * @param cacheTime 缓存时间
     * @param timeUnit  时间单位
     * @param <V>       值泛型
     */
    public <V> void addAllList(String key,List<V> values, Long cacheTime, TimeUnit timeUnit){
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        list.addAll(values);
        list.expire(cacheTime,timeUnit);
    }

    /**
     * 添加list到redis缓存
     * @param key       redis key
     * @param values    list values
     * @param cacheTime 缓存时间 秒
     * @param <V>       值泛型
     */
    public <V> void addAllList(String key,List<V> values, Long cacheTime){
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        list.addAll(values);
        list.expire(cacheTime,TimeUnit.SECONDS);
    }

    /**
     * 添加list到redis缓存
     * @param key       redis key
     * @param values    list values
     * @param <V>       值泛型
     */
    public <V> void addAllList(String key,List<V> values){
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        list.addAll(values);
    }

    /**
     * 根据 redis key 获取 list数据
     * @param key   redis key
     * @param <V>   value 泛型
     * @return  List<T>
     */
    public <V> List<V> getAllList(String key) {
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        return list.readAll();
    }

    /**
     * 根据 redis key 获取 list数据
     * @param key   redis key
     * @param end   list end index
     * @param <V>   value 泛型
     * @return  List<T>
     */
    public <V> List<V> getAllList(String key,int end) {
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        return list.range(end);
    }

    /**
     * 根据 redis key 获取 list数据
     * @param key   redis key
     * @param start list start index
     * @param end   list end index
     * @param <V>   value 泛型
     * @return  List<T>
     */
    public <V> List<V> getAllList(String key,int start, int end) {
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        return list.range(start,end);
    }

    /**
     * 清除list
     * @param key   redis key
     * @param <V>   value 泛型
     */
    public <V> void clearList(String key) {
        RList<V> list = redissonClient.getList(Objects.requireNonNull(key));
        list.clear();
    }

    /**
     * 计数器
     * @param key       计数器key
     * @param number    增值
     * @return  返回计数结果
     */
    public long incr(String key, long number,long cacheTime, TimeUnit timeUnit) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(Objects.requireNonNull(key));
        atomicLong.expire(cacheTime,timeUnit);
        return atomicLong.addAndGet(number);
    }

    /**
     * 计数器
     * @param key       计数器key
     * @param number    增值
     * @return  返回计数结果
     */
    public long incr(String key, long number) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(Objects.requireNonNull(key));
        return atomicLong.addAndGet(number);
    }

    public long incr(String key,long cacheTime, TimeUnit timeUnit) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(Objects.requireNonNull(key));
        atomicLong.expire(cacheTime,timeUnit);
        return atomicLong.incrementAndGet();
    }

    public long incr(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(Objects.requireNonNull(key));
        return atomicLong.incrementAndGet();
    }

    /**
     * 获取计数器
     * @param key   计数器key
     * @return      返回结果
     */
    public long getIncr(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(Objects.requireNonNull(key));
        return atomicLong.get();
    }

    /**
     * 计数器
     * @param namespace 命名空间
     * @param key       计数器key
     * @param number    增值
     * @return  返回计数结果
     */
    public long hIncr(String namespace, String key, long number) {
        RMapCache<String, Long> rMapCache = redissonClient.getMapCache(Objects.requireNonNull(namespace), LongCodec.INSTANCE);
        rMapCache.putIfAbsent(key,0L);
        return rMapCache.addAndGet(key,number);
    }

    /**
     * 获取计数器
     * @param namespace 命名空间
     * @param key       计数器key
     * @return          返回计数器值
     */
    public long hGetIncr(String namespace, String key) {
        RMapCache<String, Long> rMapCache = redissonClient.getMapCache(Objects.requireNonNull(namespace),IntegerCodec.INSTANCE);
        return rMapCache.get(key);
    }

    /**
     * 获取计数器
     * @param namespace 命名空间
     * @return  返回结果
     */
    public Map<String,Long> hGetIncr(String namespace) {
        RMapCache<String, Long> rMapCache = redissonClient.getMapCache(Objects.requireNonNull(namespace),IntegerCodec.INSTANCE);
        return rMapCache.readAllMap();
    }




}
