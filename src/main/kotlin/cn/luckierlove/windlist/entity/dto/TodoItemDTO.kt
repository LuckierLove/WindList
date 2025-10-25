package cn.luckierlove.windlist.entity.dto

import cn.luckierlove.windlist.common.enums.TodoItemPriority
import cn.luckierlove.windlist.common.enums.TodoItemStatus
import java.util.*

data class TodoItemDTO(
    var title: String?,
    var content: String?,
    var status: TodoItemStatus?,
    var priority: TodoItemPriority?,
    var dueDate: Date?
)