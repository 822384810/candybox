package me.candybox.user.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import me.candybox.user.model.UserRoleRelation;
import me.candybox.user.vo.UserRoleRelationVO;

/**
 * UserRoleRelation Mapper
 */
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation>{
    
    @Select("<script>"
    +" select a.id role_id,a.name role_name ,a.tag role_tag ,b.user_id user_id,b.id user_role_rel_id ,b.create_time user_role_rel_create_time "
    +" from user_role_info a  left join user_role_relation b on a.id=b.role_id and b.status=1 and b.user_id=#{userId}"
    +" where a.status=1 "
    +" <if test='roleName != null and roleName != \"%\"'> and a.name like #{roleName} </if> "
    +" <if test='roleTag != null and roleTag != \"%\"'>and a.tag like #{roleTag} </if>  "
    +" order by a.create_time asc "
    +" </script>")
    IPage<UserRoleRelationVO> selectByUser(IPage<?> page, String userId,String roleName,String roleTag);


}
