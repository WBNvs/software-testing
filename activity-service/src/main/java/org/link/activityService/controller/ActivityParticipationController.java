package org.link.activityService.controller;

import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.ParticipatorListDTO;
import org.link.activityService.service.ActivityParticipationService;
import org.link.activityService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/activity/participation")
public class ActivityParticipationController {

    @Autowired
    private ActivityParticipationService activityParticipationService;

    //申请加入活动
    @PostMapping("")
    public Result addParticipation(@RequestHeader String userId, @RequestHeader String authStatus, @RequestBody Map<String,String> Params){
        if (!authStatus.equals("VERIFIED")){
            return Result.error("请先进行学生认证");
        }
        String reason = activityParticipationService.addParticipation(userId, Integer.parseInt(Params.get("activityId")));
        if (reason != null){
            return Result.error(reason);
        }
        return Result.success();
    }

    //查看三种类型的活动列表（已加入、申请加入中、申请退出中）
    @GetMapping("")
    public Result findParticipation(@RequestHeader String userId, @RequestParam("participationStatus") String participationStatus){
        List<ActivityListDTO> activityListDTOList = activityParticipationService.findParticipation(userId, participationStatus);
        return Result.success(activityListDTOList);
    }

    //申请退出活动
    @PutMapping("/{activityId}")
    public Result applyForExit(@RequestHeader String userId, @PathVariable("activityId") Integer activityId){
        String reason = activityParticipationService.applyForExit(userId, activityId);
        if (reason != null){
            return Result.error(reason);
        }
        return Result.success();
    }

    //获取包括发起者在内的所有人员列表
    @GetMapping("/participators")
    public Result findParticipationParticipators(@RequestParam("activityId") Integer activityId){
        List<ParticipatorListDTO> participatorListDTOList = activityParticipationService.findParticipationParticipators(activityId);
        return Result.success(participatorListDTOList);
    }
}
