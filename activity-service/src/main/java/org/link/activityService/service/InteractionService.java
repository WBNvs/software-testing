package org.link.activityService.service;

import org.link.activityService.dto.*;
import org.link.activityService.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InteractionService {

    //用户视角获取活动评论
    List<CommentDTO> getActivityComments(String userId, Integer activityId);

    //管理员视角获取被举报活动评论
    List<AdminViewCommentDTO> getAdminViewActivityComments(Integer activityId);

    Boolean addLike(String userId, String activityId);

    void deleteLike(String userId, Integer activityId);

    Boolean addFavorite(String userId, String activityId);

    void deleteFavorite(String userId, Integer activityId);

    //创建评论
    CommentDTO createComment(String userId,CreateCommentDTO createCommentDTO);

    //删除评论（修改评论状态）
    void deleteComment(Integer commentId);

    //创建举报
    void createReport(String userId, ReportDTO reportDTO);

    //获取未审核举报列表
    List<PendingReportListDTO> getPendingReportList();

    //根据举报id获取举报
    PendingReportListDTO getReport(Integer reportId);

    //更新举报信息（改为已审核）
    void updateReportStatus(Integer reportId);

    List<ActivityListDTO> findFavoriteActivities(String userId);

    //活动锁定
    void lockActivity(Integer activityId);
}
