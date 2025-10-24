package cn.luckierlove.windlist.handler

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.common.exception.BaseException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 全局异常处理器
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(ex: BaseException): Result.Response<String> {
        return Result.error(ex.message ?: "未知错误")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): Result.Response<String> {
            if (ex is org.postgresql.util.PSQLException) {
                val msg = ex.message ?: return Result.error("未知错误")

                // 优先从 "Key (username)=(johndoe) already exists." 提取字段名
                val keyRegex = Regex("Key \\(([^)]+)\\)")
                val keyMatch = keyRegex.find(msg)
                if (keyMatch != null) {
                    val fields = keyMatch.groupValues[1].split(",").map { it.trim() }
                    val field = fields.firstOrNull()
                    if (!field.isNullOrEmpty()) {
                        return Result.error("字段已存在：$field")
                    }
                }

                // 从 constraint 名称中尝试（例如 users_username_key -> username）
                val consRegex = Regex("unique constraint \"([^\"]+)\"")
                val consMatch = consRegex.find(msg)
                if (consMatch != null) {
                    val cons = consMatch.groupValues[1]
                    val parts = cons.split("_")
                    if (parts.size >= 2) {
                        // 常见约束名格式：table_column_key
                        val inferred = parts.getOrNull(1)
                        if (!inferred.isNullOrEmpty()) {
                            return Result.error("字段已存在：$inferred")
                        }
                    }
                    return Result.error("违反唯一约束：$cons")
                }

                return Result.error("数据已存在，违反唯一约束")
            }
        ex.printStackTrace()
        return Result.error(ex.message ?: "未知错误")
    }
}