package cn.luckierlove.windlist.service

import cn.luckierlove.windlist.entity.TodoLists
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
}