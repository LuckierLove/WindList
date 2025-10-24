package cn.luckierlove.windlist.entity

import cn.luckierlove.windlist.common.enums.TodoItemPriority
import cn.luckierlove.windlist.common.enums.TodoItemStatus
import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.util.*

/**
 * 待办事项表
 */
data class TodoItems(
    /**
     * 主键
     */
    var id: Long? = null,

    /**
     * 所属清单Id
     */
    var listId: Long? = null,

    /**
     * 标题
     */
    var title: String? = null,

    /**
     * 具体内容
     */
    var content: String? = null,

    /**
     * 状态
     */
    var status: TodoItemStatus? = null,

    /**
     * 优先级
     */
    var priority: TodoItemPriority? = null,

    /**
     * 截止日期
     */
    var dueDate: Date? = null,

    /**
     * 完成日期
     */
    var completedAt: Date? = null,

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    var createdAt: Date? = null,

    /**
     * 修改日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updatedAt: Date? = null,
)
