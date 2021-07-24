package com.sbx.core.xxljob.autoconfigure;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/9
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    private String adminAddresses;

    private String appName;

    private String ip;

    private Integer port;

    private String accessToken;

    private String logPath;

    private Integer logRetentionDays;



}
