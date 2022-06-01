package me.candybox.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.user.mapper.UserResInfoMapper;
import me.candybox.user.mapper.UserRoleResRelationMapper;
import me.candybox.user.model.UserResInfo;
import me.candybox.user.model.UserRoleResRelation;
import me.candybox.user.vo.UserResInfoChildrenVO;
import me.candybox.user.vo.UserResInfoMenuVO;
import me.candybox.user.vo.UserResInfoPageVO;
import me.candybox.user.vo.UserResInfoTreeVO;
import me.candybox.user.vo.UserRoleResRelationVO;

/**
 * 资源业务类
 */
@Service
public class ResService {

    @Autowired
    private UserRoleResRelationMapper userRoleResRelationMapper;
    @Autowired
    private UserResInfoMapper userResInfoMapper;
   

    /**
     * 资源角色关系保存
     * @param userRoleRelationVO
     * @return
     */
    public int save(UserRoleResRelationVO userRoleResRelationVO){
        int ret = 1;
        UserRoleResRelation userRoleResRelation = new UserRoleResRelation();
        userRoleResRelation.setResId(userRoleResRelationVO.getResId());
        userRoleResRelation.setRoleId(userRoleResRelationVO.getRoleId());

        QueryWrapper<UserRoleResRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("res_id", userRoleResRelation.getResId());
        queryWrapper.eq("role_id", userRoleResRelation.getRoleId());
        queryWrapper.eq("status", 1);
        if(userRoleResRelationVO.isUserRoleResRelCheck()){
            userRoleResRelation.setUpdateTime(new Date());
            userRoleResRelation.setUpdateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
            userRoleResRelation.setUpdateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
            userRoleResRelation.setStatus(0);
            ret = userRoleResRelationMapper.update(userRoleResRelation, queryWrapper);
        }else{
            long count = userRoleResRelationMapper.selectCount(queryWrapper);
            if(count<=0){
                userRoleResRelation.setCreateTime(new Date());
                userRoleResRelation.setCreateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
                userRoleResRelation.setCreateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
                userRoleResRelation.setStatus(1);
                ret = userRoleResRelationMapper.insert(userRoleResRelation);
            }
        }
        return ret;
    }


    /**
     *  查询所属菜单资源
     * @return
     */
    public List<UserResInfoTreeVO> selectTreeAll(){
        QueryWrapper<UserResInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("type", 1);
        queryWrapper.orderByAsc("sort");
        List<UserResInfo>  list = userResInfoMapper.selectList(queryWrapper);
        if(list==null){
            return null;
        }
        List<UserResInfoTreeVO> userResInfoTreeVOs = new ArrayList<>();
        list.forEach(item->{
            if("0".equals(item.getParentId())){
                UserResInfoTreeVO userResInfoTreeVO = new UserResInfoTreeVO();
                BeanUtils.copyProperties(item, userResInfoTreeVO);
                execResTree(userResInfoTreeVO,item.getId(),list);
                userResInfoTreeVOs.add(userResInfoTreeVO);
            }
        });
        return userResInfoTreeVOs;
    }

    /**
     * 资源树递归方法
     * @param userResInfoTreeVO
     * @param parentId
     * @param list
     */
    private void execResTree(UserResInfoTreeVO userResInfoTreeVO,String parentId,List<UserResInfo> list){
        List<UserResInfoTreeVO> tUserResInfoTreeVOs = new ArrayList<>();
        list.forEach(item->{
            if(parentId.equals(item.getParentId())){
                UserResInfoTreeVO tUserResInfoTreeVO = new UserResInfoTreeVO();
                BeanUtils.copyProperties(item, tUserResInfoTreeVO);
                execResTree(userResInfoTreeVO,item.getId(),list);
                tUserResInfoTreeVOs.add(tUserResInfoTreeVO);
            }
            
        });
        if(tUserResInfoTreeVOs.size()<=0){
            return;
        }
        userResInfoTreeVO.setChildren(tUserResInfoTreeVOs);
    }



    /**
     * 按用户生成菜单
     * @param userId
     * @return
     */
    public UserResInfoMenuVO selectMenuByUserAll(String userId){
        UserResInfoMenuVO userResInfoMenuVO = new UserResInfoMenuVO();
        List<UserResInfoPageVO> userResInfoPageVOs = new ArrayList<>();
        List<UserResInfo> userResInfos =  userResInfoMapper.selectUserResByUser(userId);
        if(userResInfos==null){
            return userResInfoMenuVO;
        }
        UserResInfoPageVO userResInfoPageVO = new UserResInfoPageVO();
        userResInfoPageVOs.add(userResInfoPageVO);

        List<UserResInfoChildrenVO> userResInfoChildrenVOs = new ArrayList<>();
        userResInfos.forEach(item->{
            if("0".equals(item.getParentId())){
                UserResInfoChildrenVO userResInfoChildrenVO = new UserResInfoChildrenVO();
                userResInfoChildrenVO.setLabel(item.getName());
                userResInfoChildrenVO.setSchemaApi(item.getApiUrl());
                if(!StrUtil.isBlank(item.getDescript())){
                    JSONObject jsonObject = JSON.parseObject(item.getDescript());
                    userResInfoChildrenVO.setIcon(jsonObject.getString("icon"));
                    userResInfoChildrenVO.setUrl(jsonObject.getString("url"));
                    userResInfoChildrenVO.setLink(jsonObject.getString("link"));
                    userResInfoChildrenVO.setIsDefaultPage(jsonObject.getString("default"));
                    userResInfoChildrenVO.setSchema(jsonObject.getString("schema"));
                }
                userResInfoChildrenVOs.add(userResInfoChildrenVO);
                execResMenu(userResInfoChildrenVO,item.getId(),userResInfos);
            }
            userResInfoPageVO.setChildren(userResInfoChildrenVOs);
        });
        userResInfoMenuVO.setPages(userResInfoPageVOs);
        return userResInfoMenuVO;
    }


    /**
     * 菜单树递归调用
     * @param pUserResInfoChildrenVO
     * @param parentId
     * @param userResInfos
     */
    private void execResMenu(UserResInfoChildrenVO pUserResInfoChildrenVO,String parentId,List<UserResInfo> userResInfos){
        List<UserResInfoChildrenVO> userResInfoChildrenVOs = new ArrayList<>();
        userResInfos.forEach(item->{
            if(parentId.equals(item.getParentId())){
                UserResInfoChildrenVO userResInfoChildrenVO = new UserResInfoChildrenVO();
                userResInfoChildrenVO.setLabel(item.getName());
                userResInfoChildrenVO.setSchemaApi(item.getApiUrl());
                if(!StrUtil.isBlank(item.getDescript())){
                    JSONObject jsonObject = JSON.parseObject(item.getDescript());
                    userResInfoChildrenVO.setIcon(jsonObject.getString("icon"));
                    userResInfoChildrenVO.setUrl(jsonObject.getString("url"));
                    userResInfoChildrenVO.setLink(jsonObject.getString("link"));
                    userResInfoChildrenVO.setIsDefaultPage(jsonObject.getString("default"));
                    userResInfoChildrenVO.setSchema(jsonObject.get("schema"));
                }
                userResInfoChildrenVOs.add(userResInfoChildrenVO);
                execResMenu(userResInfoChildrenVO,item.getId(),userResInfos);
            }
        });
        if(userResInfoChildrenVOs.size()<=0){
            return;
        }
        pUserResInfoChildrenVO.setChildren(userResInfoChildrenVOs);
    }

}
