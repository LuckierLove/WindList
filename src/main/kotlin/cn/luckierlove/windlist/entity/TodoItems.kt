package cn.luckierlove.windlist.entity

import cn.luckierlove.windlist.common.enums.TodoItemPriority
import cn.luckierlove.windlist.common.enums.TodoItemStatus
import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.util.Date

/**
 * 待办事项表
 */
data class TodoItems(
    /**
     * 主键
     */
    var id: Long?,

    /**
     * 所属清单Id
     */
    var listId: Long?,

    /**
     * 标题
     */
    var title: String?,

    /**
     * 具体内容
     */
    var content: String?,

    /**
     * 状态
     */
    var status: TodoItemStatus?,

    /**
     * 优先级
     */
    var priority: TodoItemPriority?,

    /**
     * 截止日期
     */
    var dueDate: Date?,

    /**
     * 完成日期
     */
    var completedAt: Date?,

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    var createdAt: Date?,

    /**
     * 修改日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updatedAt: Date?,
)
