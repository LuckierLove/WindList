package cn.luckierlove.windlist.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * JWT 配置属性
 */
@ConfigurationProperties(prefix = "windlist.jwt")
data class JwtProperties(
    val secretKey: String, // 密钥
    val expirationTime: Long, // 过期时间，单位：毫秒
    val tokenName: String, // token 名称
)
