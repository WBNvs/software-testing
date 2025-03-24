package org.link.pointService.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.link.pointService.entity.Point;

@Mapper
public interface PointMapper {


    @Select(("select * from point where user_id = #{userId}"))
    Point findPoint(String userId);

    @Insert("insert into point (user_id) values (#{userId})")
    void initPoint(String userId);

    @Update("UPDATE point SET point = #{point} WHERE user_id = #{userId}")
    void updatePoint(String userId, Integer point);
}
