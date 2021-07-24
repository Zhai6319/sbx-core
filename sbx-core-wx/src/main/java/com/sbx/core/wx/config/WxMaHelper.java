package com.sbx.core.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/2
 */
@Slf4j
@Component
public class WxMaHelper {

    @Resource
    private WxMaProperties wxMaProperties;


    public Boolean send(String page,String toUser, Map<String,String> params, String templateKey){
        WxMaProperties.Config config = wxMaProperties.getConfigs().get(0);
        final WxMaService wxService = WxMaConfiguration.getMaService(config.getAppid());
        final WxMaMsgService wxMaMsgService = wxService.getMsgService();
        WxMaSubscribeMessage message = new WxMaSubscribeMessage();
        message.setToUser(toUser);
        MessageModel model = wxMaProperties.getMsgTemplate().get(templateKey);
        if (Objects.isNull(model)) {
            return false;
        }
        for (String s : model.getCode()) {
            if (Objects.isNull(params.get(s))) {
                return false;
            }
            WxMaSubscribeMessage.Data data = new WxMaSubscribeMessage.Data();
            data.setName(s);
            data.setValue(params.get(s));
            message.addData(data);
        }
        message.setTemplateId(model.getTemplateId());
        message.setPage(page);
        message.setMiniprogramState(wxMaProperties.getEnv());
        message.setLang("zh_CN");
        try {
            wxMaMsgService.sendSubscribeMsg(message);
            return true;
        } catch (WxErrorException e) {
            log.error("微信消息发送失败",e);
            return false;
        }
    }
}
