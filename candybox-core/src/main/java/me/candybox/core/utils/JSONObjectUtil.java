package me.candybox.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public class JSONObjectUtil {
    /**
     * key驼峰格式转换
     * @param json JSONObject
     * @param upper 首字母大小写
     * @return
     */
    public static JSONObject formatKey(final JSONObject json,boolean upper){
        JSONObject real =new JSONObject();
        for(String it : json.keySet()){
            Object objR = json.get(it);
            //转换为驼峰格式
            //转换为下划线⽅式
            String key = it.contains("_")? StrUtil.toCamelCase(it): StrUtil.toUnderlineCase(it);
            //⾸字母⼤写或者⼩写
            key = upper ? StrUtil.upperFirst(key): StrUtil.lowerFirst(key);
            if(objR instanceof String){
                real.put(key, objR);
            }
            if(objR instanceof JSONObject){
                real.put(key,formatKey((JSONObject) objR, upper));
            }
            if(objR instanceof JSONArray){
                JSONArray jsonA =new JSONArray();
                for(Object objA :(JSONArray) objR){
                        jsonA.add(formatKey((JSONObject) objA,upper));
                }
                real.put(key, jsonA);
            }
        }
        return real;
    }


    /**
     * jsonObject 转 QueryWrapper
     * @param jsonObject
     * @return
     */
    public static QueryWrapper jsonObject2QueryWrapper(JSONObject jsonObject){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if(jsonObject!=null){
            JSONObject jsonObjectFormat = JSONObjectUtil.formatKey(jsonObject, false);
            jsonObjectFormat.keySet().forEach(key->{
                if(ObjectUtil.isNotEmpty(jsonObjectFormat.get(key))){
                    if("orderAsc".equals(key)||"order_asc".equals(key)){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.orderByAsc(it.contains("_")? StrUtil.toCamelCase(it): StrUtil.toUnderlineCase(it));
                        return;
                    }
                    if("orderDesc".equals(key)||"order_desc".equals(key)){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.orderByDesc(it.contains("_")? StrUtil.toCamelCase(it): StrUtil.toUnderlineCase(it));
                        return;
                    }
                    if(key.indexOf("eq-")==0){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.eq(key.replaceFirst("eq-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    if(key.indexOf("like-left-")==0){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.likeLeft(key.replaceFirst("like-left-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    if(key.indexOf("like-right-")==0){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.likeRight(key.replaceFirst("like-right-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    if(key.indexOf("like-")==0){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.like(key.replaceFirst("like-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    queryWrapper.eq(key, jsonObjectFormat.get(key));
                }
            });
        }
        return queryWrapper;
    }
    
}
