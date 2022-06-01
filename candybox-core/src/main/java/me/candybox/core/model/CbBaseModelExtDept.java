package me.candybox.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * candybox model 父类
 * 封装基础属性
 */

@Getter
@Setter
public abstract class CbBaseModelExtDept<T> extends CbBaseModel<CbBaseModelExtDept<?>> {

    @ApiModelProperty(value = "创建单位名称")
    private String createDeptName;
    @ApiModelProperty(value = "创建单位Id")
    private String createDeptId;
    @ApiModelProperty(value = "更新单位名称")
    private String updateDeptName;
    @ApiModelProperty(value = "更新单位Id")
    private String updateDeptId;

    
}
