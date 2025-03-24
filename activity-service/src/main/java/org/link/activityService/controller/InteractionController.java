package org.link.activityService.controller;

import org.link.activityService.dto.ActivityDetailDTO;
import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.CommentDTO;
import org.link.activityService.dto.CreateCommentDTO;
import org.link.activityService.dto.ReportDTO;
import org.link.activityService.dto.*;
import org.link.activityService.service.InteractionService;
import org.link.activityService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;



    //获取活动评论
    @GetMapping("/comments/{activityId}")
    public Result getActivityComments(@RequestHeader String userId,@PathVariable(value = "activityId", required = true) Integer activityId){
        List<CommentDTO>commentDTOList = interactionService.getActivityComments(userId, activityId);
        return Result.success(commentDTOList);
    }

    //管理员查看被举报活动评论表
    @GetMapping("/adminView/comments/{activityId}")
    public Result getAdminViewActivityComments(@PathVariable Integer activityId){
        List<AdminViewCommentDTO>adminViewCommentDTOList = interactionService.getAdminViewActivityComments(activityId);
        return Result.success(adminViewCommentDTOList);
    }

    //点赞
    @PostMapping("/likes")
    public Result addLike(@RequestHeader String userId, @RequestBody Map<String, String> params){
        String activityId = params.get("activityId");
        Boolean isSuccessful = interactionService.addLike(userId, activityId);
        if (!isSuccessful)
        {
            return Result.error("点赞失败");
        }
        return Result.success();
    }

    //取消点赞
    @DeleteMapping("/likes/{activityId}")
    public Result deleteLike(@RequestHeader String userId, @PathVariable("activityId") Integer activityId){
        interactionService.deleteLike(userId, activityId);
        return Result.success();
    }

    //收藏
    @PostMapping("/favorite")
    public Result addFavorite(@RequestHeader String userId, @RequestBody Map<String, String> params){
        String activityId = params.get("activityId");
        Boolean isSuccessful = interactionService.addFavorite(userId, activityId);
        if (!isSuccessful)
        {
            return Result.error("收藏失败");
        }
        return Result.success();
    }

    //取消收藏
    @DeleteMapping("/favorite/{activityId}")
    public Result deleteFavorite(@RequestHeader String userId, @PathVariable("activityId") Integer activityId){
        interactionService.deleteFavorite(userId, activityId);
        return Result.success();
    }

    //添加评论
    @PostMapping("/comment")
    public Result createComment(
            @RequestHeader String userId,
            @RequestBody CreateCommentDTO createCommentDTO) {
        return Result.success(interactionService.createComment(userId, createCommentDTO));
    }

    //修改评论状态（删除）
    @PutMapping("/comment/{commentId}")
    public Result deleteComment(@PathVariable("commentId") Integer commentId) {
        interactionService.deleteComment(commentId);
        return Result.success();
    }

    //添加举报
    @PostMapping("/report")
    public Result createReport(
            @RequestHeader String userId,
            @RequestBody ReportDTO reportDTO) {
        interactionService.createReport(userId, reportDTO);
        return Result.success();
    }

    //获取未审核举报列表
    @GetMapping("/admin/report/pending")
    public Result getPendingReportList(){
        List<PendingReportListDTO> pendingReportList = interactionService.getPendingReportList();
        return Result.success(pendingReportList);
    }

    //根据举报id获取举报
    @GetMapping("/admin/report/{reportId}")
    public Result getReport(@PathVariable Integer reportId){
        PendingReportListDTO pendingReportListDTO = interactionService.getReport(reportId);
        return Result.success(pendingReportListDTO);
    }

    //更改举报状态（转为已审核）
    @PutMapping("/admin/report/verified/{reportId}")
    public Result updateReportStatus(@PathVariable Integer reportId){
        interactionService.updateReportStatus(reportId);
        return Result.success();
    }

    //查看收藏列表
    @GetMapping("/favorite")
    public Result findFavoriteActivities(@RequestHeader String userId){
        List<ActivityListDTO> activityListDTOList = interactionService.findFavoriteActivities(userId);
        return Result.success(activityListDTOList);
    }

    //活动锁定
    @PutMapping("/admin/lock/{activityId}")
    public Result lockActivity(@PathVariable Integer activityId){
        interactionService.lockActivity(activityId);
        return Result.success();
    }



}
