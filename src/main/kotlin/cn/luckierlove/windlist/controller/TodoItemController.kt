package cn.luckierlove.windlist.controller

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.entity.dto.SubTaskDTO
import cn.luckierlove.windlist.entity.dto.TodoItemDTO
import cn.luckierlove.windlist.entity.vo.TodoItemVO
import cn.luckierlove.windlist.service.TodoItemService
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

/**
 * 待办事项相关接口
 */
@RestController
@RequestMapping("/items")
class TodoItemController {
    private val log = LoggerFactory.getLogger(TodoItemController::class.java)

    @Resource
    private lateinit var todoItemService: TodoItemService

    /**
     * 获取单个待办事项详细信息
     */
    @GetMapping("/{itemId}")
    fun getItemDetail(@PathVariable itemId: Long): Result.Response<TodoItemVO> {
        log.info("Get a todo item $itemId detail info")
        val result: TodoItemVO = todoItemService.getById(itemId)
        return Result.success(result)
    }

    /**
     * 更新单个待办事项
     */
    @PutMapping("/{itemId}")
    fun updateItem(@PathVariable itemId: Long, @RequestBody todoItemDTO: TodoItemDTO): Result.Response<String> {
        log.info("Update the todo item $itemId to $todoItemDTO")
        todoItemService.updateTodoItem(itemId, todoItemDTO)
        return Result.success()
    }

    /**
     * 删除单个待办事项
     */
    @DeleteMapping("/{itemId}")
    fun deleteItem(@PathVariable itemId: Long): Result.Response<String> {
        log.info("Delete the todo item $itemId")
        todoItemService.deleteTodoItem(itemId)
        return Result.success()
    }

    /**
     * 为待办事项添加子任务
     */
    @PostMapping("/{itemId}/subtasks")
    fun addSubTask(@PathVariable itemId: Long, @RequestBody subTaskDTO: SubTaskDTO): Result.Response<String> {
        log.info("Add a sub task to todo item $itemId")
        todoItemService.addSubTask(itemId, subTaskDTO)
        return Result.success()
    }

    /**
     * 为待办事项添加标签
     */
    @PostMapping("/{itemId}/tags/{tagId}")
    fun addTagToItem(@PathVariable itemId: Long, @PathVariable tagId: Long): Result.Response<String> {
        log.info("Add tag $tagId to todo item $itemId")
        todoItemService.addTagToItem(itemId, tagId)
        return Result.success()
    }

    /**
     * 为待办事项移除标签
     */
    @DeleteMapping("/{itemId}/tags/{tagId}")
    fun removeTagFromItem(@PathVariable itemId: Long, @PathVariable tagId
: Long): Result.Response<String> {
        log.info("Remove tag $tagId from todo item $itemId")
        todoItemService.removeTagFromItem(itemId, tagId)
        return Result.success()
    }
}
