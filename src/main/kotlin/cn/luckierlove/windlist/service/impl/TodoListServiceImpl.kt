package cn.luckierlove.windlist.service.impl

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.common.enums.TodoItemStatus
import cn.luckierlove.windlist.constant.TodoItemConstant
import cn.luckierlove.windlist.constant.TodoListsConstant
import cn.luckierlove.windlist.context.BaseContext
import cn.luckierlove.windlist.entity.TodoItems
import cn.luckierlove.windlist.entity.TodoLists
import cn.luckierlove.windlist.entity.dto.TodoItemDTO
import cn.luckierlove.windlist.entity.dto.TodoListDTO
import cn.luckierlove.windlist.mapper.TodoItemMapper
import cn.luckierlove.windlist.mapper.TodoListMapper
import cn.luckierlove.windlist.service.TodoListService
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

@Service
class TodoListServiceImpl: TodoListService {
    @Resource
    private lateinit var todoListMapper: TodoListMapper

    @Resource
    private lateinit var todoItemMapper: TodoItemMapper

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

    /**
     * 创建待办事项
     */
    override fun createTodoItem(listId: Long, todoItemDTO: TodoItemDTO) {
        val todoItem = TodoItems().apply {
            this.listId = listId
            this.title = todoItemDTO.title
            this.content = todoItemDTO.content
            this.status = TodoItemStatus.PENDING
            this.priority = todoItemDTO.priority
            this.dueDate = todoItemDTO.dueDate
            this.completedAt = null
        }
        todoItemMapper.insert(todoItem)
    }

    /**
     * 根据待办事项列表id获取其所有待办事项
     */
    override fun getTodoItemsFromList(
        listId: Long,
        page: Long,
        pageSize: Long,
        status: TodoItemStatus?
    ): Result.PageResponse<MutableList<TodoItems>> {
        val wrapper: QueryWrapper<TodoItems> = QueryWrapper()
        wrapper.eq(TodoItemConstant.LIST_ID_FILED, listId)
        if(status != null) wrapper.eq(TodoListsConstant.STATUS_FIELD, status.toString())
        val pageInfo = Page<TodoItems>(page, pageSize)
        val pageResult = todoItemMapper.selectPage(pageInfo, wrapper)
        return Result.pageSuccess(
            data = pageResult.records,
            total = pageResult.total,
            page = pageResult.current,
            pageSize = pageResult.size
        )
    }
}