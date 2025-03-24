package org.link.userService.mapper;


import org.apache.ibatis.annotations.*;
import org.link.userService.entity.User;
import org.link.userService.dto.UserInfoDTO;

@Mapper
public interface UserMapper {

    @Select("select * from user where user_id = #{openid}")
    User findUser(String openid);

    @Insert("insert into user (user_id) values (#{openid})")
    void addUser(String openid);

    @Update("UPDATE user SET user_name=#{dto.userName}, avatar_url=#{dto.avatarUrl}, contact_info=#{dto.contactInfo}, is_ban=#{dto.isBan} WHERE user_id=#{userId}")
    int updateUser(@Param("userId") String userId, @Param("dto") UserInfoDTO dto);

    //封禁用户
    @Update("UPDATE user SET  is_ban= 1 WHERE user_id=#{userId}")
    void lockUser(@Param("userId") String userId);

}
