package org.link.activityService.mapper;

import org.apache.ibatis.annotations.*;
import org.link.activityService.dto.ActivityDTO;
import org.link.activityService.entity.Activity;
import org.link.activityService.entity.Likes;

import java.util.List;

@Mapper
public interface ActivityMapper {
    @Select("select * from activity where status = 'NORMAL'")
    List<Activity> findAllActivity();


    @Select("select * from activity where status = 'NORMAL' and category = #{category}")
    List<Activity> findCategoryActivity(String category);

    @Select("select * from activity where activity_id = #{activityId}")
    Activity findActivityDetail(Integer activityId);

    @Update("UPDATE activity SET like_count = like_count + 1 WHERE activity_id =#{activityId}")
    void addActivityLike(Integer activityId);

    @Update("UPDATE activity SET like_count = like_count - 1 WHERE activity_id =#{activityId}")
    void deleteActivityLike(Integer activityId);

    @Update("UPDATE activity SET favorite_count = favorite_count + 1 WHERE activity_id =#{activityId}")
    void addActivityFavorite(Integer activityId);

    @Update("UPDATE activity SET favorite_count = favorite_count - 1 WHERE activity_id =#{activityId}")
    void deleteActivityFavorite(Integer activityId);

    @Select("select * from activity where organizer_id = #{userId} and status = #{activityStatus}")
    List<Activity> findActivitiesByUserStatus(String userId, String activityStatus);

    @Select("select * from activity where activity_id = #{activityId}")
    Activity findActivityByActivityId(Integer activityId);

    @Insert("insert into activity (organizer_id,activity_name,category,start_time,description,max_num,status) values (#{organizerId},#{activityName},#{category},#{startTime},#{description},#{maxNum},'NORMAL')")
    @Options(useGeneratedKeys = true, keyProperty = "activityId", keyColumn = "activity_id")
    void addActivity(ActivityDTO activityDTO);

    @Delete("delete from activity where activity_id = #{activityId}")
    void deleteActivity(Integer activityId);



    @Update("UPDATE activity SET current_num = current_num + 1 WHERE activity_id =#{activityId}")
    void addParticipatorNum(Integer activityId);

    @Update("UPDATE activity SET current_num = current_num - 1 WHERE activity_id =#{activityId}")
    void deleteParticipatorNum(Integer activityId);

    //更新活动状态（改为锁定）
    @Update("UPDATE activity SET status = 'LOCKED' WHERE activity_id =#{activityId}")
    void lockActivity(Integer activityId);

}
