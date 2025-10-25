package cn.luckierlove.windlist.service.impl

import cn.luckierlove.windlist.common.exception.AuthException
import cn.luckierlove.windlist.common.utils.JwtUtil
import cn.luckierlove.windlist.constant.JwtClaimsConstant
import cn.luckierlove.windlist.constant.UserConstant
import cn.luckierlove.windlist.constant.UserMessageConstant
import cn.luckierlove.windlist.entity.Users
import cn.luckierlove.windlist.entity.dto.UserDTO
import cn.luckierlove.windlist.mapper.UserMapper
import cn.luckierlove.windlist.service.UserService
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import jakarta.annotation.Resource
import org.apache.tomcat.util.net.openssl.ciphers.MessageDigest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.DigestUtils
import java.util.UUID
import kotlin.random.Random

/**
 * 用户服务实现类
 */
@Service
class UserServiceImpl : UserService {
    @Resource
    private lateinit var userMapper: UserMapper
    @Resource
    private lateinit var jwtUtil: JwtUtil
    /**
     * 用户注册
     */
    @Transactional
    override fun register(userDTO: UserDTO) {

        if(userDTO.username == null || userDTO.username!!.isEmpty())
            throw AuthException(UserMessageConstant.USERNAME_EMPTY)

        if(userDTO.password == null || userDTO.password!!.isEmpty())
            throw AuthException(UserMessageConstant.PASSWORD_EMPTY)

        if(userDTO.email == null || userDTO.email!!.isEmpty())
            throw AuthException(UserMessageConstant.EMAIL_EMPTY)

        val user = Users().apply {
            this.username = userDTO.username
            this.password = DigestUtils.md5DigestAsHex(userDTO.password!!.toByteArray())
            this.email = userDTO.email
            this.nickname = UserConstant.DEFAULT_NICKNAME_PREFIX + UUID.randomUUID().toString()
            this.avatarUrl = UserConstant.DEFAULT_AVATAR_URL
            this.accountNonLocked = UserConstant.DEFAULT_ACCOUNT_NON_LOCKED
            this.accountNonExpired = UserConstant.DEFAULT_ACCOUNT_NON_EXPIRED
            this.credentialsNonExpired = UserConstant.DEFAULT_CREDENTIALS_NON_EXPIRED
            this.enabled = UserConstant.DEFAULT_ENABLED
        }
        userMapper.insert(user)
        userMapper.saveUserRole(user.id!!, UserConstant.DEFAULT_ROLE)
    }

    /**
     * 用户登录
     */
    override fun login(userDTO: UserDTO): String{
        if(userDTO.username == null || userDTO.username!!.isEmpty())
            throw AuthException(UserMessageConstant.USERNAME_EMPTY)

        if(userDTO.password == null || userDTO.password!!.isEmpty())
            throw AuthException(UserMessageConstant.PASSWORD_EMPTY)

        val user = userMapper.findByUsername(userDTO.username!!)


        val hashedPassword = DigestUtils.md5DigestAsHex(userDTO.password!!.toByteArray())
        if(user!!.password != hashedPassword)
            throw AuthException(UserMessageConstant.INVALID_PASSWORD)

        // 登录成功，返回用户信息或生成token等操作
        val token = jwtUtil.createJWT(mutableMapOf(
            JwtClaimsConstant.USER_ID to user.id!!,
        ))
        return token
    }
}