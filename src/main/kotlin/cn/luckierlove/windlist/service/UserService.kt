package cn.luckierlove.windlist.service

import cn.luckierlove.windlist.entity.dto.UserDTO

/**
 * 用户服务
 */
interface UserService {
    /**
     * 用户注册
     */
    fun register(userDTO: UserDTO)

    /**
     * 用户登录
     */
    fun login(userDTO: UserDTO): String
}