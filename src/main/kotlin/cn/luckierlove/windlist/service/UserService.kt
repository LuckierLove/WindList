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
}