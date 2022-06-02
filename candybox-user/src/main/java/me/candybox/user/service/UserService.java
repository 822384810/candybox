package me.candybox.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.core.model.BaseModel;
import me.candybox.core.service.CbDataService;
import me.candybox.core.vo.TokenInfoVO;
import me.candybox.user.mapper.LogLoginMapper;
import me.candybox.user.mapper.UserDeptInfoMapper;
import me.candybox.user.mapper.UserInfoMapper;
import me.candybox.user.model.LogLogin;
import me.candybox.user.model.UserDeptInfo;
import me.candybox.user.model.UserInfo;
import me.candybox.user.vo.LoginInfoVo;
import me.candybox.user.vo.UserDeptInfoTreeVO;

/**
 * 用户组织结构业务类
 */

@Service
public class UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserDeptInfoMapper userDeptInfoMapper;
    @Autowired
    private LogLoginMapper logLoginMapper;
    @Autowired
    private CbDataService cbDataService;


    /**
     * 用户登录
     */
    public TokenInfoVO login(LoginInfoVo loginInfoVo){
        List<UserInfo> userInfos =  userInfoMapper.selectListUserName(loginInfoVo.getUserName(),loginInfoVo.getUserName());
        if(userInfos==null||userInfos.size()!=1){
            return null;
        }
        UserInfo userInfo = userInfos.get(0);
        if(!SmUtil.sm3().digestHex(loginInfoVo.getUserPwd()).equals(userInfo.getPassword())){
            return null;
        }
        userInfo.setLastLoginTime(new Date());
        if(userInfo.getLoginCount()==null){
            userInfo.setLoginCount((long)0);
        }else{
            userInfo.setLoginCount(userInfo.getLoginCount()+1);
        }
        userInfoMapper.updateById(userInfo);
        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        tokenInfoVO.setDeptId(userInfo.getDeptId());
        tokenInfoVO.setDeptName(userInfo.getDeptName());
        tokenInfoVO.setUserId(userInfo.getId());
        tokenInfoVO.setUserName(userInfo.getName());
        tokenInfoVO.setTokenId(IdUtil.simpleUUID());
        tokenInfoVO.setTokenType("user");
        //登录日志
        LogLogin logLogin = new LogLogin();
        BeanUtils.copyProperties(tokenInfoVO, logLogin);
        logLogin.setLoginDev(loginInfoVo.getLoginDev());
        logLogin.setLoginIp(loginInfoVo.getLoginIp());
        logLogin.setLoginSource(loginInfoVo.getLoginSource());
        logLogin.setLoginTime(new Date());
        logLoginMapper.insert(logLogin);

        return tokenInfoVO;
    }


    /**
     * 单位信息修改（同步修改用户信息）
     * @param userDeptInfo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveDept(UserDeptInfo userDeptInfo){
        userDeptInfo.setUpdateTime(new Date());
        userDeptInfo.setUpdateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
        userDeptInfo.setUpdateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
        int ret = userDeptInfoMapper.updateById(userDeptInfo);
        UserInfo userInfo = new UserInfo();
        userInfo.setDeptName(userDeptInfo.getName());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", userDeptInfo.getId());
        ret+=userInfoMapper.update(userInfo, queryWrapper);
        return ret;
    }

    /**
     * 用户保存
     * @param userInfo
     * @return
     */
    public int saveUser(UserInfo userInfo){
        List<UserInfo> userInfos =  userInfoMapper.selectListUserName(userInfo.getNickName(),userInfo.getMobilePhone());
        if(StrUtil.isBlank(userInfo.getId())){
            //用户名或手机号码已存在
            if(userInfos!=null&&userInfos.size()>0){
                return 2;
            }
            userInfo.setPassword(SmUtil.sm3().digestHex(userInfo.getPassword()));
            userInfo.setCreateTime(new Date());
            userInfo.setCreateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
            userInfo.setCreateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
            userInfo.setStatus(1);
            return userInfoMapper.insert(userInfo);
        }else{
            userInfo.setUpdateTime(new Date());
            userInfo.setUpdateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
            userInfo.setUpdateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
            //修改后的用户名或手机号码已存在
            if(userInfos!=null&&userInfos.size()>1){
                return 3;
            }
            if(userInfos!=null&&userInfos.size()==1){
                if(userInfos.get(0).getId().equals(userInfo.getId())){
                    execUserInfoItem(userInfos.get(0), userInfo);
                    return userInfoMapper.updateById(userInfo);
                }else{
                    //修改后的用户名或手机号码已存在
                    return 4;
                }
            }
            if(userInfos!=null&&userInfos.size()<1){
                UserInfo tUserInfo = userInfoMapper.selectById(userInfo.getId());
                if(tUserInfo==null){
                    //修改id不存在
                    return 5;
                }
                execUserInfoItem(tUserInfo, userInfo);
                return userInfoMapper.updateById(userInfo);
            }

        }
        return 0;
    }


    /**
     * 处理用户信息特殊字段
     * @param oldInfo
     * @param newInfo
     */
    private void execUserInfoItem(UserInfo oldInfo,UserInfo newInfo){
        if(StrUtil.isBlank(newInfo.getPassword())){
            newInfo.setPassword(null);
        }else{
            newInfo.setPassword(SmUtil.sm3().digestHex(newInfo.getPassword()));
        }
        if(!StrUtil.isBlank(newInfo.getMobilePhone())){
            if(DesensitizedUtil.mobilePhone(oldInfo.getMobilePhone()).equals(newInfo.getMobilePhone())){
                newInfo.setMobilePhone(null);
            }
        }
        if(!StrUtil.isBlank(newInfo.getIdNumber())){
            if(DesensitizedUtil.idCardNum(oldInfo.getIdNumber(),1,4).equals(newInfo.getIdNumber())){
                newInfo.setIdNumber(null);
            }
        }
    }


    /**
     * 用户分页查询
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<BaseModel<?>> selectPage (IPage<BaseModel<?>> page,Wrapper<BaseModel<?>> queryWrapper){
        IPage<BaseModel<?>> iPage = cbDataService.selectPage(new UserInfo(),page,queryWrapper);
        if(iPage!=null){
            iPage.getRecords().forEach(item->{
                UserInfo userInfo = (UserInfo)item;
                userInfo.setMobilePhone(DesensitizedUtil.mobilePhone(userInfo.getMobilePhone()));
                userInfo.setIdNumber(DesensitizedUtil.idCardNum(userInfo.getIdNumber(),1,4));
                userInfo.setPassword("");
            });
        }
        return iPage;
    }


    /**
     * 单位树查询
     * @param parentId
     * @return
     */
    public List<UserDeptInfoTreeVO> selectDeptTree(String parentId){
        QueryWrapper<BaseModel<?>> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.orderByAsc("create_time");
        List<BaseModel<?>> list = cbDataService.selectList(new UserDeptInfo(),queryWrapper);
        if(list==null){
            return null;
        }
        List<UserDeptInfoTreeVO> userDeptInfoTreeVOs = new ArrayList<>();
        list.forEach(item->{
            UserDeptInfo userDeptInfo = (UserDeptInfo)item;
            UserDeptInfoTreeVO userDeptInfoTreeVO = new UserDeptInfoTreeVO();
            BeanUtils.copyProperties(userDeptInfo, userDeptInfoTreeVO);
            userDeptInfoTreeVO.setValue(JSON.parseObject("{'id':'"+userDeptInfoTreeVO.getId()+"','name':'"+userDeptInfoTreeVO.getName()+"'}"));
            userDeptInfoTreeVOs.add(userDeptInfoTreeVO);
        });
        return userDeptInfoTreeVOs;
    }
    
}
