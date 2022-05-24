package me.candybox.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import me.candybox.core.annotation.AccessTokenOauth;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.model.BaseModel;
import me.candybox.core.service.CbDataService;
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
import me.candybox.user.vo.UserDeptInfoTreeVO;

@Slf4j
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private CbDataService cbDataService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

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
            Cookie cookie = new Cookie(ConstantConfig.accessTokenKey, tokenInfoVO.getTokenId());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            rep.addCookie(cookie);
            redisUtil.set(ConstantConfig.accessTokenKey+":"+tokenInfoVO.getTokenId(), JSON.toJSONString(tokenInfoVO));
            redisUtil.expireSeconds(ConstantConfig.accessTokenKey+":"+tokenInfoVO.getTokenId(), 1*60*60*12);
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
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.orderByAsc("create_time");
        List<BaseModel> list = cbDataService.selectList(new UserDeptInfo(),queryWrapper);
        if(list==null){
            return new ResultVO();
        }
        List<UserDeptInfoTreeVO> userDeptInfoTreeVOs = new ArrayList<>();
        list.forEach(item->{
            UserDeptInfo userDeptInfo = (UserDeptInfo)item;
            UserDeptInfoTreeVO userDeptInfoTreeVO = new UserDeptInfoTreeVO();
            BeanUtils.copyProperties(userDeptInfo, userDeptInfoTreeVO);
            userDeptInfoTreeVO.setValue(JSON.parseObject("{'id':'"+userDeptInfoTreeVO.getId()+"','name':'"+userDeptInfoTreeVO.getName()+"'}"));
            userDeptInfoTreeVOs.add(userDeptInfoTreeVO);
        });
        return new ResultVO(userDeptInfoTreeVOs);
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
    public ResultVO selectUserPage(@Parameter(description="当前页",required = true) @RequestParam(defaultValue = "1",required = true) int pageNo
    ,@Parameter(description="页大小",required = true) @RequestParam(defaultValue = "10",required = true) int pageSize
    ,@Parameter(description="查询条件JSONObject封装数据",required = false) @RequestBody(required = false) JSONObject jsonObject){
        //转换为查询条件对象
        QueryWrapper<UserInfo> queryWrapper = JSONObjectUtil.jsonObject2QueryWrapper(jsonObject);
        IPage<UserInfo> iPage = userService.selectPage(new Page<UserInfo>(pageNo,pageSize),queryWrapper);
        ResultVO resultVO = new ResultVO(iPage);
        return resultVO;
    }

    
}
