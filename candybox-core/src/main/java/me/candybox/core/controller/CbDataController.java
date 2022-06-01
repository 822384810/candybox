package me.candybox.core.controller;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import me.candybox.core.ContextRefreshedListener;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.core.model.BaseModel;
import me.candybox.core.service.CbDataService;
import me.candybox.core.utils.JSONObjectUtil;
import me.candybox.core.utils.ValidatedUtil;
import me.candybox.core.vo.ResultVO;


/**
 * candybox 通用数据处理控制器
 */

@RestController
@RequestMapping("/api/core/")
public class CbDataController {
    @Autowired
    private CbDataService cbDataService;

    @Operation(summary ="数据新增")
    @PostMapping("/cbdata/{name}")
    public ResultVO insert(@Parameter(description="数据模块名称",required = true) @PathVariable(required = true) String name
    ,@Parameter(description="json格式数据",required = true) @RequestBody(required = true) JSONObject jsonObject){
        BaseModel<?> baseModel = (BaseModel<?>) ContextRefreshedListener.beans.get(name);
        if(baseModel==null){
            return new ResultVO(ConstantConfig.RESULT_STATUS_FAIL,"Model未定义");
        }
        ResultVO resultVO = new ResultVO();
        if(jsonObject!=null){
            jsonObject.put("status", 1);
            jsonObject.put("createTime", new Date());
            jsonObject.put("createUserId", TokenInfoThreadLocal.getTokenInfo().getUserId());
            jsonObject.put("createUserName", TokenInfoThreadLocal.getTokenInfo().getUserName());
            jsonObject.put("createDeptId", TokenInfoThreadLocal.getTokenInfo().getDeptId());
            jsonObject.put("createDeptName", TokenInfoThreadLocal.getTokenInfo().getDeptName());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        BaseModel<?> baseModelObj = (BaseModel<?>) objectMapper.convertValue(jsonObject, baseModel.getClass());
        //数据校验
        ValidatedUtil.validate(baseModelObj,resultVO);
        if(resultVO.getStatus()!=ConstantConfig.RESULT_STATUS_SUCC){
            return resultVO;
        }
        if(!cbDataService.insert(baseModelObj)){
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
            resultVO.setMsg("操作失败");
        }
        return resultVO;
    }

    @Operation(summary ="数据逻辑删除")
    @DeleteMapping("/cbdata/{name}")
    public ResultVO logicDelete(@Parameter(description="数据模块名称",required = true) @PathVariable(required = true) String name
    ,@Parameter(description="id",required = true) @RequestParam(required = true) String id){
        BaseModel<?> baseModel = (BaseModel<?>) ContextRefreshedListener.beans.get(name);
        if(baseModel==null){
            return new ResultVO(ConstantConfig.RESULT_STATUS_FAIL,"Model未定义");
        }
        ResultVO resultVO = new ResultVO();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("status", "0");
        jsonObject.put("updateTime", new Date());
        jsonObject.put("updateUserId", TokenInfoThreadLocal.getTokenInfo().getUserId());
        jsonObject.put("updateUserName", TokenInfoThreadLocal.getTokenInfo().getUserName());
        jsonObject.put("updateDeptId", TokenInfoThreadLocal.getTokenInfo().getDeptId());
        jsonObject.put("updateDeptName", TokenInfoThreadLocal.getTokenInfo().getDeptName());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        BaseModel<?> baseModelObj = (BaseModel<?>) objectMapper.convertValue(jsonObject,baseModel.getClass());     
        if(!cbDataService.updateById(baseModelObj)){
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
            resultVO.setMsg("操作失败");
        }
        return resultVO;
    }

    @Operation(summary ="数据修改")
    @PutMapping("/cbdata/{name}")
    public ResultVO update(@Parameter(description="数据模块名称",required = true) @PathVariable(required = true) String name
    ,@Parameter(description="id",required = true) @RequestParam(required = true) String id
    ,@Parameter(description="json格式数据",required = true) @RequestBody(required = true) JSONObject jsonObject){
        BaseModel<?> baseModel = (BaseModel<?>) ContextRefreshedListener.beans.get(name);
        if(baseModel==null){
            return new ResultVO(ConstantConfig.RESULT_STATUS_FAIL,"Model未定义");
        }
        ResultVO resultVO = new ResultVO();
        jsonObject.put("id", id);
        jsonObject.put("updateTime", new Date());
        jsonObject.put("updateUserId", TokenInfoThreadLocal.getTokenInfo().getUserId());
        jsonObject.put("updateUserName", TokenInfoThreadLocal.getTokenInfo().getUserName());
        jsonObject.put("updateDeptId", TokenInfoThreadLocal.getTokenInfo().getDeptId());
        jsonObject.put("updateDeptName", TokenInfoThreadLocal.getTokenInfo().getDeptName());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        BaseModel<?> baseModelObj = (BaseModel<?>) objectMapper.convertValue(jsonObject,baseModel.getClass());   
        //数据校验
        ValidatedUtil.validate(baseModelObj,resultVO);
        if(resultVO.getStatus()!=ConstantConfig.RESULT_STATUS_SUCC){
            return resultVO;
        }  
        if(!cbDataService.updateById(baseModelObj)){
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
            resultVO.setMsg("操作失败");
        }
        return resultVO;
    }

    @Operation(summary ="数据查询")
    @RequestMapping(value="/cbdata/{name}/list",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultVO selectList(@Parameter(description="数据模块名称",required = true) @PathVariable(required = true) String name
    ,@Parameter(description="查询条件JSONObject封装数据",required = false) @RequestBody(required = false) JSONObject jsonObject){
        BaseModel<?> baseModel = (BaseModel<?>) ContextRefreshedListener.beans.get(name);
        if(baseModel==null){
            return new ResultVO(ConstantConfig.RESULT_STATUS_FAIL,"Model未定义");
        }
        //转换为查询条件对象
        QueryWrapper<BaseModel<?>> queryWrapper = JSONObjectUtil.jsonObject2QueryWrapper(jsonObject);
        List<BaseModel<?>> list = cbDataService.selectList(baseModel,queryWrapper);
        return new ResultVO(list);
    }

    @Operation(summary ="分页数据查询")
    @RequestMapping(value="/cbdata/{name}/page",method = {RequestMethod.GET,RequestMethod.POST})
    public ResultVO selectPage(@Parameter(description="数据模块名称",required = true) @PathVariable(required = true) String name
    ,@Parameter(description="当前页",required = true) @RequestParam(defaultValue = "1",required = true) int page
    ,@Parameter(description="页大小",required = true) @RequestParam(defaultValue = "10",required = true) int perPage
    ,@Parameter(description="查询条件JSONObject封装数据",required = false) @RequestBody(required = false) JSONObject jsonObject){
        BaseModel<?> baseModel = (BaseModel<?>) ContextRefreshedListener.beans.get(name);
        if(baseModel==null){
            return new ResultVO(ConstantConfig.RESULT_STATUS_FAIL,"Model未定义");
        }
        //转换为查询条件对象
        QueryWrapper<BaseModel<?>> queryWrapper = JSONObjectUtil.jsonObject2QueryWrapper(jsonObject);
        IPage<BaseModel<?>> iPage = cbDataService.selectPage(baseModel,new Page<BaseModel<?>>(JSONObjectUtil.getPage(jsonObject, page),JSONObjectUtil.getPerPage(jsonObject, perPage)),queryWrapper);
        ResultVO resultVO = new ResultVO(iPage);
        return resultVO;
    }


    


}
