package me.candybox.user.model;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.candybox.core.annotation.CbDataName;
import me.candybox.core.model.CbBaseModel;

/**
 * 用户单位
 */
@Data
@CbDataName(name="user_dept")
public class UserDeptInfo extends CbBaseModel<UserDeptInfo>{
    @TableId(type=IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "单位id")
    private String id;

    @ApiModelProperty(value = "单位上级id")
    private String parentId;

    @ApiModelProperty(value = "单位名称")
    @NotBlank(message = "{UserDeptInfo.name}")
    @Length(min = 1,max = 50,message = "{UserDeptInfo.name}")
    private String name;

    @ApiModelProperty(value = "单位简称")
    @Length(max = 100,message = "{UserDeptInfo.shortName}")
    private String shortName;
}
