package cn.luckierlove.windlist.service

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.common.enums.TodoItemStatus
import cn.luckierlove.windlist.entity.TodoItems
import cn.luckierlove.windlist.entity.TodoLists
import cn.luckierlove.windlist.entity.dto.TodoItemDTO
import cn.luckierlove.windlist.entity.dto.TodoListDTO

interface TodoListService {
    /**
     * 创建待办事项列表
     */
    fun createTodoList(todoListDTO: TodoListDTO)

    /**
     * 根据当前用户获取其所有代办清单
     */
    fun getTodoListsFromCurrentUser(): MutableList<TodoLists>

    /**
     * 根据id获取待办事项列表
     */
    fun getById(listId: Long): TodoLists?

    /**
     * 更新待办事项列表
     */
    fun updateTodoList(listId: Long, todoListDTO: TodoListDTO)

    /**
     * 删除待办事项列表
     */
    fun deleteById(listId: Long)

    /**
     * 创建待办事项
     */
    fun createTodoItem(listId: Long, todoItemDTO: TodoItemDTO)

    /**
     * 根据待办事项列表id获取其所有待办事项
     */
    fun getTodoItemsFromList(
        listId: Long,
        page: Long,
        pageSize: Long,
        status: TodoItemStatus?
    ): Result.PageResponse<MutableList<TodoItems>>
}