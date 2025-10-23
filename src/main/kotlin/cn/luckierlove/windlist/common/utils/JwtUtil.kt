package cn.luckierlove.windlist.common.utils

import cn.luckierlove.windlist.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.Resource
import jdk.internal.org.jline.keymap.KeyMap.key
import org.springframework.stereotype.Component


/**
 * JWT 工具类
 */
@Component
class JwtUtil {

    @Resource
    private lateinit var jwtProperties: JwtProperties

    /**
     * 创建 JWT Token
     * @param claims 包含信息
     * @return 生成的 JWT Token
     */
    fun createJWT(claims: MutableMap<String, Any>): String {
        val exMillis = System.currentTimeMillis() + jwtProperties.expirationTime
        val date = java.util.Date(exMillis)
        val key = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(Charsets.UTF_8))
        return Jwts.builder()
            .claims(claims)
            .expiration(date)
            .signWith(key)
            .compact()
    }

    /**
     * 解析 JWT Token
     * @param jwt JWT Token 字符串
     * @return 包含的信息
     */
    fun parseJWT(jwt: String): MutableMap<String, Any> {
        val key = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(Charsets.UTF_8))
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(jwt)
            .payload
        return claims
    }
}