package cn.luckierlove.windlist.constant

/**
 * 用户角色枚举
 */
enum class UserRole
    (val value: Int, val description: String)
{
    ROLE_USER(1, "普通用户"),
    ROLE_ADMIN(2, "管理员");

    override fun toString(): String = value.toString()

    companion object {
        fun fromValue(value: Int?): UserRole? = value?.let { v ->
            entries.find { it.value == v }
        }
    }
}