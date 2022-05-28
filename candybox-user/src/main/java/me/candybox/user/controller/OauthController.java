package me.candybox.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import me.candybox.core.config.ConstantConfig;
import me.candybox.core.utils.ValidatedUtil;
import me.candybox.core.vo.ResultVO;
import me.candybox.user.model.Oauth2RegisteredClient;
import me.candybox.user.service.OauthService;

@Slf4j
@RestController
@RequestMapping("/api/user/")
public class OauthController {


    @Autowired
    private OauthService oauthService;

    @Operation(summary ="认证客户端保存")
    @RequestMapping(value = "/oauth",method = {RequestMethod.POST})
    public ResultVO saveUser(@Parameter(description="Oauth Client",required = true) @RequestBody(required = true) Oauth2RegisteredClient oauth2RegisteredClient){
        ResultVO resultVO = new ResultVO();
        //数据校验
        ValidatedUtil.validate(oauth2RegisteredClient,resultVO);
        if(resultVO.getStatus()!=ConstantConfig.RESULT_STATUS_SUCC){
            return resultVO;
        }
        int ret = oauthService.save(oauth2RegisteredClient);
        if(ret==1){
            return resultVO;
        }
        resultVO.setStatus(ConstantConfig.RESULT_STATUS_FAIL);
        resultVO.setMsg("操作失败");
        return resultVO;
    }




    
}
