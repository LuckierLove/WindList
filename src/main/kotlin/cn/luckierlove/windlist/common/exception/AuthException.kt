package cn.luckierlove.windlist.common.exception


/**
 * 认证异常类
 */
class AuthException (
    message: String? = null,
    cause: Throwable? = null
):BaseException(message, cause)