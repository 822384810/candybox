package me.candybox.core.utils;


import java.util.Date;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import me.candybox.core.config.TokenInfoThreadLocal;
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
                continue;
            }
            if(objR instanceof JSONObject){
                real.put(key,formatKey((JSONObject) objR, upper));
                continue;
            }
            if(objR instanceof JSONArray){
                JSONArray jsonA =new JSONArray();
                for(Object objA :(JSONArray) objR){
                        jsonA.add(formatKey((JSONObject) objA,upper));
                }
                real.put(key, jsonA);
                continue;
            }
            real.put(key, objR);
        }
        return real;
    }


    /**
     * jsonObject 转 QueryWrapper（com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     * orderAsc、orderDesc排序，值为列名
     * eq-列名，等于条件，值为条件值
     * like-left-列名、like-right-列名、like-列名，模糊匹配条件，值为条件值
     * lt-列名、le-列名、gt-列名、ge-列名，小于大于条件，值为条件
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
                    //分页参数不处理
                    if("page".equals(key)||"perPage".equals(key)||"per_page".equals(key)){
                        return;
                    }
                    //正序
                    if("orderAsc".equals(key)||"order_asc".equals(key)){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.orderByAsc(it.contains("_")? StrUtil.toCamelCase(it): StrUtil.toUnderlineCase(it));
                        return;
                    }
                    //倒序
                    if("orderDesc".equals(key)||"order_desc".equals(key)){
                        String it=jsonObjectFormat.get(key).toString();
                        queryWrapper.orderByDesc(it.contains("_")? StrUtil.toCamelCase(it): StrUtil.toUnderlineCase(it));
                        return;
                    }
                    //等于
                    if(key.indexOf("eq-")==0){
                        queryWrapper.eq(key.replaceFirst("eq-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //小于
                    if(key.indexOf("lt-")==0){
                        queryWrapper.lt(key.replaceFirst("lt-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //小于等于
                    if(key.indexOf("le-")==0){
                        queryWrapper.le(key.replaceFirst("le-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //大于
                    if(key.indexOf("gt-")==0){
                        queryWrapper.gt(key.replaceFirst("gt-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //大于等于
                    if(key.indexOf("ge-")==0){
                        queryWrapper.ge(key.replaceFirst("ge-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //左like
                    if(key.indexOf("like-left-")==0){
                        queryWrapper.likeLeft(key.replaceFirst("like-left-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //右like
                    if(key.indexOf("like-right-")==0){
                        queryWrapper.likeRight(key.replaceFirst("like-right-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //like
                    if(key.indexOf("like-")==0){
                        queryWrapper.like(key.replaceFirst("like-", ""), jsonObjectFormat.get(key));
                        return;
                    }
                    //默认等于
                    queryWrapper.eq(key, jsonObjectFormat.get(key));
                }
            });
        }
        return queryWrapper;
    }

    /**
     * jsonObject 转 Query(org.springframework.data.mongodb.core.query.Query)
     * orderAsc、orderDesc排序，值为列名
     * eq-列名，等于条件，值为条件值
     * like-left-列名、like-right-列名、like-列名，模糊匹配条件，值为条件值
     * lt-列名、le-列名、gt-列名、ge-列名，小于大于条件，值为条件
     * 列名，等于条件，值为条件值
     * @param jsonObject
     * @return
     */
    public static Query jsonObject2MongodbQuery(JSONObject jsonObject){
        Query query = new Query();
        if(jsonObject!=null){
            jsonObject.keySet().forEach(key->{
                if(ObjectUtil.isNotEmpty(jsonObject.get(key))){
                    //分页参数不处理
                    if("page".equals(key)||"perPage".equals(key)||"per_page".equals(key)){
                        return;
                    }
                    if(key.indexOf("-createTime")!=-1||key.indexOf("-updateTime")!=-1){
                        Object object = jsonObject.get(key);
                        if(!StrUtil.isBlankIfStr(object)&&StrUtil.isNumeric(object.toString())){
                            jsonObject.put(key, new Date(object.toString().length()==10?Long.parseLong(object.toString())*1000:Long.parseLong(object.toString())));
                        }else{
                            return;
                        }
                    }
                    //正序
                    if("orderAsc".equals(key)||"order_asc".equals(key)){
                        String it=jsonObject.get(key).toString();
                        query.with(Sort.by(Sort.Direction.ASC, it));
                        return;
                    }
                    //倒序
                    if("orderDesc".equals(key)||"order_desc".equals(key)){
                        String it=jsonObject.get(key).toString();
                        query.with(Sort.by(Sort.Direction.DESC, it));
                        return;
                    }
                    //等于
                    if(key.indexOf("eq-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("eq-", "")).is(jsonObject.get(key)));
                        return;
                    }
                    //小于
                    if(key.indexOf("lt-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("lt-", "")).lt(jsonObject.get(key)));
                        return;
                    }
                    //小于等于
                    if(key.indexOf("le-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("le-", "")).lte(jsonObject.get(key)));
                        return;
                    }
                    //大于
                    if(key.indexOf("gt-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("gt-", "")).gt(jsonObject.get(key)));
                        return;
                    }
                    //大于等于
                    if(key.indexOf("ge-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("ge-", "")).gte(jsonObject.get(key)));
                        return;
                    }
                    //左like
                    if(key.indexOf("like-left-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("like-left-", "")).regex(jsonObject.get(key).toString()+"$"));
                        return;
                    }
                    //右like
                    if(key.indexOf("like-right-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("like-right-", "")).regex("^"+jsonObject.get(key).toString()));
                        return;
                    }
                    //like
                    if(key.indexOf("like-")==0){
                        query.addCriteria(Criteria.where(key.replaceFirst("like-", "")).regex(jsonObject.get(key).toString()));
                        return;
                    }
                    //默认等于
                    query.addCriteria(Criteria.where(key).is(jsonObject.get(key)));
                }
            });
        }
        return query;
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


    /**
     * 设置更新用户相关信息
     * @param jsonObject
     */
    public static void setCreateUser(JSONObject jsonObject){
        jsonObject.put("createTime", new Date());
        jsonObject.put("createUserId", TokenInfoThreadLocal.getTokenInfo().getUserId());
        jsonObject.put("createUserName", TokenInfoThreadLocal.getTokenInfo().getUserName());
        jsonObject.put("createDeptId", TokenInfoThreadLocal.getTokenInfo().getDeptId());
        jsonObject.put("createDeptName", TokenInfoThreadLocal.getTokenInfo().getDeptName());
    }

    /**
     * 设置更新用户相关信息
     * @param jsonObject
     */
    public static void setUpdateUser(JSONObject jsonObject){
        jsonObject.put("updateTime", new Date());
        jsonObject.put("updateUserId", TokenInfoThreadLocal.getTokenInfo().getUserId());
        jsonObject.put("updateUserName", TokenInfoThreadLocal.getTokenInfo().getUserName());
        jsonObject.put("updateDeptId", TokenInfoThreadLocal.getTokenInfo().getDeptId());
        jsonObject.put("updateDeptName", TokenInfoThreadLocal.getTokenInfo().getDeptName());
    }
    
}
