package cn.luckierlove.windlist.service

import cn.luckierlove.windlist.entity.Tags
import cn.luckierlove.windlist.entity.dto.TagDTO

interface TagService {
    /**
     * 获取当前用户所有标签
     */
    fun getTagsByCurrentUserId(): MutableList<Tags>

    /**
     * 创建新标签
     */
    fun save(tagDTO: TagDTO)

    /**
     * 更新标签
     */
    fun update(tagId: Long, tagDTO: TagDTO)

    /**
     * 删除标签
     */
    fun delete(tagId: Long)
}