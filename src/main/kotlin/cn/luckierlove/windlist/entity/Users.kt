package cn.luckierlove.windlist.entity

import java.time.LocalDateTime

/**
 * 用户表
 */
data class Users(
    /**
     * 用户Id
     */
    var id: Long?,

    /**
     * 用户名
     */
    var username: String?,

    /**
     * 邮箱
     */
    var email: String?,

    /**
     * 密码
     */
    var password: String?,

    /**
     * 昵称
     */
    var nickname: String?,

    /**
     * 头像URL
     */
    var avatarUrl: String?,

    /**
     * 是否启用
     */
    var enabled: Boolean?,

    /**
     * 账户是否过期
     */
    var accountNonExpired: Boolean?,

    /**
     * 密码是否过期
     */
    var credentialsNonExpired: Boolean?,

    /**
     * 账户是否锁定
     */
    var accountNonLocked: Boolean?,

    /**
     * 创建时间
     */
    var createdAt: LocalDateTime?,

    /**
     * 修改时间
     */
    var updatedAt: LocalDateTime?,
)
