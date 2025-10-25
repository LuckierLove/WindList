package cn.luckierlove.windlist.service

import cn.luckierlove.windlist.entity.dto.TodoItemDTO
import cn.luckierlove.windlist.entity.vo.TodoItemVO

interface TodoItemService {
    /**
     * 根据待办事项id获取其详细信息
     */
    fun getById(itemId: Long): TodoItemVO

    /**
     * 更新单个待办事项
     */
    fun updateTodoItem(itemId: Long, todoItemDTO: TodoItemDTO)

    /**
     * 删除单个待办事项
     */
    fun deleteTodoItem(itemId: Long)
}