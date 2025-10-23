package cn.luckierlove.windlist.common.exception

import java.lang.RuntimeException

/**
 * 基础异常类
 */
open class BaseException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)