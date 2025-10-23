package cn.luckierlove.windlist.mapper

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserRoleMapper {
    @Insert("INSERT INTO user_roles (user_id, role_id) VALUES (#{userId}, #{roleId})")
    fun saveUserRole(userId: Long, roleId: Int)
}