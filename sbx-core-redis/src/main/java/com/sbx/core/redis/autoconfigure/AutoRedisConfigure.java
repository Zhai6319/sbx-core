package com.sbx.core.redis.autoconfigure;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.sbx.core.redis.annotation.EnableRedis;
import com.sbx.core.redis.cache.SbxRedisCache;
import com.sbx.core.redis.helper.RedisHelper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>AutoRedisConfigure class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/2
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@ConditionalOnBean(annotation = {EnableRedis.class})
@EnableConfigurationProperties(RedisProperties.class)
public class AutoRedisConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisHelper")
    public RedisHelper redisHelper(){
        return new RedisHelper();
    }

    @Bean
    @ConditionalOnMissingBean(ValueOperations.class)
    public ValueOperations valueOperations(RedisTemplate redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public SbxRedisCache redisClient(RedisTemplate<String, Object> redisTemplate) {
        return new SbxRedisCache(redisTemplate);
    }

    @Bean
    public RedissonClient redissonClient(@Autowired RedisProperties properties) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword()).setDatabase(properties.getDatabase());
        return Redisson.create(config);
    }


}
