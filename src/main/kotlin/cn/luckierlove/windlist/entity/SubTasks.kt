package cn.luckierlove.windlist.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.util.*

/**
 * 子任务表
 */
data class SubTasks(
    /**
     * 主键
     */
    var id: Long? = null,

    /**
     * 所属待办Id
     */
    var itemId: Long? = null,

    /**
     * 标题
     */
    var title: String? = null,

    /**
     * 完成情况
     */
    var completed: Boolean? = false,

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    var createdAt: Date? = null,

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updatedAt: Date? = null,
)
