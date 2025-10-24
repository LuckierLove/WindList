package cn.luckierlove.windlist.controller

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.context.BaseContext
import cn.luckierlove.windlist.entity.TodoLists
import cn.luckierlove.windlist.entity.dto.TodoListDTO
import cn.luckierlove.windlist.service.TodoListService
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

/**
 * 待办事项列表接口
 */
@RestController
@RequestMapping("/lists")
class TodoListController {
    private val log = LoggerFactory.getLogger(TodoListController::class.java)
    @Autowired
    @Resource
    private lateinit var todoListService: TodoListService

    /**
     * 创建清单
     */
    @PostMapping
    fun createTodoList(@RequestBody todoListDTO: TodoListDTO): Result.Response<String> {
        log.info("Creating new todo list $todoListDTO")
        todoListService.createTodoList(todoListDTO)
        return Result.success()
    }

    /**
     * 获取当前用户的所有待办列表
     */
    @GetMapping
    fun getAllTodoListFromCurrentUser(): Result.Response<MutableList<TodoLists>>{
        log.info("Get all todo lists from current user ${BaseContext.getCurrentUserId()}")
        return Result.success(todoListService.getTodoListsFromCurrentUser())
    }

    /**
     * 获取单个待办列表
     */
    @GetMapping("/{listId}")
    fun getTodoListDetail(@PathVariable listId: Long): Result.Response<TodoLists> {
        log.info("Get a list detail info by listId $listId")
        return Result.success(todoListService.getById(listId))
    }

    /**
     * 更新待办列表
     */
    @PutMapping("/{listId}")
    fun updateTodoList(@PathVariable listId: Long, @RequestBody todoListDTO: TodoListDTO): Result.Response<String> {
        log.info("Update todo list $listId to $todoListDTO")
        todoListService.updateTodoList(listId, todoListDTO)
        return Result.success()
    }

    /**
     * 删除待办列表
     */
    @DeleteMapping("/{listId}")
    fun deleteTodoList(@PathVariable listId: Long): Result.Response<String> {
        log.info("Delete todo list $listId")
        todoListService.deleteById(listId)
        return Result.success()
    }
}