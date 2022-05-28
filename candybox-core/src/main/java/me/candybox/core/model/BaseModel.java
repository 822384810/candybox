package me.candybox.core.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Getter;
import lombok.Setter;



/**
 * model父类
 */
@Getter
@Setter
public  class BaseModel<T> extends Model<BaseModel<?>> {
    
}
