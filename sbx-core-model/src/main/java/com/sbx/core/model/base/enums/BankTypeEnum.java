package com.sbx.core.model.base.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/2/24
 */
public enum BankTypeEnum {


    ABC("ABC","中国农业银行"),
    BCCB("BCCB","北京银行"),
    BOC("BOC","中国银行"),
    BOS("BOS","上海银行"),
    BQD("BQD","青岛银行"),
    CBHB("CBHB","渤海银行"),
    CCB("CCB","中国建设银行"),
    CEB("CEB","中国光大银行"),
    CIB("CIB","兴业银行"),
    CITIC("CITIC","中信银行"),
    CMB("CMB","招商银行"),
    CMBC("CMBC","民生银行"),
    COMM("COMM","交通银行"),
    CSCB("CSCB","长沙银行"),
    CZB("CZB","浙商银行"),
    CZCB("CZCB","浙江稠州商业银行"),
    EGBANK("EGBANK","恒丰银行"),
    GDB("GDB","广发银行"),
    GNXS("GNXS","广州农村商业银行"),
    GZCB("GZCB","广州银行"),
    HCCB("HCCB","杭州银行"),
    HNNXS("HNNXS","湖南省农村信用社联合社"),
    HSBANK("HSBANK","徽商银行"),
    HXB("HXB","华夏银行"),
    ICBC("ICBC","中国工商银行"),
    ICBCA("ICBCA","华商银行"),
    LZCB("LZCB","兰州银行"),
    NBCB("NBCB","宁波银行"),
    NJCB("NJCB","南京银行"),
    PSBC("PSBC","中国邮储银行"),
    SHRCB("SHRCB","上海农商银行"),
    SNXS("SNXS","深圳农村商业银行"),
    SPDB("SPDB","浦发银行"),
    SXJS("SXJS","晋城银行"),
    SZPAB("SZPAB","平安银行"),
    UPOP("UPOP","银联在线支付"),
    WZCB("WZCB","温州银行"),
    HEBB("HEBB","河北银行"),
    TJCB("TJCB","天津银行"),

    WXNATIVE("WXNATIVE","微信扫码"),
    WXXCX("WXXCX","微信小程序"),
    ZFBNATIVE("ZFBNATIVE","支付宝扫码"),
    WXAPP("WXAPP","微信APP支付"),
    ZFBAPP("ZFBAPP","支付宝APP支付"),
    ZFBSHH("ZFBSHH","支付宝生活号支付"),
    WXBCODE("WXBCODE","微信条码"),
    ZFBBCODE("ZFBBCODE","支付宝条码"),
    WXJSAPI("WXJSAPI","微信公众号"),
    YLZS("YLZS","银联主扫"),
    YLJTM("YLJTM","银联静态码"),
    QQNATIVE("QQNATIVE","QQ钱包扫码"),
    WALLET("WALLET","钱包余额"),
    ;
    @Getter
    private final String code;

    @Getter
    private final String name;

    BankTypeEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    public static BankTypeEnum getByCode(String code) {
        if (Objects.nonNull(code)) {
            for (BankTypeEnum itemEnum : BankTypeEnum.values()) {
                if (Objects.equals(itemEnum.getCode(),code)) {
                    return itemEnum;
                }
            }
        }
        return null;
    }

}
