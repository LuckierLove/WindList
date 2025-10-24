package cn.luckierlove.windlist.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.time.LocalDateTime
import java.util.Date

/**
 * 子任务表
 */
data class SubTasks(
    /**
     * 主键
     */
    var id: Long?,

    /**
     * 所属待办Id
     */
    var itemId: Long?,

    /**
     * 标题
     */
    var title: String?,

    /**
     * 完成情况
     */
    var completed: Boolean?,

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    var createdAt: Date?,

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updatedAt: Date?,
)
