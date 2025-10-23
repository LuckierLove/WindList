package cn.luckierlove.windlist.controller

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.entity.dto.UserDTO
import cn.luckierlove.windlist.service.UserService
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 认证相关接口
 */
@RestController
@RequestMapping("/auth")
class AuthController {
    private val log = LoggerFactory.getLogger(AuthController::class.java)
    @Resource
    private lateinit var userService: UserService

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    fun register(@RequestBody userDTO: UserDTO): Result.Response<String> {
        log.info("Registering user: $userDTO")
        userService.register(userDTO)
        return Result.success()
    }
}