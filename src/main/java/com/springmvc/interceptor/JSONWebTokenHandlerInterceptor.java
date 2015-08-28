package com.springmvc.interceptor;

import com.ajaxjson.JSONMessage;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.util.JSONWebTokenUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

/**
 * Created by tony on 15/8/25.
 */
public class JSONWebTokenHandlerInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return this.doHandler(request, response, handler);
    }

    protected boolean doHandler(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (this.notNeedCheckToken(request)) {
            return true;
        }
        String jwtValue = request.getParameter(JSONWebTokenUtils.JWT_REQUEST_PARAMETER_KEY);
        if (JSONWebTokenUtils.checkToken(jwtValue)) {
            return true;
        }

        try {
            response.getWriter().write(JSONMessage.newMessageOnFail().setCode(401).setMsg("Unauthorized").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean notNeedCheckToken(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("GET")
                || request.getServletPath().indexOf("/user/login") > -1
                || request.getServletPath().indexOf("/user/register") > -1
                || request.getServletPath().indexOf("/user/checkToken") > -1
                || request.getServletPath().indexOf("/cms/add") > -1
                ;
    }

}
