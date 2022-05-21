package me.candybox.core.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * candybox model 父类
 * 封装基础属性
 */

@Data
public abstract class CbBaseModel<T> extends BaseModel<CbBaseModel> {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "创建用户名称")
    private String createUserName;
    @ApiModelProperty(value = "创建用户Id")
    private String createUserId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "更新用户名称")
    private String updateUserName;
    @ApiModelProperty(value = "更新用户Id")
    private String updateUserId;
    @ApiModelProperty(value = "数据是否有效,逻辑删除使用 1：有效 0：无效")
    private int status;
    
}
