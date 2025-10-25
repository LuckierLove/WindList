package cn.luckierlove.windlist.service.impl

import cn.luckierlove.windlist.entity.SubTasks
import cn.luckierlove.windlist.entity.dto.SubTaskDTO
import cn.luckierlove.windlist.mapper.SubTaskMapper
import cn.luckierlove.windlist.service.SubTaskService
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

@Service
class SubTaskServiceImpl: SubTaskService {
    @Resource
    private lateinit var subTaskMapper: SubTaskMapper

    /**
     * 更新子任务
     */
    override fun updateSubTask(subTaskId: Long, subTaskDTO: SubTaskDTO) {
        val subTask = SubTasks().apply {
            this.id = subTaskId
            this.title = subTaskDTO.title
            this.completed = subTaskDTO.completed
        }
        subTaskMapper.updateById(subTask)
    }


    /**
     * 删除子任务
     */
    override fun deleteSubTask(subTaskId: Long) {
        subTaskMapper.deleteById(subTaskId)
    }
}