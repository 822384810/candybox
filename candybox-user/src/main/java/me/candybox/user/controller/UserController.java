package me.candybox.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import me.candybox.core.annotation.AccessTokenOauth;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.model.BaseModel;
import me.candybox.core.utils.HttpUtil;
import me.candybox.core.utils.JSONObjectUtil;
import me.candybox.core.utils.RedisUtil;
import me.candybox.core.utils.ValidatedUtil;
import me.candybox.core.vo.ResultVO;
import me.candybox.core.vo.TokenInfoVO;
import me.candybox.user.model.UserDeptInfo;
import me.candybox.user.model.UserInfo;
import me.candybox.user.service.UserService;
import me.candybox.user.vo.LoginInfoVo;


@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;


    @AccessTokenOauth(value = false)
    @Operation(summary ="清除token")
    @RequestMapping(value={"/token"},method = {RequestMethod.DELETE},produces="application/json;charset=UTF-8")
    public ResultVO clearToken(HttpServletRequest request, HttpServletResponse response){
        String accessToken=HttpUtil.getCookie(request, ConstantConfig.ACCESS_TOKEN_KEY);
        if(accessToken==null){
            accessToken=request.getHeader(ConstantConfig.ACCESS_TOKEN_KEY);
        }
        if(accessToken==null){
            accessToken=request.getParameter(ConstantConfig.ACCESS_TOKEN_KEY);
        }
        if(!StrUtil.isBlank(accessToken)){
            if(redisUtil.hasKey(ConstantConfig.ACCESS_TOKEN_KEY+":"+accessToken)){
                redisUtil.del(ConstantConfig.ACCESS_TOKEN_KEY+":"+accessToken);
            }
        }
        return new ResultVO();
    }

    @AccessTokenOauth(value = false)
    @Operation(summary ="web登录")
    @PostMapping("/web/login")
    public ResultVO webLogin(@Parameter(description="登录信息",required = true) @RequestBody(required = true) LoginInfoVo loginInfoVo
    ,HttpServletRequest req,HttpServletResponse rep){
        ResultVO resultVO = new ResultVO();
        //数据校验
        ValidatedUtil.validate(loginInfoVo,resultVO);
        if(resultVO.getStatus()!=ConstantConfig.RESULT_STATUS_SUCC){
            return resultVO;
        }
        //获取登录相关信息
        loginInfoVo.setLoginIp(HttpUtil.getClientIP(req));
        loginInfoVo.setLoginDev(req.getHeader("User-Agent"));
        loginInfoVo.setLoginSource("web");

        TokenInfoVO tokenInfoVO = userService.login(loginInfoVo);
        if(tokenInfoVO!=null){
            Cookie cookie = new Cookie(ConstantConfig.ACCESS_TOKEN_KEY, tokenInfoVO.getTokenId());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            rep.addCookie(cookie);
            redisUtil.set(ConstantConfig.ACCESS_TOKEN_KEY+":"+tokenInfoVO.getTokenId(), JSON.toJSONString(tokenInfoVO));
            redisUtil.expireSeconds(ConstantConfig.ACCESS_TOKEN_KEY+":"+tokenInfoVO.getTokenId(), 1*60*60*2);
            resultVO.setData(tokenInfoVO);
            return resultVO;
        }
        resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
        resultVO.setMsg("用户名或密码错误");
        return resultVO;
    }


    @Operation(summary ="单位修改保存")
    @RequestMapping(value = "/dept",method = {RequestMethod.PUT})
    public ResultVO saveDept(@Parameter(description="登录信息",required = true) @RequestBody(required = true) UserDeptInfo userDeptInfo
    ,@Parameter(description="id",required = false) @RequestParam(required = false) String id){
        ResultVO resultVO = new ResultVO();
        //数据校验
        ValidatedUtil.validate(userDeptInfo,resultVO);
        if(resultVO.getStatus()!=ConstantConfig.RESULT_STATUS_SUCC){
            return resultVO;
        }
        userDeptInfo.setId(id);
        int ret = userService.saveDept(userDeptInfo);
        if(ret>=1){
            return resultVO;
        }
        resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
        resultVO.setMsg("操作失败");
        return resultVO;
    }

    @Operation(summary ="组织机构树查询")
    @GetMapping("/dept/tree")
    public ResultVO selectDeptTree(@Parameter(description="父id",required = true) @RequestParam(defaultValue = "0") String parentId){
        return new ResultVO(userService.selectDeptTree(parentId));
    }


    @Operation(summary ="用户保存")
    @RequestMapping(value = "/user",method = {RequestMethod.POST,RequestMethod.PUT})
    public ResultVO saveUser(@Parameter(description="登录信息",required = true) @RequestBody(required = true) UserInfo userInfo
    ,@Parameter(description="id",required = false) @RequestParam(required = false) String id){
        ResultVO resultVO = new ResultVO();
        //数据校验
        ValidatedUtil.validate(userInfo,resultVO);
        if(resultVO.getStatus()!=ConstantConfig.RESULT_STATUS_SUCC){
            return resultVO;
        }
        userInfo.setId(id);
        int ret = userService.saveUser(userInfo);
        if(ret==1){
            return resultVO;
        }
        resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
        resultVO.setMsg("操作失败");
        return resultVO;
    }


    @Operation(summary ="用户查询")
    @RequestMapping(value = "/user/page",method = {RequestMethod.POST,RequestMethod.GET})
    public ResultVO selectUserPage(@Parameter(description="当前页",required = true) @RequestParam(defaultValue = "1",required = true) int page
    ,@Parameter(description="页大小",required = true) @RequestParam(defaultValue = "10",required = true) int perPage
    ,@Parameter(description="查询条件JSONObject封装数据",required = false) @RequestBody(required = false) JSONObject jsonObject){
        //转换为查询条件对象
        QueryWrapper<BaseModel<?>> queryWrapper = JSONObjectUtil.jsonObject2QueryWrapper(jsonObject);
        IPage<BaseModel<?>> iPage = userService.selectPage(new Page<BaseModel<?>>(JSONObjectUtil.getPage(jsonObject, page),JSONObjectUtil.getPage(jsonObject, perPage)),queryWrapper);
        ResultVO resultVO = new ResultVO(iPage);
        return resultVO;
    }

    
}
