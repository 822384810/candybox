package me.candybox.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.core.model.BaseModel;
import me.candybox.core.service.CbDataService;
import me.candybox.user.mapper.UserRoleResRelationMapper;
import me.candybox.user.model.UserResInfo;
import me.candybox.user.model.UserRoleResRelation;
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
    private CbDataService cbDataService;


   

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
     *  按用户查询所属菜单资源
     * @param userId
     * @return
     */
    public List<UserResInfoTreeVO> selectTreeByUserAll(String userId){
        QueryWrapper<UserResInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("type", 1);
        queryWrapper.orderByAsc("create_time");
        List<BaseModel> list = cbDataService.selectList(new UserResInfo(),queryWrapper);
        if(list==null){
            return null;
        }
        List<UserResInfoTreeVO> userResInfoTreeVOs = new ArrayList<>();
        list.forEach(item->{
            UserResInfo userResInfo = (UserResInfo)item;
            if("0".equals(userResInfo.getParentId())){
                UserResInfoTreeVO userResInfoTreeVO = new UserResInfoTreeVO();
                BeanUtils.copyProperties(item, userResInfoTreeVO);
                execResTree(userResInfoTreeVO,userResInfo.getId(),list);
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
    private void execResTree(UserResInfoTreeVO userResInfoTreeVO,String parentId,List<BaseModel> list){
        List<UserResInfoTreeVO> tUserResInfoTreeVOs = new ArrayList<>();
        list.forEach(item->{
            UserResInfo userResInfo = (UserResInfo)item;
            if(parentId.equals(userResInfo.getParentId())){
                UserResInfoTreeVO tUserResInfoTreeVO = new UserResInfoTreeVO();
                BeanUtils.copyProperties(item, tUserResInfoTreeVO);
                // tUserResInfoTreeVO.setValue(JSON.parseObject("{'id':'"+tUserResInfoTreeVO.getId()+"','name':'"+tUserResInfoTreeVO.getName()+"'}"));
                execResTree(userResInfoTreeVO,userResInfo.getId(),list);
                tUserResInfoTreeVOs.add(tUserResInfoTreeVO);
            }
            
        });
        if(tUserResInfoTreeVOs.size()<=0){
            return;
        }
        userResInfoTreeVO.setChildren(tUserResInfoTreeVOs);
    }




}
