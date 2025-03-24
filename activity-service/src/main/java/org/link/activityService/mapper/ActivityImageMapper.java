package org.link.activityService.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.link.activityService.entity.ActivityImage;

import java.util.List;

@Mapper
public interface ActivityImageMapper {
    @Select("select * from activity_image where activity_id = #{activityId}")
    List<ActivityImage> findActivityImages(Integer activityId);

    @Insert("insert into activity_image (activity_id,photo_path) values (#{activityId} , #{picture})")
    void addActivityImage(String activityId, String picture);

    @Delete("delete from activity_image where activity_id = #{activityId}")
    void deleteActivityImage(Integer activityId);
}
