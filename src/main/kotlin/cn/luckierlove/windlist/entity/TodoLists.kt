package cn.luckierlove.windlist.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.util.Date

/**
 * 待办事项清单表
 */
data class TodoLists(
    /**
     * 主键
     */
    var id: Long? = null,

    /**
     * 用户Id
     */
    var userId: Long? = null,

    /**
     * 事项清单名称
     */
    var name: String? = null,

    /**
     * 描述
     */
    var description: String? = null,

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
