package me.candybox.core.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import me.candybox.core.annotation.AccessTokenOauth;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.core.utils.RedisUtil;
import me.candybox.core.vo.ResultVO;
import me.candybox.core.vo.TokenInfoVO;


/**
 * token拦截器
 */
@Slf4j
@Component
public class AccessTokenInterceptor implements HandlerInterceptor{

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("remove {}", ConstantConfig.ACCESS_TOKEN_KEY);
        TokenInfoThreadLocal.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessTokenOauth accessTokenOauth=handlerMethod.getMethod().getAnnotation(AccessTokenOauth.class);
            if(accessTokenOauth!=null&&accessTokenOauth.value()==false){
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        }
        ResultVO resultVO = new ResultVO(ConstantConfig.RESULT_STATUS_NO_ACCESSTOKEN, "用户登录已过期，请重新登录");
        response.setCharacterEncoding("utf-8");
        String accessToken=getCookie(request, ConstantConfig.ACCESS_TOKEN_KEY);
        if(accessToken==null){
            accessToken=request.getHeader(ConstantConfig.ACCESS_TOKEN_KEY);
        }
        if(accessToken==null){
            accessToken=request.getParameter(ConstantConfig.ACCESS_TOKEN_KEY);
        }
        log.debug("{}={}", ConstantConfig.ACCESS_TOKEN_KEY, accessToken);
        if(StrUtil.isBlank(accessToken)){
            response.getWriter().write(JSON.toJSONString(resultVO));
            response.setStatus(401);
            return false;
        }
        Object ret =  redisUtil.get(ConstantConfig.ACCESS_TOKEN_KEY+":"+accessToken);
        log.debug("{}={}", "TokenInfoVO", ret);
        if(ret!=null){
            TokenInfoVO tokenInfoVO=JSON.parseObject((String)ret, TokenInfoVO.class);
            TokenInfoThreadLocal.setTokenInfo(tokenInfoVO);
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        response.getWriter().write(JSON.toJSONString(resultVO));
        response.setStatus(401);
        return false;
    }


    private String getCookie(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(key.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    
}
