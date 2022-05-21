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
 * 资源信息
 */
@Data
@CbDataName(name="user_res")
public class UserResInfo extends CbBaseModel<UserResInfo>{


    @ApiModelProperty(value = "资源id")
    @TableId(type=IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "资源上级id")
    @NotBlank(message = "{UserResInfo.parentId}")
    private String parentId;

    @ApiModelProperty(value = "资源名称")
    @NotBlank(message = "{UserResInfo.name}")
    @Length(min = 1,max = 100,message = "{UserResInfo.name}")
    private String name;

    @ApiModelProperty(value = "资源描述")
    private String descript;

    @ApiModelProperty(value = "资源标签")
    private String tag;

    @ApiModelProperty(value = "资源类型：1菜单资源2操作资源3数据资源9其他资源")
    private String type;
    
}
