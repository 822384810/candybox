package me.candybox.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static String getCookie(HttpServletRequest request,String key){
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
