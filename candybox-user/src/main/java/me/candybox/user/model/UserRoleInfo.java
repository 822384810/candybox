package me.candybox.user.model;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.candybox.core.annotation.CbDataName;
import me.candybox.core.model.CbBaseModel;

/**
 * 角色信息
 */
@Getter
@Setter
@CbDataName(name="user_role")
public class UserRoleInfo extends CbBaseModel<UserRoleInfo>{
    @ApiModelProperty(value = "角色id")
    @TableId(type=IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "{UserRoleInfo.name}")
    @Length(min = 1,max = 50,message = "{UserRoleInfo.name}")
    private String name;

    @ApiModelProperty(value = "标签")
    @Length(max = 50,message = "{UserRoleInfo.tag}")
    private String tag;
}
