package me.candybox.core.utils;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import me.candybox.core.model.BaseModel;

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
     * jsonObject 转 QueryWrapper（com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     * orderAsc、orderDesc排序，值为列名
     * eq-列名，等于条件，值为条件值
     * like-left-列名、like-right-列名、like-列名，模糊匹配条件，值为条件值
     * 列名，等于条件，值为条件值
     * @param jsonObject
     * @return
     */
    public static QueryWrapper<BaseModel<?>> jsonObject2QueryWrapper(JSONObject jsonObject){
        QueryWrapper<BaseModel<?>> queryWrapper = new QueryWrapper<>();
        if(jsonObject!=null){
            JSONObject jsonObjectFormat = JSONObjectUtil.formatKey(jsonObject, false);
            jsonObjectFormat.keySet().forEach(key->{
                if(ObjectUtil.isNotEmpty(jsonObjectFormat.get(key))){
                    if("page".equals(key)||"perPage".equals(key)||"per_page".equals(key)){
                        return;
                    }
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
                        // String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.eq(key.replaceFirst("eq-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    if(key.indexOf("like-left-")==0){
                        // String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.likeLeft(key.replaceFirst("like-left-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    if(key.indexOf("like-right-")==0){
                        // String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.likeRight(key.replaceFirst("like-right-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    if(key.indexOf("like-")==0){
                        // String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.like(key.replaceFirst("like-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    queryWrapper.eq(key, jsonObjectFormat.get(key));
                }
            });
        }
        return queryWrapper;
    }



    /**
     * 获取页码
     * @param jsonObject
     * @param page
     * @return
     */
    public static int getPage(JSONObject jsonObject,int page){
        if(jsonObject!=null){
            if(NumberUtil.isInteger(jsonObject.getString("page"))){
                page=jsonObject.getIntValue("page");
            }
        }
        return page;
    }

    /**
     * 获取页大小
     * @param jsonObject
     * @param perPage
     * @return
     */
    public static int getPerPage(JSONObject jsonObject,int perPage){
        if(jsonObject!=null){
            if(NumberUtil.isInteger(jsonObject.getString("perPage"))){
                perPage=jsonObject.getIntValue("perPage");
            }
        }
        return perPage;
    }
    
}
