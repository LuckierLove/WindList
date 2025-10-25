package cn.luckierlove.windlist.mapper

import cn.luckierlove.windlist.entity.SubTasks
import cn.luckierlove.windlist.entity.Tags
import cn.luckierlove.windlist.entity.TodoItems
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface TodoItemMapper : BaseMapper<TodoItems> {
    @Select("select id, user_id, name, color, created_at from tags where id in (select tag_id from todo_item_tags where item_id = #{todoItemId})")
    fun findTagsByTodoItemId(todoItemId: Long): MutableList<Tags>

    @Select("select id, item_id, title, completed, created_at, updated_at from sub_tasks where item_id = #{todoItemId}")
    fun findSubTasksByTodoItemId(todoItemId: Long): MutableList<SubTasks>

    @Delete("delete from sub_tasks where item_id = #{todoItemId}")
    fun deleteSubTasksByTodoItemId(todoItemId: Long)

    @Delete("delete from todo_item_tags where item_id = #{todoItemId}")
    fun deleteRelativeTagsByTodoItemId(todoItemId: Long)

    @Insert("insert into todo_item_tags (item_id, tag_id) values (#{itemId}, #{tagId})")
    fun addTagToItem(itemId: Long, tagId: Long)

    @Delete("delete from todo_item_tags where item_id = #{itemId} and tag_id = #{tagId}")
    fun removeTagFromItem(itemId: Long, tagId: Long)
}