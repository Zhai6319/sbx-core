package com.sbx.core.secure.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * <p>
 *     AuthUserClient class:
 *     获取授权用户信息
 * </p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
@Component
@AllArgsConstructor
public class AuthUserClient {

    private final RestTemplate restTemplate;


}
