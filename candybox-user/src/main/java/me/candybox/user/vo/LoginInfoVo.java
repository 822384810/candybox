package me.candybox.user.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginInfoVo {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "{LoginInfoVo.userName}")
    private String userName;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "{LoginInfoVo.userPwd}")
    private String userPwd;
    @ApiModelProperty(value = "验证码")
    // @NotBlank(message = "{LoginInfoVo.code}")
    private String code;

    private String loginDev;
    private String loginIp;
    private String loginSource;
}
