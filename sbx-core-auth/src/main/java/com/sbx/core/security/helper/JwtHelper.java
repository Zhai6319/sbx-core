package com.sbx.core.security.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>JwtHelper class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/3
 */
public class JwtHelper {

    public final static int ONE_DAY_SECOND = 1*24*60*60;
    public final static String JWT_SECRET = "oYUhkv-b3MzSwV7npMUMdpEErXic";
    private final static int MAX_EXPIRED_DAY = 1;
    public static String BEARER = "bearer";
    public static Integer AUTH_LENGTH = 7;

    private static Map<String,Object> getJwtHeader(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return map;
    }


    /**
     * 根据访问令牌获取token信息
     * @param auth
     * @return
     */
    public static String getToken(String auth){
        if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                auth = auth.substring(7);
                return auth;
            }
        }
        return null;
    }

    /**
     * 验证token
     * */
    public static DecodedJWT verifyJwt(String auth) throws UnsupportedEncodingException {
        String token = getToken(auth);
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }





}
