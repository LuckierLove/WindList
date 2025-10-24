package cn.luckierlove.windlist.common.enums

/**
 * 待办事项优先级
 */
@Suppress("unused")
enum class TodoItemPriority(val code: String, val description: String) {
    LOW("LOW", "优先级低"),
    MEDIUM("MEDIUM", "优先级中"),
    HIGH("HIGH", "优先级高");

    override fun toString(): String = code

    companion object {
        fun fromCode(code: String?): TodoItemPriority? = code?.let { c ->
            entries.find { it.code == c }
        }
    }
}
