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
import me.candybox.core.config.TokenInfoThreadLocal;
import me.candybox.core.vo.ResultVO;
import me.candybox.user.service.ResService;
import me.candybox.user.vo.UserRoleResRelationVO;

@Slf4j
@RestController
@RequestMapping("/api/user/res/")
public class ResController {


    @Autowired
    private ResService resService;

    @Operation(summary ="资源树全部查询")
    @GetMapping("/tree/all")
    public ResultVO selectTreeAll(){
        return new ResultVO(resService.selectTreeAll());
    }

    @Operation(summary ="资源菜单树全部查询")
    @GetMapping("/menu/user/all")
    public ResultVO selectMenuByUserAll(){
        return new ResultVO(resService.selectMenuByUserAll(TokenInfoThreadLocal.getTokenInfo().getUserId()));
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
