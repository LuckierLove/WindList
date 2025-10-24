package cn.luckierlove.windlist.entity.dto

import cn.luckierlove.windlist.common.enums.TodoItemPriority
import java.util.*

data class TodoItemDTO(
    var title: String?,
    var content: String?,
    var priority: TodoItemPriority?,
    var dueDate: Date?
)