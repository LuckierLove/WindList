package cn.luckierlove.windlist.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.util.*

/**
 * 标签表
 */
data class Tags(
    /**
     * 主键
     */
    var id: Long? = null,

    /**
     * 所属用户Id
     */
    var userId: Long? = null,

    /**
     * 标签名
     */
    var name: String? = null,

    /**
     * 颜色
     */
    var color: String? = null,

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    var createdAt: Date? = null,

)
