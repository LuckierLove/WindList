package cn.luckierlove.windlist.mapper

import cn.luckierlove.windlist.entity.Tags
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface TagMapper: BaseMapper<Tags> {

    @Select("select id, user_id, name, color, created_at from tags where user_id = #{userId}")
    fun getTagsByCurrentUserId(userId: Long?): MutableList<Tags>

    @Delete("delete from todo_item_tags where tag_id = #{tagId}")
    fun deleteRelationsByTagId(tagId: Long)
}