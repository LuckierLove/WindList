package cn.luckierlove.windlist.mapper

import cn.luckierlove.windlist.entity.Users
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper: BaseMapper<Users> {
    @Select("SELECT * FROM users WHERE username = #{username}")
    fun findByUsername(username: String): Users?

    @Insert("INSERT INTO user_roles (user_id, role_id) VALUES (#{userId}, #{roleId})")
    fun saveUserRole(userId: Long, roleId: Int)
}