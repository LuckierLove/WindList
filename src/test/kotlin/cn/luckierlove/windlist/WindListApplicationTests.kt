package cn.luckierlove.windlist

import io.jsonwebtoken.Jwts.claims
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WindListApplicationTests {

    @Resource
    private lateinit var jwtUtil: cn.luckierlove.windlist.common.utils.JwtUtil

    @Test
    fun testJwtUtil() {
        val claims = mutableMapOf<String, Any>(
            "userId" to 12345,
            "username" to "testuser"
        )
        val token = jwtUtil.createJWT(claims)
        println("Generated JWT Token: $token")
        val parsedClaims = jwtUtil.parseJWT(token)
        println("Parsed Claims: $parsedClaims")
    }

}
