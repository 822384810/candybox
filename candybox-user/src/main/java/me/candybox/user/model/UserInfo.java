package me.candybox.user.model;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.candybox.core.annotation.CbDataName;
import me.candybox.core.model.CbBaseModel;

/**
 * 用户信息
 */
@Data
@CbDataName(name="user_user")
public class UserInfo extends CbBaseModel<UserInfo>{
    @ApiModelProperty(value = "用户id")
    @TableId(type=IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "{UserInfo.name}")
    @Length(min = 1,max = 50,message = "{UserInfo.name}")
    private String name;

    @ApiModelProperty(value = "登录名")
    @Length(max = 50,message = "{UserInfo.nickName}")
    private String nickName;

    @ApiModelProperty(value = "密码")
    @Length(max = 20,message = "{UserInfo.password}")
    private String password;

    @ApiModelProperty(value = "手机号号码")
    @Length(max = 20,message = "{UserInfo.mobilePhone}")
    private String mobilePhone;

    @ApiModelProperty(value = "邮件")
    @Email(message = "{UserInfo.mail}")
    private String mail;

    @ApiModelProperty(value = "证件号码")
    @Length(max = 30,message = "{UserInfo.idNumber}")
    private String idNumber;

    @ApiModelProperty(value = "证件类型")
    @DecimalMax(value = "5",message = "{UserInfo.idType}")
    private int idType;

    @ApiModelProperty(value = "单位编号")
    @Length(max = 36,message = "{UserInfo.deptId}")
    private String deptId;

    @ApiModelProperty(value = "单位名称")
    @Length(max = 100,message = "{UserInfo.deptName}")
    private String deptName;


    private Date lastLoginTime;
    private String lastLoginIp;
    private String lastLoginDev;
    private String lastLoginSource;
    private long loginCount;



}
