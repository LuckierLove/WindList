package cn.luckierlove.windlist.entity

import java.time.LocalDateTime
import java.util.Date

/**
 * 待办事项清单表
 */
data class TodoLists(
    /**
     * 主键
     */
    var id: Long?,

    /**
     * 用户Id
     */
    var userId: Long?,

    /**
     * 事项清单名称
     */
    var name: String?,

    /**
     * 描述
     */
    var description: String?,

    /**
     * 创建时间
     */
    var createdAt: Date?,

    /**
     * 修改时间
     */
    var updatedAt: Date?,
)
