package com.sbx.core.secure.properties;

import com.sbx.core.tool.util.Func;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>SecureProperties class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
@Data
@ConfigurationProperties("sbx.secure")
public class SecureProperties {

    private List<String> skipUrl = new ArrayList<>();

    {
        skipUrl.add("/actuator/**");
        skipUrl.add("/token/**");
        skipUrl.add("/oauth/captcha");
        skipUrl.add("/oauth/logout");
        skipUrl.add("/v2/api-docs");
        skipUrl.add("/v2/api-docs-ext");
        skipUrl.add("/client-feign/**");
    }


    public String[] getDefaultSkipUrl() {
        return Func.toStrArray(Func.toStr(skipUrl));
    }
}
