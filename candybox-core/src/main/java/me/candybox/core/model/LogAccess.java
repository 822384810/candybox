package me.candybox.core.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogAccess extends Model<BaseModel> {

    @TableId(type=IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "类名")
    private String className;
    @ApiModelProperty(value = "方法名")
    private String methodName;
    @ApiModelProperty(value = "操作内容")
    private String optContent;
    @ApiModelProperty(value = "操作ip")
    private String optIp;
    @ApiModelProperty(value = "token ID")
    private String tokenId;
    @ApiModelProperty(value = "使用时间")
    private Long useTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "创建用户名称")
    private String createUserName;
    @ApiModelProperty(value = "创建用户Id")
    private String createUserId;
    @ApiModelProperty(value = "创建单位名称")
    private String createDeptName;
    @ApiModelProperty(value = "创建单位Id")
    private String createDeptId;
    
}
