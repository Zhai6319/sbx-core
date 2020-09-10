package com.sbx.core.mybatis.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.sbx.core.launch.factory.YamlPropertySourceFactory;
import com.sbx.core.model.constants.CommonConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>MybatisPlusAutoConfiguration class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/20
 */
@EnableTransactionManagement
@Configuration
@MapperScan(CommonConstant.PARENT_PACKAGE+".**.mapper.**")
@PropertySource(value = "classpath:sbx-mybatis.yaml", factory = YamlPropertySourceFactory.class)
public class MybatisPlusAutoConfigure {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }


}
