package cn.luckierlove.windlist.controller

import cn.luckierlove.windlist.common.Result
import cn.luckierlove.windlist.entity.dto.SubTaskDTO
import cn.luckierlove.windlist.service.SubTaskService
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*


/**
 * 子任务相关接口
 */
@RestController
@RequestMapping("/subtasks")
class SubTaskController {
    private val log = LoggerFactory.getLogger(SubTaskController::class.java)

    @Resource
    private lateinit var subTaskService: SubTaskService

    /**
     * 更新子任务
     */
    @PutMapping("/{subTaskId}")
    fun updateSubTask(@PathVariable subTaskId: Long, @RequestBody subTaskDTO: SubTaskDTO): Result.Response<String> {
        log.info("Update the sub task $subTaskId to $subTaskDTO")
        subTaskService.updateSubTask(subTaskId, subTaskDTO)
        return Result.success()
    }

    /**
     * 删除子任务
     */
    @DeleteMapping("/{subTaskId}")
    fun deleteSubTask(@PathVariable subTaskId: Long): Result.Response<String> {
        log.info("Delete the sub task $subTaskId")
        subTaskService.deleteSubTask(subTaskId)
        return Result.success()
    }
}