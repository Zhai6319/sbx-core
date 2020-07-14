package com.sbx.core.tool.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/27
 */
public abstract class TxtUtil {

    private static final String HEXES = "0123456789ABCDEF";
    private static final String IP_PARTNER = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
    private static final Pattern PATTERN_STR = Pattern.compile("[\\u4e00-\\u9fa5]+");

    public TxtUtil(){
    }

    public static byte toByte(char c){
        byte b = (byte)HEXES.indexOf(c);
        return b;
    }

    /**
     * 判断当前字符串是否为汉字
     * created by zhaijianchao on 2018/12/13
     * @param str 需要验证的字符串
     * */
    public static boolean isChineseCharacter(String str){
        Matcher m = PATTERN_STR.matcher(str);
        if (StringUtils.isBlank(str)) {
            return false;
        }
        // 有汉字时，返回真
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Object> unionArray(Boolean repeat, Object[][] arrays){
        List<Object> result = new ArrayList<Object>();
        try{
            Object aobj[][];
            int j = (aobj = arrays).length;
            for(int i = 0; i < j; i++){
                Object arr[] = aobj[i];
                Object aobj1[];
                int l = (aobj1 = arr).length;
                for(int k = 0; k < l; k++){
                    Object ar = aobj1[k];
                    if(repeat.booleanValue()){
                        if(!result.contains(ar)){
                            result.add(ar);
                        }
                    } else {
                        result.add(ar);
                    }
                }
            }
        } catch (Exception e){
            return null;
        }
        return result;
    }


    public static String toHex(byte raw[]){
        if(raw == null){
            return null;
        }
        StringBuilder hex = new StringBuilder(2 * raw.length);
        byte abyte0[];
        int j = (abyte0 = raw).length;
        for(int i = 0; i < j; i++){
            byte b = abyte0[i];
            hex.append(HEXES.charAt((b & 240) >> 4)).append(HEXES.charAt(b & 15));
        }
        return hex.toString();
    }

    public static int toInt(String value){
        int result = 0;
        try{
            result = Integer.valueOf(value).intValue();
        } catch (Exception exception) { }
        return result;
    }

    public static byte[] toBytes(String hex){
        int len = hex.length() / 2;
        hex = hex.toUpperCase();
        byte result[] = new byte[len];
        char achar[] = hex.toCharArray();
        for(int i = 0; i < len; i++){
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    public static String toUnicode(String value){
        String result = "";
        String temp = "";
        try{
            char ac[];
            int j = (ac = value.toCharArray()).length;
            for(int i = 0; i < j; i++){
                char c = ac[i];
                temp = (new StringBuilder("0000")).append(Integer.toHexString(c)).toString();
                temp = (new StringBuilder("\\u")).append(temp.substring(temp.length() - 4)).toString();
                result = (new StringBuilder(String.valueOf(result))).append(temp).toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static final boolean isEmpty(String s){
        return s == null || s.trim().isEmpty();
    }

    public static final boolean isIPAddress(String s){
        if(isEmpty(s)){
            return false;
        } else {
            Pattern p = Pattern.compile(IP_PARTNER);
            Matcher m = p.matcher(s);
            return m.matches();
        }
    }

    public static String getMd5(String data) {
        return DigestUtils.md5Hex(data);
    }

    public static final String digest(String src) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(src.getBytes("UTF-8"));
            return toHex(digest.digest());
        } catch(Exception e) {
            return null;
        }
    }

    public static final String MD5(String s) {
        String hexDigits = "0123456789abcdef";
        try {
            byte strTemp[] = s.getBytes("gb2312");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte raw[] = mdTemp.digest();
            StringBuilder hex = new StringBuilder(2 * raw.length);
            byte abyte0[];
            int j = (abyte0 = raw).length;
            for(int i = 0; i < j; i++) {
                byte b = abyte0[i];
                hex.append(hexDigits.charAt((b & 240) >> 4)).append(hexDigits.charAt(b & 15));
            }
            return hex.toString();
        } catch(Exception e) {
            return null;
        }
    }



    public static String align(String value, int len, char c, boolean leftAlign) {
        String result = "";
        if(leftAlign){
            for(int i = 0; i < len; i++){
                if(i < value.length()){
                    result = (new StringBuilder(String.valueOf(result))).append(value.charAt(i)).toString();
                }else{
                    result = (new StringBuilder(String.valueOf(result))).append(c).toString();
                }
            }

        } else {
            for(int i = 0; i < len; i++){
                if(i < value.length()){
                    result = (new StringBuilder(String.valueOf(result))).append(value.charAt(i)).toString();
                } else {
                    result = (new StringBuilder(String.valueOf(c))).append(result).toString();
                }
            }

        }
        return result;
    }

    public static String trim(String src, int len, String triming) {
        if(src == null || len <= 0){
            return "";
        }
        byte bStr[] = src.getBytes();
        if(len >= bStr.length){
            return src;
        }
        String cStr = new String(bStr, len - 1, 2);
        if(cStr.length() == 1 && src.contains(cStr)){
            len--;
        }
        return (new StringBuilder(String.valueOf(new String(bStr, 0, len)))).append(triming).toString();
    }

    public static String replace(String src, int start, int len, String replacement) {
        if(src.length() < (start + len) - 1) {
            return src;
        } else {
            return (new StringBuilder()).append(src.substring(0, start - 1)).append(replacement).append(src.substring((start + len) - 1)).toString();
        }
    }

    public static String connect(String src[], String c) {
        if(src == null || src.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer("");
        sb.append(src[0]);
        for(int i = 1; i < src.length; i++){
            sb.append(c).append(src[i]);
        }
        return sb.toString();
    }

    public static boolean isEmail(String emailStr) {
        String regEmail = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return match(emailStr, regEmail, true);
    }

    public static boolean isMobile(String mobileStr) {
        String regMobile = "(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])\\d{8}";
        return match(mobileStr, regMobile, false);
    }

    public static String getMobileCorp(String mobileStr) {
        if(mobileStr.contains("+")){
            mobileStr = mobileStr.replace("+86", "");
        }
        String telephone = mobileStr.substring(0, 3);
        int telArea = Integer.valueOf(telephone).intValue();
        int TELS_YD[] = {
                134, 135, 136, 137, 138, 139, 147, 150, 151, 152,
                154, 157, 158, 159, 187, 188
        };
        for(int i = 0; i < TELS_YD.length; i++){
            if(telArea == TELS_YD[i]){
                return "mobile";
            }
        }
        int TELS_LT[] = {
                130, 131, 132, 133, 155, 156, 185, 186
        };
        for(int i = 0; i < TELS_LT.length; i++){
            if(telArea == TELS_LT[i]){
                return "unicom";
            }
        }
        int TELS_DX[] = {
                189, 180
        };
        for(int i = 0; i < TELS_DX.length; i++){
            if(telArea == TELS_LT[i]){
                return "telecom";
            }
        }
        return null;
    }

    public static boolean isIDNumber(String IDNumber) {
        boolean result = IDNumber.matches("[0-9]{15}|[0-9]{17}[0-9X]");
        if(result) {
            int year;
            int month;
            int date;
            if(IDNumber.length() == 15) {
                year = Integer.parseInt(IDNumber.substring(6, 8));
                month = Integer.parseInt(IDNumber.substring(8, 10));
                date = Integer.parseInt(IDNumber.substring(10, 12));
            } else {
                year = Integer.parseInt(IDNumber.substring(6, 10));
                month = Integer.parseInt(IDNumber.substring(10, 12));
                date = Integer.parseInt(IDNumber.substring(12, 14));
            }
            switch(month) {
                case 2:{
                    result = date >= 1 && (year % 4 != 0 ? date <= 28 : date <= 29);
                    break;
                }
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:{
                    result = date >= 1 && date <= 31;
                    break;
                }
                case 4:
                case 6:
                case 9:
                case 11: {
                    result = date >= 1 && date <= 31;
                    break;
                }
                default:
                    result = false;
                    break;
            }
        }
        return result;
    }

    public static boolean match(String src, String reg, boolean caseIgnore) {
        Pattern pattern = caseIgnore ? Pattern.compile(reg, 2) : Pattern.compile(reg);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    public static String preview(String src, int len, String str) {
        if(src != null && src.length() > len) {
            src = (new StringBuilder(String.valueOf(src.substring(0, len - 1)))).append(str).toString();
        }
        return src;
    }

    public static final String get6Radom() {
        String newString = null;
        double doubleP = Math.random() * 1000000D;
        if(doubleP >= 1000000D){
            doubleP = 999999D;
        }

        int tempString = (int)Math.ceil(doubleP);
        for(newString = (new StringBuilder()).append(tempString).toString(); newString.length() < 6; newString = (new StringBuilder("0")).append(newString).toString()){

        }
        return newString;
    }

    public static boolean checkUserName(String username) {
        for(int nIndex = 0; nIndex < username.length(); nIndex++) {
            char cCheck = username.charAt(nIndex);
            if(nIndex == 0 && (cCheck == '-' || cCheck == '_')){
                return false;
            }
            if(!isDigit(cCheck) && !isAlpha(cCheck) && !isChinese(cCheck) && cCheck != '-' && cCheck != '_'){
                return false;
            }
        }
        return true;
    }

    public static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    public static boolean isAlpha(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean isC(String str) {
        boolean strflag = false;
        for(int i = 0; i < str.length(); i++) {
            strflag = (new StringBuilder(String.valueOf(str.charAt(i)))).toString().matches("[\u4E00-\u9FAF]");
        }
        return strflag;
    }


    static List<String> online_list = new ArrayList<String>(0);

    static List<String> client_list = new ArrayList<String>(0);

    static List<String> group_list = new ArrayList<String>(0);


    /**
     * 判断用户类型分类
     * 1代表线上用户
     * 2代表分销终端
     * 3代表集团内部用户
     * @param accType
     * @return
     */
    public static int checkAccType(String accType){
        if(online_list.contains(accType)){
            return 1;
        }
        if(client_list.contains(accType)){
            return 2;
        }
        if(group_list.contains(accType)){
            return 3;
        }
        return 1;
    }


}
