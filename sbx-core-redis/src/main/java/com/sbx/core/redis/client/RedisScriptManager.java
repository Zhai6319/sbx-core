package com.sbx.core.redis.client;


import com.sbx.core.model.enums.EResultCode;
import com.sbx.core.model.exception.CustomException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: RedisScriptManager
 * @Description:
 * @author guoluwei
 * @date 2020/9/3 16:58
 */
@Component
public class RedisScriptManager {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String IPAC = "IPAC:";


    private static final String IP_BLACK_LIST_SCRIPT = "local time=ARGV[1]\n" +
            "local blackCount=ARGV[2]\n" +
            "local blackTime=ARGV[3]\n" +
            "if (redis.call('get',KEYS[1])==false) then\n" +
            "    redis.call('set',KEYS[1],0)\n" +
            "    redis.call('expire',KEYS[1],time)\n" +
            "end\n" +
            "local count=redis.call('incr', KEYS[1])\n" +
            "if (count>tonumber(blackCount)) then\n" +
            "    redis.call('expire', KEYS[1], blackTime)\n" +
            "end\n" +
            "return count";

    /**
     * @Title: ipAccessCount
     * @Description: ip接口调用计数器
     * @param key redis换成key 拼接之后为 IPAC:${key}:${ip}
     * @param ip  调用方ip  IpUtils.getRemoteIP(request)  如果不需要对比ip，可以随便传个字符串，不要为空
     * @param refuseNum  最大调用次数
     * @param refuseTime 缓存过期时间 单位秒  一个ip 一个key refuseTime秒内最多调用refuseNum次
     * @param blackNum  拉黑调用次数
     * @param blackTime  refuseTime内调用超过blackNum次，ip会被拉黑blackTime秒,并且如果不停止调用，会一直刷新拉黑时间
     * @return java.lang.Long 返回已用次数
     */
    public Long ipAccessCount(String key, String ip, int refuseNum, int refuseTime, int blackNum, int blackTime) {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>(IP_BLACK_LIST_SCRIPT, Long.class);
        //这个脚本的逻辑是就是不停计数,有一个较短的过期时间,只在第一次插入的时候设置过期时间,之后继续计数
        //如果计数大于一定值,这个计数就转为黑名单,设置一个更大的过期时间,并且每次都会刷新过期时间
        //这样一个ip只会在redis里存一条数据
        List<String> keys = Collections.singletonList(IPAC + key + ":" + ip);
        Object[] args = {String.valueOf(refuseTime), String.valueOf(blackNum), String.valueOf(blackTime)};
        Long count = stringRedisTemplate.execute(defaultRedisScript, keys, args);
        if (count != null) {
            if (count > blackNum) {
                throw new CustomException(EResultCode.IP_BAN);
            } else if (count > refuseNum) {
                throw new CustomException(EResultCode.IP_BAN);
            }
        }
        return count;
    }

}
