package cn.luckierlove.windlist

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan
class WindListApplication

fun main(args: Array<String>) {
    runApplication<WindListApplication>(*args)
}
