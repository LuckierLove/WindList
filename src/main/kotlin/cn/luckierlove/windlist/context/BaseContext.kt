package cn.luckierlove.windlist.context

object BaseContext {
    var threadLocal: ThreadLocal<Long> = ThreadLocal<Long>()

    fun setCurrentUserId(userId: Long) {
        threadLocal.set(userId)
    }

    fun getCurrentUserId(): Long? {
        return threadLocal.get()
    }
}