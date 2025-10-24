package cn.luckierlove.windlist

import cn.luckierlove.windlist.properties.JwtProperties
import org.mybatis.spring.annotation.MapperScans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@MapperScans
@EnableConfigurationProperties(JwtProperties::class)
@EnableAspectJAutoProxy
class WindListApplication

fun main(args: Array<String>) {
    runApplication<WindListApplication>(*args)
}
