package com.sbx.core.map.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/8
 */
@Data
@ConfigurationProperties(prefix = "map")
public class MapProperties {

    /**
     * 高德导航配置
     */
    private GaoDe gaoDe;

    /**
     * 腾讯导航配置
     */
    private Tencent tencent;

    @Data
    public static class GaoDe{
        private String key;

    }

    @Data
    public static class Tencent{
        private String key;
    }
}
