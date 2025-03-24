package org.link.feign.clients;

import org.link.feign.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/user/participators")
    List<UserModel> getParticipators(@RequestParam("participatorIdList") List<String> participatorIdList);

    @GetMapping("/user/commentUsersAvatar")
    List<String> getCommentUsersAvatar(@RequestParam("commentUsers") List<String> commentUsers);

    //查询单个用户对应的userModel
    @GetMapping("/user/userInfo/userModel")
    UserModel getUserModel(@RequestHeader String userId);
}
