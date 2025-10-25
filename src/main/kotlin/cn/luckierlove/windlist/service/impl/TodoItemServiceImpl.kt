package cn.luckierlove.windlist.service.impl

import cn.luckierlove.windlist.entity.SubTasks
import cn.luckierlove.windlist.entity.Tags
import cn.luckierlove.windlist.entity.TodoItems
import cn.luckierlove.windlist.entity.dto.SubTaskDTO
import cn.luckierlove.windlist.entity.dto.TodoItemDTO
import cn.luckierlove.windlist.entity.vo.TodoItemVO
import cn.luckierlove.windlist.mapper.SubTaskMapper
import cn.luckierlove.windlist.mapper.TodoItemMapper
import cn.luckierlove.windlist.service.TodoItemService
import jakarta.annotation.Resource
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoItemServiceImpl: TodoItemService {
    @Resource
    private lateinit var todoItemMapper: TodoItemMapper
    @Resource
    private lateinit var subTaskMapper: SubTaskMapper

    /**
     * 根据待办事项id获取其详细信息
     */
    override fun getById(itemId: Long): TodoItemVO {
        val todoItem = todoItemMapper.selectById(itemId)
        val tags: MutableList<Tags> = todoItemMapper.findTagsByTodoItemId(itemId)
        val subTasks: MutableList<SubTasks> = todoItemMapper.findSubTasksByTodoItemId(itemId)
        val todoItemVO = TodoItemVO()
        BeanUtils.copyProperties(todoItem, todoItemVO)
        todoItemVO.tags = tags
        todoItemVO.subTasks = subTasks
        return todoItemVO
    }

    /**
     * 更新单个待办事项
     */
    override fun updateTodoItem(itemId: Long, todoItemDTO: TodoItemDTO) {
        val todoItem = TodoItems().apply {
            this.id = itemId
            this.status = todoItemDTO.status
            this.dueDate = todoItemDTO.dueDate
            this.priority = todoItemDTO.priority
            this.title = todoItemDTO.title
            this.content = todoItemDTO.content
        }
        todoItemMapper.updateById(todoItem)
    }

    /**
     * 删除单个待办事项
     */
    @Transactional
    override fun deleteTodoItem(itemId: Long) {
        // 删除待办事项关联的子任务
        todoItemMapper.deleteSubTasksByTodoItemId(itemId)
        // 删除待办事项关联的标签
        todoItemMapper.deleteRelativeTagsByTodoItemId(itemId)
        // 删除待办事项本身
        todoItemMapper.deleteById(itemId)
    }

    /**
     * 为待办事项添加子任务
     */
    override fun addSubTask(itemId: Long, subTaskDTO: SubTaskDTO) {
        val subTask = SubTasks().apply {
            this.itemId = itemId
            this.title = subTaskDTO.title
        }

        subTaskMapper.insert(subTask)
    }

    /**
     * 为待办事项添加标签
     */
    override fun addTagToItem(itemId: Long, tagId: Long) {
        todoItemMapper.addTagToItem(itemId, tagId)
    }

    /**
     * 从待办事项移除标签
     */
    override fun removeTagFromItem(itemId: Long, tagId: Long) {
        todoItemMapper.removeTagFromItem(itemId, tagId)
    }
}