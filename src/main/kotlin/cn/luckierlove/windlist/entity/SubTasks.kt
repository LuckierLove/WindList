package cn.luckierlove.windlist.entity

import java.time.LocalDateTime

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
    var createdAt: LocalDateTime?,

    /**
     * 修改时间
     */
    var updatedAt: LocalDateTime?,
)
