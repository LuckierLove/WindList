package cn.luckierlove.windlist.config

import cn.luckierlove.windlist.interceptor.JwtTokenInterceptor
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration: WebMvcConfigurer {
    private val logger = LoggerFactory.getLogger(WebConfiguration::class.java)

    @Resource
    private lateinit var jwtTokenInterceptor: JwtTokenInterceptor

    /**
     * 拦截器配置
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        logger.info("Adding interceptors")
        registry.addInterceptor(jwtTokenInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/auth/**", // 排除认证相关的路径
            )
    }

    /**
     * 静态资源配置
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        logger.info("Adding resource handlers")
        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
    }
}