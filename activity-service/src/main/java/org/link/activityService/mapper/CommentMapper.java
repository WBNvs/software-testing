package org.link.activityService.mapper;

import org.apache.ibatis.annotations.*;
import org.link.activityService.entity.Comment;
import org.link.activityService.entity.Report;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("select * from comment where activity_id = #{activityId}")
    List<Comment> findActivityComments(Integer activityId);

    @Insert("insert into comment (user_id , activity_id,content,floor,parent_Floor) values (#{insertComment.userId} , #{insertComment.activityId},#{insertComment.content},#{insertComment.floor},#{insertComment.parentFloor})")
    void addComment(@Param("insertComment")Comment insertComment);

    @Select("select * from comment where activity_id = #{activityId} and floor = #{floor}")
    Comment findCertainComment(@Param("activityId")Integer activityId , @Param("floor")Integer floor);

    //根据活动id查询对应的评论数量，返回数量值
    @Select("select COUNT(*) from comment WHERE activity_id = #{activityId}")
    Integer getCommentCountByActivityId(@Param("activityId") Integer activityId);

    //删除评论- 根据评论id设置状态和content
    @Update("UPDATE comment SET status = 'DELETED', content = '该评论已删除' WHERE comment_id = #{commentId}")
    void deleteComment(@Param("commentId") Integer commentId);

    @Delete("delete from comment where activity_id = #{activityId}")
    void deleteCommentByActivity(Integer activityId);
}
