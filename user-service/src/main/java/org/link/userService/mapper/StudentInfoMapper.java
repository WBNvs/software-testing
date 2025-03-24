package org.link.userService.mapper;

import org.apache.ibatis.annotations.*;
import org.link.userService.entity.StudentInfo;
import org.link.userService.dto.StudentInfoDTO;

import java.util.List;

@Mapper
public interface StudentInfoMapper {

    @Select("select * from student_info where user_id = #{openid}")
    StudentInfo findStudentInfo(String openid);

    @Insert("insert into student_info (user_id) values (#{openid})")
    void addStudentInfo(String openid);

    @Update("UPDATE student_info SET name=#{dto.name}, college=#{dto.college}, major=#{dto.major}, student_id=#{dto.studentId} WHERE user_id=#{userId}")
    int updateStudentInfo(@Param("userId") String userId, @Param("dto") StudentInfoDTO dto);

    @Select("SELECT user_id from student_info where auth_status = #{status}")
    List<String> findStudentInfoByStatus(String status);

    @Update("update student_info set auth_status=#{status} where user_id=#{userId}")
    void handleAuth(String userId, String status);

//    @Insert("insert into student_info (user_id) values (#{openid})")
//    StudentInfo addStudentInfo(String openid);
}
