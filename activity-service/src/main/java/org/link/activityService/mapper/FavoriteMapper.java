package org.link.activityService.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.link.activityService.entity.Favorite;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Select("select * from favorite where activity_id = #{activityId} and user_id = #{userId}")
    Favorite findFavorite(Integer activityId, String userId);

    @Insert("insert into favorite (user_id , activity_id) values (#{userId} , #{activityId})")
    void addFavorite(Integer activityId, String userId);

    @Delete("delete from favorite where activity_id = #{activityId} and user_id = #{userId}")
    Integer deleteFavorite(Integer activityId, String userId);

    @Select("select activity_id from favorite where user_id = #{userId}")
    List<Integer> findActivityIdByUserId(String userId);

    @Delete("delete from participation_record where activity_id = #{activityId}")
    void deleteFavoriteByActivity(Integer activityId);
}
