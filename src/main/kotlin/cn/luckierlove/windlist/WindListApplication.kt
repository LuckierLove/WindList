package cn.luckierlove.windlist

import cn.luckierlove.windlist.properties.JwtProperties
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.annotation.MapperScans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScans
@EnableConfigurationProperties(JwtProperties::class)
class WindListApplication

fun main(args: Array<String>) {
    runApplication<WindListApplication>(*args)
}
