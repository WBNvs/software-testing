package org.link.activityService.service.impl;

import org.link.activityService.dto.*;
import org.link.activityService.entity.*;
import org.link.activityService.mapper.*;
import org.link.activityService.service.InteractionService;
import org.link.feign.clients.NotificationClient;
import org.link.feign.clients.UserClient;
import org.link.feign.model.NotificationModel;
import org.link.feign.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private LikesMapper likesMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private ParticipationRecordMapper participationRecordMapper;
    @Autowired
    private ActivityImageMapper activityImageMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReportMapper reportMapper;



    @Override
    public List<CommentDTO> getActivityComments(String userId, Integer activityId) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        List<String> commentUsers = new ArrayList<>();
        List<Comment> commentList = commentMapper.findActivityComments(activityId);
        if (commentList.size() != 0){
            //找到每个评论用户的头像和昵称
            for (Comment comment : commentList){
                commentUsers.add(comment.getUserId());
            }
            List<UserModel> userModelList = userClient.getParticipators(commentUsers);

            for (int i = 0; i<commentList.size(); i++){
                commentDTOList.add(new CommentDTO(userId, commentList.get(i), userModelList.get(i)));
            }
        }
        return commentDTOList;
    }

    //管理员视角获取被举报活动评论
    @Override
    public List<AdminViewCommentDTO> getAdminViewActivityComments(Integer activityId){
        List<AdminViewCommentDTO> adminViewCommentDTOList = new ArrayList<>();
        List<String> commentUsers = new ArrayList<>();
        List<Comment> commentList = commentMapper.findActivityComments(activityId);
        if (commentList.size() != 0){
            //找到每个评论用户的头像和昵称
            for (Comment comment : commentList){
                commentUsers.add(comment.getUserId());
            }
            List<UserModel> userModelList = userClient.getParticipators(commentUsers);

            for (int i = 0; i<commentList.size(); i++){
                adminViewCommentDTOList.add(new AdminViewCommentDTO(commentList.get(i), userModelList.get(i)));
            }
        }
        return adminViewCommentDTOList;
    }

    @Override
    public Boolean addLike(String userId, String activityId) {
        Likes likes = likesMapper.findLike(Integer.parseInt(activityId), userId);
        if (likes != null){
            return false;
        }
        likesMapper.addLike(Integer.parseInt(activityId), userId);
        activityMapper.addActivityLike(Integer.parseInt(activityId));
        return true;
    }

    @Override
    public void deleteLike(String userId, Integer activityId) {
        Integer i = likesMapper.deleteLike(activityId, userId);
        if (i != 0){
            activityMapper.deleteActivityLike(activityId);
        }
        return;
    }

    @Override
    public Boolean addFavorite(String userId, String activityId) {
        Favorite favorite = favoriteMapper.findFavorite(Integer.parseInt(activityId), userId);
        if (favorite != null){
            return false;
        }
        favoriteMapper.addFavorite(Integer.parseInt(activityId), userId);
        activityMapper.addActivityFavorite(Integer.parseInt(activityId));
        return true;
    }

    @Override
    public void deleteFavorite(String userId, Integer activityId) {
        Integer i = favoriteMapper.deleteFavorite(activityId, userId);
        if (i != 0)
        {
            activityMapper.deleteActivityFavorite(activityId);
        }
        return;
    }

    //创建评论
    @Override
    public CommentDTO createComment(String userId, CreateCommentDTO createCommentDTO){
        Comment insertComment = new Comment();
        insertComment.setActivityId(createCommentDTO.getActivityId());
        insertComment.setContent(createCommentDTO.getContent());
        insertComment.setParentFloor(createCommentDTO.getParentFloor());
        insertComment.setUserId(userId);

        //floor需要根据当前活动id查询到的最大floor值+1来计算
        Integer commentCount=commentMapper.getCommentCountByActivityId(createCommentDTO.getActivityId());
        insertComment.setFloor(commentCount+1);

        //插入评论
        commentMapper.addComment(insertComment);
        System.out.println(createCommentDTO);
        System.out.println(insertComment);


        //根据用户id获取userModel
        UserModel userModel = userClient.getUserModel(userId);
        System.out.println(userModel);

        //根据活动id和楼层获取评论
        Comment comment = commentMapper.findCertainComment(insertComment.getActivityId(),insertComment.getFloor());

        //拼接commentDTO并返回
        System.out.println(comment);
        CommentDTO commentDTO= new CommentDTO(userId,comment,userModel);

        //给发起人发通知
        Activity activity = activityMapper.findActivityByActivityId(createCommentDTO.getActivityId());
        NotificationModel notificationModel = new NotificationModel(activity.getOrganizerId(), "Manage");
        notificationModel.setContent("您所发布的名为" + activity.getActivityName() + "的活动有新的评论信息");
        notificationClient.createNotification(notificationModel);

        return commentDTO;
    }

    //删除评论（修改评论状态）
    @Override
    public void deleteComment(Integer commentId){
        commentMapper.deleteComment(commentId);
    }

    //创建举报
    @Override
    public void createReport(String userId, ReportDTO reportDTO){
        reportMapper.addReport(userId,reportDTO);
    }


    //获取未审核举报列表
    @Override
    public List<PendingReportListDTO> getPendingReportList(){
        List<PendingReportListDTO> pendingReportList = new ArrayList<>();
        List<Report> reportList =  reportMapper.findPendingReport();
        for (Report report : reportList){
            pendingReportList.add(new PendingReportListDTO(report));
        }
        return pendingReportList;
    }

    //根据举报id获取举报
    @Override
    public PendingReportListDTO getReport(Integer reportId){
        Report report = reportMapper.findReportById(reportId);
        return (new PendingReportListDTO(report));
    }

    //更新举报信息（改为已审核）
    @Override
    public void updateReportStatus(Integer reportId){
        reportMapper.updateReportStatus(reportId);
    }


    //查看所收藏的活动
    @Override
    public List<ActivityListDTO> findFavoriteActivities(String userId) {
        List<ActivityListDTO> activityListDTOList = new ArrayList<>();
        List<Integer> activityIdList = favoriteMapper.findActivityIdByUserId(userId);
        for (Integer activityId : activityIdList){
            Activity activity = activityMapper.findActivityByActivityId(activityId);
            Likes likes = likesMapper.findLike(activityId, userId);
            activityListDTOList.add(new ActivityListDTO(activity, likes));
        }
        return activityListDTOList;
    }


    //活动锁定
    @Override
    public void lockActivity(Integer activityId){
        activityMapper.lockActivity(activityId);
        //向活动发起人发送通知

    }

}
