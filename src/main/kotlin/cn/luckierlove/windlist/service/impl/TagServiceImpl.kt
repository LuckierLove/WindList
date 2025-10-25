package cn.luckierlove.windlist.service.impl

import cn.luckierlove.windlist.context.BaseContext
import cn.luckierlove.windlist.entity.Tags
import cn.luckierlove.windlist.entity.dto.TagDTO
import cn.luckierlove.windlist.mapper.TagMapper
import cn.luckierlove.windlist.service.TagService
import jakarta.annotation.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TagServiceImpl: TagService {
    @Resource
    private lateinit var tagMapper: TagMapper

    /**
     * 获取当前用户所有标签
     */
    override fun getTagsByCurrentUserId(): MutableList<Tags>{
        val userId = BaseContext.getCurrentUserId()
        return tagMapper.getTagsByCurrentUserId(userId)
    }

    /**
     * 创建新标签
     */
    override fun save(tagDTO: TagDTO) {
        val tag = Tags().apply {
            this.name = tagDTO.name
            this.color = tagDTO.color
            this.userId = BaseContext.getCurrentUserId()
        }
        tagMapper.insert(tag)
    }

    /**
     * 更新标签
     */
    override fun update(tagId: Long, tagDTO: TagDTO) {
        val tag = Tags().apply {
            this.id = tagId
            this.name = tagDTO.name
            this.color = tagDTO.color
        }
        tagMapper.updateById(tag)
    }

    /**
     * 删除标签
     */
    @Transactional
    override fun delete(tagId: Long) {
        tagMapper.deleteById(tagId)
        tagMapper.deleteRelationsByTagId(tagId)
    }
}