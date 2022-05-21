package me.candybox.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Select;

import me.candybox.user.model.UserRoleResRelation;
import me.candybox.user.vo.UserRoleResRelationVO;

/**
 * UserRoleRelation Mapper
 */
public interface UserRoleResRelationMapper extends BaseMapper<UserRoleResRelation>{
    
    @Select("<script>"
    +" select a.id role_id,a.name role_name ,a.tag role_tag ,b.res_id res_id,b.id user_role_res_rel_id ,b.create_time user_role_res_rel_create_time "
    +" from user_role_info a  left join user_role_res_relation b on a.id=b.role_id and b.status=1 and b.res_id=#{resId}"
    +" where a.status=1 "
    +" <if test='roleName != null and roleName != \"%\"'> and a.name like #{roleName} </if> "
    +" <if test='roleTag != null and roleTag != \"%\"'>and a.tag like #{roleTag} </if>  "
    +" order by a.create_time asc "
    +" </script>")
    IPage<UserRoleResRelationVO> selectByRes(IPage<?> page, String resId,String roleName,String roleTag);


}
