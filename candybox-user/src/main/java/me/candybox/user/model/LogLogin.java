package me.candybox.user.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.candybox.core.annotation.CbDataName;
import me.candybox.core.model.BaseModel;

/**
 * 登录日志
 */
@Getter
@Setter
@CbDataName(name="log_login")
public class LogLogin extends BaseModel<LogLogin>{
    @TableId(type=IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private String id;

    private String tokenId;
    private String userId;
    private String userName;
    private String deptId;
    private String deptName;
    private String loginDev;
    private String loginIp;
    private String loginSource;
    private Date loginTime;
}
