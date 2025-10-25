package cn.luckierlove.windlist.service

import cn.luckierlove.windlist.entity.dto.SubTaskDTO

interface SubTaskService {

    /**
     * 更新子任务
     */
    fun updateSubTask(subTaskId: Long, subTaskDTO: SubTaskDTO)

    /**
     * 删除子任务
     */
    fun deleteSubTask(subTaskId: Long)
}