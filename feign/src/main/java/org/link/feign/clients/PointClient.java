package org.link.feign.clients;


import org.link.feign.model.NotificationModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("point-service")
public interface PointClient {
    @PostMapping("/point/init")
    Integer initPoint(@RequestParam("userId") String userId);

    //更改积分&增加积分变动记录
    @PostMapping("/point/change")
    Integer updatePoint(@RequestHeader String userId, @RequestParam("point") Integer point);

}
