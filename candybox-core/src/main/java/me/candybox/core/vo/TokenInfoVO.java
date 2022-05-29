package me.candybox.core.vo;

import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * token信息
 */
@Data
public class TokenInfoVO {
    @ApiModelProperty(value = "token id")
    private String tokenId;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "姓名")
    private String userName;
    @ApiModelProperty(value = "单位id")
    private String deptId;
    @ApiModelProperty(value = "单位名称")
    private String deptName;
    @ApiModelProperty(value = "token类型")
    private String tokenType;
    @ApiModelProperty(value = "oauth2认证的scopes")
    private Set<String> scopes;

}
