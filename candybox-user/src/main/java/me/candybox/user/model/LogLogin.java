package me.candybox.user.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录日志
 */
@Data
public class LogLogin {
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
