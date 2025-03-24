package org.link.activityService.controller;


import org.link.activityService.dto.*;
import org.link.activityService.dto.ActivityDetailDTO;
import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.service.BrowseService;
import org.link.activityService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class BrowseController {

    @Autowired
    private BrowseService browseService;

    //主页获取活动列表
    @GetMapping("")
    public Result getAllActivities(@RequestHeader String userId, @RequestParam("category") String category){
        List<ActivityListDTO> activityListDTOList = browseService.getActivitiesList(userId, category);
        return Result.success(activityListDTOList);
    }

    //获取活动详情信息
    @GetMapping("/{activityId}")
    public Result getActivityDetail(@RequestHeader String userId,@PathVariable(value = "activityId",required = true) Integer activityId){
        ActivityDetailDTO activityDetailDTO = browseService.getActivityDetail(userId, activityId);
        if (activityDetailDTO == null){
            return Result.error("活动不存在");
        }
        return Result.success(activityDetailDTO);
    }


    //管理员查看被举报活动详情页
    @GetMapping("/adminView/{activityId}")
    public Result getAdminViewActivityDetail(@PathVariable Integer activityId){
        AdminViewActivityDetailDTO adminViewactivityDetailDTO = browseService.getAdminViewActivityDetail(activityId);
        if (adminViewactivityDetailDTO == null){
            return Result.error("活动不存在");
        }
        return Result.success(adminViewactivityDetailDTO);
    }


}
