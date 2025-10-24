package cn.luckierlove.windlist.interceptor

import cn.luckierlove.windlist.common.utils.JwtUtil
import cn.luckierlove.windlist.constant.JwtClaimsConstant
import cn.luckierlove.windlist.context.BaseContext
import cn.luckierlove.windlist.properties.JwtProperties
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

/**
 * JWT 令牌拦截器
 */
@Component
class JwtTokenInterceptor : HandlerInterceptor{
    private val logger = LoggerFactory.getLogger(JwtTokenInterceptor::class.java)

    @Resource
    private lateinit var jwtUtil: JwtUtil
    @Resource
    private lateinit var jwtProperties: JwtProperties

    /**
     * 在请求处理之前进行 JWT 令牌验证
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val token = request.getHeader(jwtProperties.tokenName)
        if (token != null) {
            logger.info("Jwt Verifying token: $token")
            return try {
                val claims = jwtUtil.parseJWT(token)
                val userId = claims[JwtClaimsConstant.USER_ID]

                logger.info("User ID: $userId")
                BaseContext.setCurrentUserId(userId.toString().toLong())
                true
            } catch (e: Exception) {
                logger.warn("JWT parsing failed: {}", e.message)
                logger.debug("JWT parse exception details", e)
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                false
            }
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        }
    }
}