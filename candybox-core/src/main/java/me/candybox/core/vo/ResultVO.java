package me.candybox.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.candybox.core.config.ConstantConfig;

/**
 * 通用返回对象
 */
@Data
public class ResultVO {

    /**
     * 构造函数，默认成功
     */
    public ResultVO(){
        this.status=ConstantConfig.RESULT_STATUS_SUCC;
        this.msg="操作成功";
    }

    /**
     * 构造函数，默认成功
     * @param msg
     */
    public ResultVO(String msg){
        this.status=ConstantConfig.RESULT_STATUS_SUCC;
        this.msg=msg;
     }

    /**
     * 构造函数，默认成功
     * @param data
     */
    public ResultVO(Object data){
       this.status=ConstantConfig.RESULT_STATUS_SUCC;
       this.msg="操作成功";
       this.data=data;
    }

    /**
     * 构造函数，无返回数据
     * @param status
     * @param msg
     */
    public ResultVO(int status,String msg){
        this.status=status;
        this.msg=msg;
     }
    
    /**
     * 构造函数
     * @param status
     * @param msg
     * @param data
     */
    public ResultVO(int status,String msg,Object data){
        this.status=status;
        this.msg=msg;
        this.data=data;
     }
 
    @ApiModelProperty(value = "执行状态，参见ConstantConfig")
    private int status;
    @ApiModelProperty(value = "执行消息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private Object data; 
}
