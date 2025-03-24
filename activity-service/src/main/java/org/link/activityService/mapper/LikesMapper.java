package org.link.activityService.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.link.activityService.entity.Likes;

@Mapper
public interface LikesMapper {

    @Select("select * from likes where activity_id = #{activityId} and user_id = #{userId}")
    Likes findLike(Integer activityId, String userId);

    @Insert("insert into likes (user_id , activity_id) values (#{userId} , #{activityId})")
    void addLike(Integer activityId, String userId);

    @Delete("delete from likes where activity_id = #{activityId} and user_id = #{userId}")
    Integer deleteLike(Integer activityId, String userId);

    @Delete("delete from likes where activity_id = #{activityId}")
    void deleteLikeByActivity(Integer activityId);
}
