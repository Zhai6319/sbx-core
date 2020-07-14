package com.sbx.core.wx.builder;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/5/16
 */
public class WxServiceBuilder {

    public static WxServiceBuilder getInstance() {
        return new WxServiceBuilder();
    }

    /**
     * 获取微信小程序service
     * @param appId
     * @param secret
     * @return
     */
    public WxMaService getMaService(String appId,String secret) {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appId);
        config.setSecret(secret);
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }

}
