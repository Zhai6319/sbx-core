package com.sbx.core.secure.autoconfigure;

import com.sbx.core.secure.filter.JwtVerifyFilter;
import com.sbx.core.secure.properties.SecureProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

/**
 * <p>AutoSecurityConfigure class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableConfigurationProperties({SecureProperties.class})
public class AutoSecurityConfigure extends WebSecurityConfigurerAdapter {


    @Resource
    private SecureProperties secureProperties;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //关闭跨站请求防护
                .cors()
                .and()
                .csrf()
                .disable()
                //允许不登陆就可以访问的方法，多个用逗号分隔
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                //其他的需要授权后访问
                .anyRequest().authenticated()
                .and()
                //增加自定义验证认证过滤器
                .addFilter(new JwtVerifyFilter(authenticationManager()))
                // 前后端分离是无状态的，不用session了，直接禁用。
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置feign调用忽略
          web.ignoring().antMatchers(secureProperties.getSkipUrl().toArray(new String[secureProperties.getSkipUrl().size()]));
    }


}
