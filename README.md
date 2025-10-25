# WindList

一个用 Kotlin + Spring Boot 开发的待办事项（Todo）示例项目，集成了 MyBatis-Plus、PostgreSQL、JWT、Swagger 等常用组件，包含用户认证、任务管理与示例 API。

---

## 主要功能

- 用户注册 / 登录（基于 JWT）
- 使用 BCrypt 存储密码
- 基于 MyBatis-Plus 的 CRUD 操作
- Swagger API 文档
- 常见异常的统一处理与返回


## 技术栈

- Kotlin
- Spring Boot
- Spring Security (JWT)
- MyBatis-Plus
- PostgreSQL
- Swagger / OpenAPI
- Gradle (Kotlin DSL)


## 仓库结构（部分）

- `src/main/kotlin` - 源代码
- `src/main/resources` - 配置和 mapper
- `sql/scheme.sql` - 数据库建表脚本
- `build.gradle.kts` / `settings.gradle.kts` - 构建脚本


## 本地快速开始

先确保本机安装并运行 PostgreSQL，然后：

1. 配置数据库信息（示例 `application.yml` 中已有 `dev` 环境配置）

2. 在项目根目录（Windows cmd）运行：

```
\.\gradlew.bat clean build -x test
\.\gradlew.bat bootRun
```

默认应用将运行在 `http://localhost:8080`。


## 数据库

项目包含 `sql/scheme.sql`（仓库中），可用于初始化数据库表。确保 `application.yml` 中 `spring.datasource` 已指向正确的数据库并能连接。



## JWT 相关常见问题

错误示例：
```
io.jsonwebtoken.security.UnsupportedKeyException: Unable to determine a suitable MAC or Signature algorithm for the specified key ...
```
原因与解决：
- 使用 `io.jsonwebtoken` (jjwt) 生成签名时，密钥（Key 或 字符串）长度或类型不合适。常见场景：使用过短的字符串作为 HMAC 密钥。
- 推荐做法：用 Base64 编码的随机字节作为秘钥，或使用 `Keys.hmacShaKeyFor(byte[])` 构造密钥。

示例（Kotlin）：

```kotlin
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys

val secret = "使用足够长度的随机字符串或从配置读取并 base64 解码"
val key = Keys.hmacShaKeyFor(secret.toByteArray())
val token = Jwts.builder()
    .setSubject("user")
    .signWith(key)
    .compact()
```

或者使用 `BCrypt`/RSA 私钥配对时，注意选择合适的签名算法并传入 Key 类型。



## Kotlin 中的日志（替代 Lombok 的 @Slf4j）

推荐两种方式：

1. kotlin-logging（更简洁）

- 依赖：`io.github.microutils:kotlin-logging`

```kotlin
import mu.KotlinLogging
private val logger = KotlinLogging.logger {}

logger.info { "日志信息" }
```

2. 传统 SLF4J：

```kotlin
import org.slf4j.LoggerFactory
private val logger = LoggerFactory.getLogger("MyClass")
```


## MyBatis-Plus 与 Kotlin 的常见问题

你可能会遇到类似：
```
can not find lambda cache for this property ... of entity ...
```
解决建议：
- 确保在 `build.gradle.kts` 中引入 `kotlin-reflect`，并且编译器版本与依赖兼容。
- 使用 lambda 列名时，MyBatis-Plus 需要能够解析 Kotlin 的 lambda 表达式，`kotlin-reflect` 通常是必需的。
- 确认实体类使用了正确的 `@TableName`、字段与 getter/setter（Kotlin data class 或 get/set 命名）映射。


## 针对  "已使用 @ConstructorBinding 注解但被定义为了 Spring 组件" 错误

- 产生原因：`@ConstructorBinding` 用于绑定 `@ConfigurationProperties` 的不可变属性类，不应同时将其作为 Spring 管理的常规组件（如 `@Component`）。
- 解决方案：
  - 如果类是配置属性类，保留 `@ConstructorBinding` 且移除 `@Component`/`@Configuration` 等注解，并确保通过 `@EnableConfigurationProperties(YourProps::class)` 或 `@ConfigurationPropertiesScan` 注册。
  - 或者移除 `@ConstructorBinding` 并使用默认的构造器注入/Mutable 属性。


## 处理数据库唯一约束异常并返回重复字段名

如果数据库抛出类似（PostgreSQL）重复键错误：
```
ERROR: duplicate key value violates unique constraint "users_username_key"
  Key (username)=(johndoe) already exists.
```
你可以在全局异常处理器中针对 `org.postgresql.util.PSQLException` 或 MyBatis 抛出的包装异常进行解析，示例思路：

```kotlin
// 在全局 @ControllerAdvice 中
@ExceptionHandler(Exception::class)
fun handleException(e: Exception): ResponseEntity<Any> {
    // 如果是 duplicate key 错误，尝试用正则提取字段名
    val message = e.message ?: ""
    val regex = Regex("Key \(([^)]+)\)=")
    val match = regex.find(message)
    if (match != null) {
        val field = match.groupValues[1]
        return ResponseEntity.status(409).body(mapOf(
            "code" to 409,
            "message" to "重复的字段: $field",
            "data" to null
        ))
    }
    // 其他默认处理
    return ResponseEntity.status(500).body(mapOf("code" to 500, "message" to message, "data" to null))
}
```

注意：不同数据库与驱动的异常信息格式不同，正则需根据实际 message 调整。


## 常见构建错误与排查

1. Kotlin 元数据不兼容（示例）：
```
Module was compiled with an incompatible version of Kotlin. The binary version of its metadata is 2.1.0, expected version is 1.9.0.
```
- 原因：依赖中某个库使用了不同 Kotlin 编译器/元数据版本（例如使用 Kotlin 2.x 的库，而项目仍为 1.9.x）。
- 解决：升级项目的 Kotlin 版本到与依赖兼容，或替换/降级该依赖。

2. 其他建议：
- 尝试执行 `\.\gradlew.bat clean build --refresh-dependencies`。
- 查看 `build.gradle.kts` 中的 Kotlin 版本并与所有 Kotlin 相关依赖（包括第三方扩展如 MyBatis-Plus extension）保持一致。


## 调试建议

- 在遇到运行/编译问题时：
  - 查看 `./build/reports/problems/problems-report.html`（Gradle 提示的位置）
  - 使用 `--stacktrace` 或 `--info` 参数查看更详细的输出：

```
\.\gradlew.bat build --stacktrace --info
```


## 测试

- 项目包含单元测试（`src/test`），可以用 Gradle 运行：

```
\.\gradlew.bat test
```


## 贡献

欢迎提交 issue 或 PR，提交流程请先在开发分支上提交 feature 分支，确保单元测试通过并对 README 做相应更新。


## 许可证

- MIT（如需更改，请在此处说明）

