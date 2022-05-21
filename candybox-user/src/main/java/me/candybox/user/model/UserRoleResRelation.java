package me.candybox.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.candybox.core.annotation.CbDataName;
import me.candybox.core.model.CbBaseModel;

/**
 * 用户角色对应关系
 */

@Data
@CbDataName(name="user_role_res_rel")
public class UserRoleResRelation extends CbBaseModel<UserRoleResRelation>{
    @ApiModelProperty(value = "关系id")
    @TableId(type=IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty(value = "资源id")
    private String resId;
    @ApiModelProperty(value = "角色id")
    private String roleId;

}
