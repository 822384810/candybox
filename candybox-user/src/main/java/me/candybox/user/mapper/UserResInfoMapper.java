package me.candybox.user.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Select;

import me.candybox.user.model.UserResInfo;

/**
 * UserDeptInfo Mapper
 */
public interface UserResInfoMapper extends BaseMapper<UserResInfo>{
 
    
    @Select("select * from user_res_info a where a.status=1 and a.type=1 and exists ( "
        +" select id from user_role_res_relation b where a.id=b.res_id and b.status=1 "
        +" and exists ( "
        +" select id from user_role_relation  c where b.role_id=c.role_id and c.status=1 and c.user_id=#{userId}"
        +" ) )  order by sort")
    List<UserResInfo> selectUserResByUser(String userId);
}
