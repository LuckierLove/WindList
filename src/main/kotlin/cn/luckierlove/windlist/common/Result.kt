package cn.luckierlove.windlist.common

/**
 * 统一结果封装
 */
object Result {
    const val SUCCESS = 200
    const val ERROR = 500

    data class Response<T>(
        val code: Int,
        val message: String,
        val data: T? = null
    )

    fun <T> success(data: T? = null, message: String = "Success"): Response<T> {
        return Response(SUCCESS, message, data)
    }

    fun <T> error(message: String = "Error", data: T? = null): Response<T> {
        return Response(ERROR, message, data)
    }
}