package me.candybox.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.vo.ResultVO;
import me.candybox.user.service.RoleService;
import me.candybox.user.vo.UserRoleRelationVO;
import me.candybox.user.vo.UserRoleResRelationVO;


@RestController
@RequestMapping("/api/user/role/")
public class RoleController {

    @Autowired
    private RoleService rolesService;


    @Operation(summary ="用户角色关系")
    @RequestMapping(value = "/relation/user/page" ,method = {RequestMethod.GET,RequestMethod.POST})
    public ResultVO selectRelationByUserPage(@Parameter(description="用户id",required = true) @RequestParam(required = true) String userId
    ,@Parameter(description="角色名称",required = true) @RequestParam(required = true,defaultValue = "") String roleName
    ,@Parameter(description="角色😊",required = true) @RequestParam(required = true,defaultValue = "") String roleTag
    ,@Parameter(description="当前页",required = true) @RequestParam(defaultValue = "1",required = true) int page
    ,@Parameter(description="页大小",required = true) @RequestParam(defaultValue = "10",required = true) int perPage){
        IPage<UserRoleRelationVO> iPage = rolesService.selectRelationByUser(new Page<UserRoleRelationVO>(page,perPage), userId,roleName,roleTag);
        ResultVO resultVO = new ResultVO(iPage);
        return resultVO;
    }


    @Operation(summary ="资源角色关系")
    @RequestMapping(value = "/relation/res/page" ,method = {RequestMethod.GET,RequestMethod.POST})
    public ResultVO selectRelationByResPage(@Parameter(description="资源id",required = true) @RequestParam(required = true) String resId
    ,@Parameter(description="角色名称",required = true) @RequestParam(required = true,defaultValue = "") String roleName
    ,@Parameter(description="角色😊",required = true) @RequestParam(required = true,defaultValue = "") String roleTag
    ,@Parameter(description="当前页",required = true) @RequestParam(defaultValue = "1",required = true) int page
    ,@Parameter(description="页大小",required = true) @RequestParam(defaultValue = "10",required = true) int perPage){
        IPage<UserRoleResRelationVO> iPage = rolesService.selectRelationByRes(new Page<UserRoleResRelationVO>(page,perPage), resId,roleName,roleTag);
        ResultVO resultVO = new ResultVO(iPage);
        return resultVO;
    }


    @Operation(summary ="用户角色关系保存")
    @RequestMapping(value = "/relation" ,method = {RequestMethod.POST})
    public ResultVO save(@Parameter(description="json格式数据",required = true) @RequestBody(required = true) UserRoleRelationVO userRoleRelationVO){
        ResultVO resultVO = new ResultVO();
        if(rolesService.save(userRoleRelationVO)<1){
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
            resultVO.setMsg("操作失败");
        }
        return resultVO;
    }
    
}
