package cn.luckierlove.windlist.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.time.LocalDateTime
import java.util.Date

/**
 * 标签表
 */
data class Tags(
    /**
     * 主键
     */
    var id: Long?,

    /**
     * 所属用户Id
     */
    var userId: Long?,

    /**
     * 标签名
     */
    var name: String?,

    /**
     * 颜色
     */
    var color: String?,

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    var createAt: Date?,
)
