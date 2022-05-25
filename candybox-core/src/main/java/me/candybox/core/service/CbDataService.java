package me.candybox.core.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.stereotype.Service;

import me.candybox.core.model.BaseModel;

/**
 * candybox 通用数据处理服务
 */

 @Service("cbDataService")
public class CbDataService {

    /**
     * 数据增加
     * @param baseModel
     * @return
     */
    public boolean insert (BaseModel baseModel){
        return baseModel.insert();
    }

    /**
     * 数据更新通过id
     * @param baseModel
     * @return
     */
    public boolean updateById (BaseModel baseModel){
        return baseModel.updateById();
    }

    /**
     * 数据更新通过条件
     * @param baseModel
     * @return
     */
    public boolean update (BaseModel baseModel,Wrapper<BaseModel> queryWrapper){
        return baseModel.update(queryWrapper);
    }

    /**
     * 数据增加或更新
     * @param baseModel
     * @return
     */
    public boolean insertOrUpdate (BaseModel baseModel){
        return baseModel.insertOrUpdate();
    }

    /**
     * 数据删除通过id
     * @param baseModel
     * @return
     */
    public boolean deleteById (BaseModel baseModel){
        return baseModel.deleteById();
    }

    /**
     * 所有数据查询
     * @param baseModel
     * @return
     */
    public List<BaseModel> selectList (BaseModel baseModel,QueryWrapper queryWrapper){
        return baseModel.selectList(queryWrapper);
    }

    


    /**
     * 分页数据查询
     * @param baseModel
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<BaseModel> selectPage (BaseModel baseModel,IPage<BaseModel> page,Wrapper<BaseModel> queryWrapper){
        return baseModel.selectPage(page,queryWrapper);
    }
}