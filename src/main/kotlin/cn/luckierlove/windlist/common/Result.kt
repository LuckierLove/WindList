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

    /**
     * 分页统一结果封装
     */
    data class PageResponse<T>(
        val code: Int, // 200 成功，500 失败
        val message: String,
        val data: T? = null,
        val page: Long,
        val pageSize: Long,
        val total: Long
    )

    fun <T> success(data: T? = null, message: String = "Success"): Response<T> {
        return Response(SUCCESS, message, data)
    }

    fun <T> error(message: String = "Error", data: T? = null): Response<T> {
        return Response(ERROR, message, data)
    }

    fun <T> pageSuccess(
        data: T? = null,
        page: Long,
        pageSize: Long,
        total: Long,
        message: String = "Success"
    ): PageResponse<T> {
        return PageResponse(SUCCESS, message, data, page, pageSize, total)
    }

    fun <T> pageError(
        message: String = "Error",
        data: T? = null,
        page: Long,
        pageSize: Long,
        total: Long
    ): PageResponse<T> {
        return PageResponse(ERROR, message, data, page, pageSize, total)
    }

}