package me.candybox.core.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * model父类
 */
@Data
public abstract class BaseModel<T> extends Model<BaseModel> {
    
}