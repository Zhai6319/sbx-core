package com.sbx.core.oss.component.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/5/4
 */
@Data
@ConfigurationProperties(prefix = "sbx.oss")
public class AliOssProperties {

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    private String roleArn;

    private String roleSessionName;

    private String endpoint;

}
