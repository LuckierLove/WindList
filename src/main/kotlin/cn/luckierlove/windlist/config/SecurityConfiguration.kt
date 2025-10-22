package cn.luckierlove.windlist.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.DefaultSecurityFilterChain

@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain? =
        http.authorizeHttpRequests {
                    it.anyRequest().authenticated()
                }
            .build()
}