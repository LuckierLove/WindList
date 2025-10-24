package cn.luckierlove.windlist.service.impl

import cn.luckierlove.windlist.constant.TodoListsConstant
import cn.luckierlove.windlist.context.BaseContext
import cn.luckierlove.windlist.entity.TodoLists
import cn.luckierlove.windlist.entity.dto.TodoListDTO
import cn.luckierlove.windlist.mapper.TodoListMapper
import cn.luckierlove.windlist.service.TodoListService
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

@Service
class TodoListServiceImpl: TodoListService, ServiceImpl<TodoListMapper, TodoLists>() {
    @Resource
    private lateinit var todoListMapper: TodoListMapper

    /**
     * 创建待办事项列表
     */
    override fun createTodoList(todoListDTO: TodoListDTO) {
        val todoList = TodoLists().apply {
            this.name = todoListDTO.name
            this.description = todoListDTO.description
            this.userId = BaseContext.getCurrentUserId()
        }
        todoListMapper.insert(todoList)
    }

    /**
     * 根据当前用户获取其所有代办清单
     */
    override fun getTodoListsFromCurrentUser(): MutableList<TodoLists>{
        val userId = BaseContext.getCurrentUserId()
        val wrapper: QueryWrapper<TodoLists> = QueryWrapper()
        wrapper.eq(TodoListsConstant.USER_ID_FIELD, userId)
        return todoListMapper.selectList(wrapper)
    }

    /**
     * 根据id获取待办事项列表
     */
    override fun getById(listId: Long): TodoLists?{
        return todoListMapper.selectById(listId)
    }

    /**
     * 更新待办事项列表
     */
    override fun updateTodoList(listId: Long, todoListDTO: TodoListDTO){
        val todoList = TodoLists().apply {
            this.id = listId
            this.name = todoListDTO.name
            this.description = todoListDTO.description
        }
        todoListMapper.updateById(todoList)
    }

    /**
     * 删除待办事项列表
     */
    override fun deleteById(listId: Long) {
        todoListMapper.deleteById(listId)
    }
}