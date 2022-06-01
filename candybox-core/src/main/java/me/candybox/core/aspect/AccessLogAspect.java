package me.candybox.core.aspect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import lombok.extern.slf4j.Slf4j;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.core.model.LogAccess;
import me.candybox.core.utils.HttpUtil;
import me.candybox.core.vo.ResultVO;

@Slf4j
@Aspect
@Component
public class AccessLogAspect {

    // @Autowired
    // private LogAccessMapper logAccessMapper;

    @Pointcut("execution (* me.candybox..*Controller.*(..)) && !execution(* me.candybox..*ErrorController.*getErrorPath(..))")
    public void AccessLogAspectPointcut() {
    }

    @Around(value = "AccessLogAspectPointcut()")
    public Object doArround(ProceedingJoinPoint pjp) throws Exception {
        long stime = System.currentTimeMillis();
        log.debug("{} Before", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        LogAccess model = new LogAccess();
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest req = attributes.getRequest();
            log.debug("url={}",req.getServletPath());
            model.setUrl(req.getServletPath());
            model.setClassName(pjp.getSignature().getDeclaringTypeName());
            model.setMethodName(pjp.getSignature().getName());
            model.setCreateTime(new Date());
            model.setOptIp(HttpUtil.getClientIP(req));
            // //处理参数
            Object[] objects = pjp.getArgs();
            List<Object> objectList = new ArrayList<>();
            if(objects!=null){
                for (Object object:objects){
                    if(object==null){
                        continue;
                    }
                    if(object instanceof HttpServletRequest){
                        continue;
                    }
                    if(object instanceof HttpServletResponse){
                        continue;
                    }
                    objectList.add(object);
                }
            }
            log.debug("args={}", JSON.toJSONString(objectList));
            model.setOptContent(JSON.toJSONString(objectList));
            //运行方法
            Object o = pjp.proceed();
            //操作员信息
            if(TokenInfoThreadLocal.getTokenInfo()!=null){
                model.setCreateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
                model.setCreateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
                model.setCreateDeptId(TokenInfoThreadLocal.getTokenInfo().getDeptId());
                model.setCreateDeptName(TokenInfoThreadLocal.getTokenInfo().getDeptName());
                model.setTokenId(TokenInfoThreadLocal.getTokenInfo().getTokenId());
            }
            return o;
        } catch (Throwable throwable) {
            // throw new Exception(throwable.getMessage(), throwable);
            log.error("throwable={}",throwable);
            return new ResultVO(ConstantConfig.RESULT_STATUS_EXCEP,"异常错误");
        } finally {
            long etime = System.currentTimeMillis();
            model.setUseTime( (etime - stime));
            try {
                model.insert();
            } catch (Exception e) {
                log.error("Exception:", e);
            }
            log.debug("{} After 运行时间：{}ms", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName(), (etime - stime));
        }
    }
}
