package cn.luckierlove.windlist.entity

import cn.luckierlove.windlist.constant.TodoItemPriority
import cn.luckierlove.windlist.constant.TodoItemStatus
import java.time.LocalDateTime
import java.util.Date

/**
 * 待办事项表
 */
data class TodoItems(
    /**
     * 主键
     */
    var id: Long?,

    /**
     * 所属清单Id
     */
    var listId: Long?,

    /**
     * 标题
     */
    var title: String?,

    /**
     * 具体内容
     */
    var content: String?,

    /**
     * 状态
     */
    var status: TodoItemStatus?,

    /**
     * 优先级
     */
    var priority: TodoItemPriority?,

    /**
     * 截止日期
     */
    var dueDate: Date?,

    /**
     * 完成日期
     */
    var completedAt: Date?,

    /**
     * 创建日期
     */
    var createdAt: Date?,

    /**
     * 修改日期
     */
    var updatedAt: Date?,
)
