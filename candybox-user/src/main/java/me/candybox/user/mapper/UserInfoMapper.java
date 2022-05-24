package me.candybox.user.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Select;

import me.candybox.user.model.UserInfo;

/**
 * UserInfo Mapper
 */
public interface UserInfoMapper extends BaseMapper<UserInfo>{

    @Select("select * from user_info where ( nick_name=#{nickName} or mobile_phone=#{mobilePhone} ) and status=1 ")
    List<UserInfo> selectListUserName(String nickName,String mobilePhone);

}
