package me.candybox.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import me.candybox.core.config.ConstantConfig;
import me.candybox.core.vo.ResultVO;
public class ValidatedUtil {

    private static final Validator VALIDATOR=Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 获取表单验证消息
     * @param bindingResult 表单验证消息
     * @param resultVO 返回对象
     * @return Boolean true or false
     */
    public static Boolean validate(BindingResult bindingResult,ResultVO resultVO){
        if(bindingResult==null){
            return true;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if(bindingResult.hasErrors()){
            List<FieldError> errors=bindingResult.getFieldErrors();
            for(FieldError fieldError:errors){
                String tmp = fieldError.getDefaultMessage();
                try {
                    tmp=new String(tmp.getBytes("ISO-8859-1"));
                 } catch (UnsupportedEncodingException e) {
                     tmp="";
                 }
                if(stringBuffer.indexOf(tmp)==-1) {
                    if(stringBuffer.length()<=0){
                        stringBuffer.append(tmp);
                    }else{
                        stringBuffer.append("\r\n"+tmp);
                    }
                }
            }
            if(resultVO==null){
                resultVO=new ResultVO();
            }
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_PARA_FAIL);
            resultVO.setMsg(stringBuffer.toString());
            return false;
        }
        return true;
    }


    /**
     * 手动验证对象输入
     * @param <T>
     * @param t
     * @param resultVO
     * @return
     */
    public static <T> Boolean validate(T t,ResultVO resultVO){
        Set<ConstraintViolation<T>> constraints = VALIDATOR.validate(t);
        if(constraints.size()>0){
            StringBuffer stringBuffer = new StringBuffer();
            for (ConstraintViolation<T> constraintViolation : constraints) {
                String tmp = constraintViolation.getMessage();
                try {
                    tmp=new String(tmp.getBytes("ISO-8859-1"));
                 } catch (UnsupportedEncodingException e) {
                     tmp="";
                 }
                if(stringBuffer.indexOf(tmp)==-1) {
                    if(stringBuffer.length()<=0){
                        stringBuffer.append(tmp);
                    }else{
                        stringBuffer.append("\r\n"+tmp);
                    }
                }
            }
            if(resultVO==null){
                resultVO=new ResultVO();
            }
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_PARA_FAIL);
            resultVO.setMsg(stringBuffer.toString());
            return false;
            //throw new ValidateException(stringBuffer.toString());
        }
        return true;


    }


    /**
     * 手动验证对象输入
     * @param <T>
     * @param t
     * @param resultVO
     * @param groups
     * @return
     */
    public static <T> Boolean validate(T t,ResultVO resultVO,Class<?>...groups){
        Set<ConstraintViolation<T>> constraints = VALIDATOR.validate(t,groups);
        if(constraints.size()>0){
            StringBuffer stringBuffer = new StringBuffer();
            for (ConstraintViolation<T> constraintViolation : constraints) {
                String tmp = constraintViolation.getMessage();
                try {
                   tmp=new String(tmp.getBytes("ISO-8859-1"));
                } catch (UnsupportedEncodingException e) {
                    tmp="";
                }
                if(stringBuffer.indexOf(tmp)==-1) {
                    if(stringBuffer.length()<=0){
                        stringBuffer.append(tmp);
                    }else{
                        stringBuffer.append("\r\n"+tmp);
                    }
                }
            }
            if(resultVO==null){
                resultVO=new ResultVO();
            }
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_PARA_FAIL);
            resultVO.setMsg(stringBuffer.toString());
            return false;
        }
        return true;

    }





}
