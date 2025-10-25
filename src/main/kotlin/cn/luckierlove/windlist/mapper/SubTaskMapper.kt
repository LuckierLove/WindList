package cn.luckierlove.windlist.mapper

import cn.luckierlove.windlist.entity.SubTasks
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SubTaskMapper: BaseMapper<SubTasks> {
}