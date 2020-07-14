package com.sbx.core.swagger.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * <p>SwaggerProperties class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/29
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /**
     * 扫描公共包下的所有接口进行swagger文档生成
     * */
    private String basePackage = "com.sbx";

    /**
     * 文档标题
     */
    private String title = "接口文档";

    /**
     * 文档描述
     */
    private String description = "接口文档";

    /**
     * 文档版本号
     */
    private String version = "1.0.0";

    /**
     * 参数
     * */
    List<Parameter> pars;




}
