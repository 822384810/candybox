package me.candybox.user.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRoleResRelationVO {

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "{UserRoleResRelationVO.roleId}")
    private String roleId;
    private String roleName;
    
    private String roleTag;

    @ApiModelProperty(value = "资源id")
    @NotBlank(message = "{UserRoleResRelationVO.resId}")
    private String resId;

    private String userRoleResRelId;
    private String userRoleResRelCreateTime;
    private boolean userRoleResRelCheck;
}
