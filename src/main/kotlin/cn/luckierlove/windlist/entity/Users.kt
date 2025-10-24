// kotlin
package cn.luckierlove.windlist.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import java.util.Date

/**
 * 用户表
 */
data class Users(
    /**
     * 用户Id
     */
    @TableId(type = IdType.AUTO)
    var id: Long? = null,

    /**
     * 用户名
     */
    var username: String? = null,

    /**
     * 邮箱
     */
    var email: String? = null,

    /**
     * 密码
     */
    var password: String? = null,

    /**
     * 昵称
     */
    var nickname: String? = null,

    /**
     * 头像URL
     */
    var avatarUrl: String? = null,

    /**
     * 是否启用
     */
    var enabled: Boolean? = null,

    /**
     * 账户是否过期
     */
    var accountNonExpired: Boolean? = null,

    /**
     * 密码是否过期
     */
    var credentialsNonExpired: Boolean? = null,

    /**
     * 账户是否锁定
     */
    var accountNonLocked: Boolean? = null,

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
