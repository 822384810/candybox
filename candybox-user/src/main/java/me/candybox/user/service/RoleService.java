package me.candybox.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.core.model.BaseModel;
import me.candybox.core.service.CbDataService;
import me.candybox.core.vo.FormSelectVO;
import me.candybox.user.mapper.UserRoleRelationMapper;
import me.candybox.user.mapper.UserRoleResRelationMapper;
import me.candybox.user.model.UserRoleInfo;
import me.candybox.user.model.UserRoleRelation;
import me.candybox.user.vo.UserRoleRelationVO;
import me.candybox.user.vo.UserRoleResRelationVO;

/** 
 * 角色业务类
*/

@Service
public class RoleService {
    
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private UserRoleResRelationMapper userRoleResRelationMapper;
    @Autowired
    private CbDataService cbDataService;



    /**
     * 按用户查询角色列表
     * @param page
     * @param userId
     * @return
     */
    public IPage<UserRoleRelationVO> selectRelationByUser(IPage<UserRoleRelationVO> page,String userId,String roleName,String roleTag){
        IPage<UserRoleRelationVO> iPage = userRoleRelationMapper.selectByUser(page,userId,roleName+"%",roleTag+"%");
        iPage.getRecords().forEach(item->{
            item.setUserRoleRelCheck(true);
            if(StrUtil.isEmpty(item.getUserRoleRelId())){
                item.setUserRoleRelCheck(false);
            }
        });
        return iPage;
    }

     /**
     * 按资源查询角色列表
     * @param page
     * @param userId
     * @return
     */
    public IPage<UserRoleResRelationVO> selectRelationByRes(IPage<UserRoleResRelationVO> page,String resId,String roleName,String roleTag){
        IPage<UserRoleResRelationVO> iPage = userRoleResRelationMapper.selectByRes(page,resId,roleName+"%",roleTag+"%");
        iPage.getRecords().forEach(item->{
            item.setUserRoleResRelCheck(true);
            if(StrUtil.isEmpty(item.getUserRoleResRelId())){
                item.setUserRoleResRelCheck(false);
            }
        });
        return iPage;
    }

    /**
     * 用户角色关系保存
     * @param userRoleRelationVO
     * @return
     */
    public int save(UserRoleRelationVO userRoleRelationVO){
        int ret = 1;
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setUserId(userRoleRelationVO.getUserId());
        userRoleRelation.setRoleId(userRoleRelationVO.getRoleId());

        QueryWrapper<UserRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userRoleRelation.getUserId());
        queryWrapper.eq("role_id", userRoleRelation.getRoleId());
        queryWrapper.eq("status", 1);
        if(userRoleRelationVO.isUserRoleRelCheck()){
            userRoleRelation.setUpdateTime(new Date());
            userRoleRelation.setUpdateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
            userRoleRelation.setUpdateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
            userRoleRelation.setStatus(0);
            ret = userRoleRelationMapper.update(userRoleRelation, queryWrapper);
        }else{
            long count = userRoleRelationMapper.selectCount(queryWrapper);
            if(count<=0){
                userRoleRelation.setCreateTime(new Date());
                userRoleRelation.setCreateUserId(TokenInfoThreadLocal.getTokenInfo().getUserId());
                userRoleRelation.setCreateUserName(TokenInfoThreadLocal.getTokenInfo().getUserName());
                userRoleRelation.setStatus(1);
                ret = userRoleRelationMapper.insert(userRoleRelation);
            }
        }
        return ret;
    }


      /**
     * 角色select form 列表查询
     * @param queryWrapper
     * @return
     */
    public List<FormSelectVO> selectListForFormSelect (Wrapper<BaseModel<?>> queryWrapper){
        List<BaseModel<?>> dList = cbDataService.selectList(new UserRoleInfo(),queryWrapper);
        List<FormSelectVO> formSelectVOs = new ArrayList<FormSelectVO>() {
        };
        if(dList!=null){
            dList.forEach(item->{
                UserRoleInfo userRoleInfo = (UserRoleInfo)item;
                FormSelectVO formSelectVO = new FormSelectVO();
                formSelectVO.setLabel(userRoleInfo.getName());
                formSelectVO.setValue(userRoleInfo.getId());
                formSelectVOs.add(formSelectVO);
            });
        }
        return formSelectVOs;
    }
    
}
