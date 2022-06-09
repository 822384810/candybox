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


    @Select("select id,name from user_info a where status=1 "
    +" and exists (select * from user_role_relation b where a.id=b.user_id and b.status=1 and b.role_id=#{roleId})")
    List<UserInfo> selectUserListByRole(String roleId);

}
