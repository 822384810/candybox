package me.candybox.user.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRoleRelationVO {

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "{UserRoleRelationVO.roleId}")
    private String roleId;
    private String roleName;
    
    private String roleTag;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "{UserRoleRelationVO.userId}")
    private String userId;

    private String userRoleRelId;
    private String userRoleRelCreateTime;
    private boolean userRoleRelCheck;
}
