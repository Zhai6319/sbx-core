package com.sbx.core.web.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/1/20
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private List<String> skipUrl = new ArrayList<>();

    private List<String> skipAuthorityUrl = new ArrayList<>();
}
