package cn.luckierlove.windlist.entity.vo

import cn.luckierlove.windlist.common.enums.TodoItemPriority
import cn.luckierlove.windlist.common.enums.TodoItemStatus
import cn.luckierlove.windlist.entity.SubTasks
import cn.luckierlove.windlist.entity.Tags
import java.util.*

data class TodoItemVO(
    var id: Long? = null,
    var listId: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var status: TodoItemStatus? = null,
    var priority: TodoItemPriority? = null,
    var dueDate: Date? = null,
    var completedAt: Date? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var subTasks: MutableList<SubTasks>? = null,
    var tags: MutableList<Tags>? = null
)
