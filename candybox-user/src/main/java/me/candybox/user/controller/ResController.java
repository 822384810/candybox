package me.candybox.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.vo.ResultVO;
import me.candybox.user.service.ResService;
import me.candybox.user.vo.UserRoleResRelationVO;

@Slf4j
@RestController
@RequestMapping("/api/user/res/")
public class ResController {

    // @Autowired
    // private CbDataService cbDataService;
    @Autowired
    private ResService resService;


    // @Operation(summary ="资源菜单树查询")
    // @GetMapping("/tree")
    // public ResultVO userResInfoTree(@Parameter(description="父id",required = true) @RequestParam(defaultValue = "0") String parentId){
    //     QueryWrapper queryWrapper = new QueryWrapper<>();
    //     queryWrapper.eq("status", 1);
    //     queryWrapper.eq("parent_id", parentId);
    //     queryWrapper.eq("type", 1);
    //     queryWrapper.orderByAsc("create_time");
    //     List<BaseModel> list = cbDataService.selectList(new UserResInfo(),queryWrapper);
    //     if(list==null){
    //         return new ResultVO();
    //     }
    //     List<UserResInfoTreeVO> userResInfoTreeVOs = new ArrayList<>();
    //     list.forEach(item->{
    //         UserResInfo userResInfo = (UserResInfo)item;
    //         UserResInfoTreeVO userResInfoTreeVO = new UserResInfoTreeVO();
    //         BeanUtils.copyProperties(userResInfo, userResInfoTreeVO);
    //         // userResInfoTreeVO.setValue(JSON.parseObject("{'id':'"+userResInfoTreeVO.getId()+"','name':'"+userResInfoTreeVO.getName()+"'}"));
    //         userResInfoTreeVOs.add(userResInfoTreeVO);
    //     });
    //     return new ResultVO(userResInfoTreeVOs);
    // }


    @Operation(summary ="资源菜单树全部查询")
    @GetMapping("/tree/user/all")
    public ResultVO selectTreeByUserAll(){
        return new ResultVO(resService.selectTreeByUserAll(null));
    }


    @Operation(summary ="资源角色关系保存")
    @RequestMapping(value = "/relation" ,method = {RequestMethod.POST})
    public ResultVO save(@Parameter(description="json格式数据",required = true) @RequestBody(required = true) UserRoleResRelationVO userRoleResRelationVO){
        ResultVO resultVO = new ResultVO();
        if(resService.save(userRoleResRelationVO)<1){
            resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
            resultVO.setMsg("操作失败");
        }
        return resultVO;
    }



    
}
