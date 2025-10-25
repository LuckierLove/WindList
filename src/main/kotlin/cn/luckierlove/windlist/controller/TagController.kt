package cn.luckierlove.windlist.controller

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.context.BaseContext
import cn.luckierlove.windlist.entity.Tags
import cn.luckierlove.windlist.entity.dto.TagDTO
import cn.luckierlove.windlist.service.TagService
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * 标签相关接口
 */
@RestController
@RequestMapping("/tags")
class TagController {
    private val log = LoggerFactory.getLogger(TagController::class.java)

    @Autowired
    @Resource
    private lateinit var tagService: TagService

    /**
     * 获取当前用户所有标签
     */
    @GetMapping
    fun getAllTags(): Result.Response<MutableList<Tags>>{
        log.info("Get all tags for current user ${BaseContext.getCurrentUserId()}")
        val tags = tagService.getTagsByCurrentUserId()
        return Result.success(tags)
    }

    /**
     * 创建新标签
     */
    @PostMapping
    fun saveTag(@RequestBody tagDTO: TagDTO): Result.Response<String>{
        log.info("Save a new tag $tagDTO for current user ${BaseContext.getCurrentUserId()}")
        tagService.save(tagDTO)
        return Result.success()
    }

    /**
     * 更新标签
     */
    @PutMapping("/{tagId}")
    fun updateTag(@PathVariable tagId: Long, @RequestBody tagDTO: TagDTO): Result.Response<String> {
        log.info("Update the tag $tagId to $tagDTO")
        tagService.update(tagId, tagDTO)
        return Result.success()
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{tagId}")
    fun deleteTag(@PathVariable tagId: Long): Result.Response<String> {
        log.info("Delete the tag $tagId")
        tagService.delete(tagId)
        return Result.success()
    }
}