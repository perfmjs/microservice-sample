package com.util;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import example.mybatis.youyue.domain.User;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tony on 15/8/28.
 */
public class JSONWebTokenUtils {

    /**jwt come from: request.getParameter(JSONWebTokenUtils.JWT_REQUEST_PARAMETER_KEY)*/
    public static final String JWT_REQUEST_PARAMETER_KEY = "x-access-token";

    /**
     * @param jwtValue e.g. xxx.yyy.zzz|userName
     */
    public static boolean checkToken(String jwtValue) {
        if (jwtValue != null && !"".equals(jwtValue) && jwtValue.split("\\|").length > 1) {
            String[] jwts = jwtValue.split("\\|");
            String token = jwts[0];
            String userName = jwts[1];
            try {
                Map<String,Object> decodedPayload =  new JWTVerifier("secretCommonLogin" + userName).verify(token);
                if (decodedPayload.get("iss") != null && decodedPayload.get("iss").equals(userName)) {
                    return true;
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | SignatureException | JWTVerifyException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * jwtValue like: xxx.yyy.zzz|userName
     * @param jwtValue
     * @return
     */
    public static String[] splitToken(String jwtValue) {
        if (jwtValue != null && !"".equals(jwtValue) && jwtValue.split("\\|").length > 1) {
            return jwtValue.split("\\|");
        }
        String[] initArray = {"",""};
        return initArray;
    }

    public static String buildJSONWebToken(String secrect, String userName, int expirySeconds) {
        return buildJSONWebToken(secrect,userName,null,null,expirySeconds);
    }

    /**
     * 构建jwt
     * @param secrect 盐值
     * @param userName 用户名
     * @param sub 主题
     * @param aud 令牌的听众, e.g 'webapp'
     * @param expirySeconds 过期时间 e.g. 3600*24
     * @return
     */
    public static  String buildJSONWebToken(String secrect, String userName, String sub, String aud, int expirySeconds) {
        JWTSigner signer = new JWTSigner(secrect + userName);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("iss", userName);
        claims.put("sub", sub==null?"commonLogin":sub);
        claims.put("aud", aud==null?"webapp":aud);
        String token = signer.sign(claims,
                new JWTSigner.Options().setAlgorithm(Algorithm.HS256)
                        .setExpirySeconds(expirySeconds)
                        .setNotValidBeforeLeeway(5)
                        .setIssuedAt(false)
                        .setJwtId(false)
        );
        return token + '|' + userName;
    }
}
