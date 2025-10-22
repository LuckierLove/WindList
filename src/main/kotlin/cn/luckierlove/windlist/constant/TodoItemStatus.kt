package cn.luckierlove.windlist.constant

/**
 * 待办事项状态
 */
@Suppress("unused")
enum class TodoItemStatus(val code: String, val description: String) {
    PENDING("PENDING", "待处理"), // 待处理
    IN_PROGRESS("IN_PROGRESS", "进行中"), // 进行中
    COMPLETED("COMPLETED", "已完成"), // 已完成
    ARCHIVED("ARCHIVED", "已归档"); // 已归档

    override fun toString(): String = code

    companion object {
        fun fromCode(code: String?): TodoItemStatus? = code?.let { c ->
            entries.find { it.code == c }
        }
    }
}
