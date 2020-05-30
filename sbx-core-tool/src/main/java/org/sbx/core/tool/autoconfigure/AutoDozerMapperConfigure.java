package org.sbx.core.tool.autoconfigure;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.sbx.core.tool.factory.IGenerator;
import org.sbx.core.tool.factory.impl.EJBGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * <p>
 *     AutoDozerMapperConfigure class:
 *     数据类型转换
 * </p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/4
 */
@Configuration
public class AutoDozerMapperConfigure {

    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean(@Value("classpath*:config/dozer/*.xml") Resource[] resources) throws Exception {
        final DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
        dozerBeanMapperFactoryBean.setMappingFiles(resources);
        return dozerBeanMapperFactoryBean;
    }

    @Bean
    public IGenerator iGenerator(){
        return new EJBGenerator();
    }


}
