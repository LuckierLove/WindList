package cn.luckierlove.windlist.service

import cn.luckierlove.windlist.entity.dto.SubTaskDTO
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

    /**
     * 为待办事项添加子任务
     */
    fun addSubTask(itemId: Long, subTaskDTO: SubTaskDTO)

    /**
     * 为待办事项添加标签
     */
    fun addTagToItem(itemId: Long, tagId: Long)

    /**
     * 从待办事项移除标签
     */
    fun removeTagFromItem(itemId: Long, tagId: Long)
}