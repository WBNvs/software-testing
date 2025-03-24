package org.link.activityService.controller;

import org.link.activityService.dto.ActivityApplicationDTO;
import org.link.activityService.dto.ActivityDTO;
import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.ParticipatorListDTO;
import org.link.activityService.service.ActivityManagementService;
import org.link.activityService.utils.Result;
import org.link.activityService.utils.minio.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/activity/management")
public class ActivityManagementController {

    @Autowired
    private ActivityManagementService activityManagementService;
    @Autowired
    private MinioService minioService;


    //获取四种状态的自己所发起的活动
    @GetMapping("")
    public Result findManagementActivities(@RequestHeader String userId, @RequestParam("activityStatus") String activityStatus)
    {
        List<ActivityListDTO> activityListDTOList = activityManagementService.findManagementActivities(userId, activityStatus);
        return Result.success(activityListDTOList);
    }

    //获取自己所发起的某一活动的人员列表
    @GetMapping("/participators")
    public Result findManagementParticipators(@RequestParam("activityId") Integer activityId){
        List<ParticipatorListDTO> participatorListDTOList = activityManagementService.findManagementParticipators(activityId);
        return Result.success(participatorListDTOList);
    }

    //获取自己所发起的某一活动的申请加入或申请退出列表
    @GetMapping("/applications")
    public Result findManagementApplication(@RequestParam("activityId") Integer activityId, @RequestParam("participationStatus") String participationStatus){
        List<ActivityApplicationDTO> activityApplicationDTOList = activityManagementService.findManagementApplications(activityId, participationStatus);
        return Result.success(activityApplicationDTOList);
    }

    //批准用户加入活动
    @PutMapping("/applicationOfJoin/{activityId}/{applicantId}")
    public Result approveJoinApplication(@PathVariable("activityId") Integer activityId, @PathVariable("applicantId") String applicantId){
        activityManagementService.approveJoinApplication(activityId, applicantId);
        return Result.success();
    }

    //拒绝用户加入活动
    @DeleteMapping("/applicationOfJoin/{activityId}/{applicantId}")
    public Result disApproveJoinApplication(@PathVariable("activityId") Integer activityId, @PathVariable("applicantId") String applicantId){
        activityManagementService.disapproveJoinApplication(activityId, applicantId);
        return Result.success();
    }

    //同意用户申请退出活动
    @DeleteMapping("/applicationOfExit/{activityId}/{applicantId}")
    public Result approveExitApplication(@PathVariable("activityId") Integer activityId,@PathVariable("applicantId") String applicantId){
        activityManagementService.approveExitApplication(activityId, applicantId);
        return Result.success();
    }

    //拒绝用户申请退出活动
    @PutMapping("/applicationOfExit/{activityId}/{applicantId}")
    public Result disapproveExitApplication(@PathVariable("activityId") Integer activityId,@PathVariable("applicantId") String applicantId){
        activityManagementService.disApproveExitApplication(activityId, applicantId);
        return Result.success();
    }

    //发起活动
    @PostMapping("")
    public Result launchActivity(@RequestHeader String userId, @RequestHeader String authStatus, @RequestPart ActivityDTO activityDTO, @RequestPart("activityPicture")List<MultipartFile>  activityPictures){
        if (!authStatus.equals("VERIFIED")) {
            return Result.error("请先进行学生认证");
        }

        List<String> pictures = new ArrayList<>();
        for (MultipartFile activityPicture : activityPictures) {
            try {
                // 确保文件不为空并且文件名有效
                if (activityPicture != null && !activityPicture.isEmpty() && activityPicture.getOriginalFilename() != null && !activityPicture.getOriginalFilename().trim().isEmpty()) {
                    pictures.add(minioService.uploadFile(activityPicture.getOriginalFilename(), activityPicture));
                } else {
                    System.out.println("上传的文件为空，跳过该文件。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 启动活动
        String reason = activityManagementService.launchActivity(userId, activityDTO, pictures);
        if (reason != null) {
            return Result.error(reason);
        }
        return Result.success();
    }

    //删除发布的活动
    @DeleteMapping("/{activityId}")
    public Result unLuanchActivity(@RequestHeader String userId,@PathVariable Integer activityId){
        String reason = activityManagementService.unLaunchActivity(userId,activityId);
        if (reason != null){
            return Result.error(reason);
        }
        return Result.success();
    }

}
