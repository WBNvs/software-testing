package org.link.activityService.service.impl;

import org.link.activityService.dto.*;
import org.link.activityService.entity.Activity;
import org.link.activityService.entity.Likes;
import org.link.activityService.mapper.*;
import org.link.activityService.service.ActivityManagementService;
import org.link.feign.clients.NotificationClient;
import org.link.feign.clients.PointClient;
import org.link.feign.clients.UserClient;
import org.link.feign.model.NotificationModel;
import org.link.feign.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;


import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityManagementServiceImpl implements ActivityManagementService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private PointClient pointClient;
    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private LikesMapper likesMapper;
    @Autowired
    private ParticipationRecordMapper participationRecordMapper;
    @Autowired
    private ActivityImageMapper activityImageMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public List<ActivityListDTO> findManagementActivities(String userId, String activityStatus) {
        List<ActivityListDTO> activityListDTOList = new ArrayList<>();
        List<Activity> activityList = activityMapper.findActivitiesByUserStatus(userId, activityStatus);
        for (Activity activity : activityList){
            Likes like = likesMapper.findLike(activity.getActivityId(), userId);
            activityListDTOList.add(new ActivityListDTO(activity, like));
        }
        return activityListDTOList;
    }

    @Override
    public List<ParticipatorListDTO> findManagementParticipators(Integer activityId) {
        List<ParticipatorListDTO> participatorListDTOList = new ArrayList<>();
        List<String> participatorIdList = participationRecordMapper.findParticipatorsIdByActivity(activityId);
        List<UserModel> userModelList = userClient.getParticipators(participatorIdList);
        for (UserModel userModel : userModelList){
            participatorListDTOList.add(new ParticipatorListDTO(userModel));
        }
        return participatorListDTOList;
    }

    @Override
    public List<ActivityApplicationDTO> findManagementApplications(Integer activityId, String participationStatus) {
        List<ActivityApplicationDTO> activityApplicationDTOList = new ArrayList<>();
        List<String> participatorIdList = participationRecordMapper.findManagementApplications(activityId, participationStatus);
        List<UserModel> userModelList = userClient.getParticipators(participatorIdList);
        for (UserModel userModel : userModelList){
            activityApplicationDTOList.add(new ActivityApplicationDTO(userModel));
        }
        return activityApplicationDTOList;
    }

    @Override
    public void approveJoinApplication(Integer activityId, String applicantId) {
        participationRecordMapper.approveJoinApplication(activityId, applicantId);
        activityMapper.addParticipatorNum(activityId);
        Activity activity = activityMapper.findActivityByActivityId(activityId);
        NotificationModel notificationModel = new NotificationModel(applicantId, "Participate");
        notificationModel.setContent("您所申请的名为" + activity.getActivityName() + "的活动已同意您的申请加入");
        notificationClient.createNotification(notificationModel);
        return;
    }

    @Override
    public void disapproveJoinApplication(Integer activityId, String applicantId) {
        Activity activity = activityMapper.findActivityByActivityId(activityId);
        NotificationModel notificationModel = new NotificationModel(applicantId, "Participate");
        notificationModel.setContent("您所申请的名为" + activity.getActivityName() + "的活动拒绝您的申请加入");
        notificationClient.createNotification(notificationModel);
        return;
    }

    @Override
    public void approveExitApplication(Integer activityId, String applicantId) {
        participationRecordMapper.approveExitApplication(activityId, applicantId);
        activityMapper.deleteParticipatorNum(activityId);
        Activity activity = activityMapper.findActivityByActivityId(activityId);
        NotificationModel notificationModel = new NotificationModel(applicantId, "Participate");
        notificationModel.setContent("您所申请退出的名为" + activity.getActivityName() + "的活动同意您的申请退出");
        notificationClient.createNotification(notificationModel);
        return;
    }

    @Override
    public void disApproveExitApplication(Integer activityId, String applicantId) {
        participationRecordMapper.disapproveExitApplication(activityId, applicantId);
        Activity activity = activityMapper.findActivityByActivityId(activityId);
        NotificationModel notificationModel = new NotificationModel(applicantId, "Participate");
        notificationModel.setContent("您所申请退出的名为" + activity.getActivityName() + "的活动拒绝您的申请退出");
        notificationClient.createNotification(notificationModel);
        return;
    }

    @Override
    public String launchActivity(String userId, ActivityDTO activityDTO, List<String> pictures) {
        activityDTO.setOrganizerId(userId);
        if (activityDTO.getActivityName().isEmpty()){
            return "活动标题不能为空";
        }
        if (activityDTO.getMaxNum() < 1){
            return "活动人数至少为1人";
        }
        if (activityDTO.getStartTime().before(new Timestamp(System.currentTimeMillis()))){
            return "发起时间必须早于当前时间";
        }
        activityMapper.addActivity(activityDTO);
        pointClient.updatePoint(userId,5);
//        System.out.println(activityDTO.getActivityId());
        for (String picture : pictures){
            activityImageMapper.addActivityImage(activityDTO.getActivityId(), picture);
        }
        return null;
    }

    @Override
    public String unLaunchActivity(String userId,Integer activityId) {
        if (pointClient.updatePoint(userId,-5) == -1){
            return "积分不足，无法删除活动";
        }
        Activity activity = activityMapper.findActivityByActivityId(activityId);
        List<String> participators = participationRecordMapper.findParticipatorsIdByActivity(activityId);
        for (String participator : participators){
            NotificationModel notificationModel = new NotificationModel(participator, "Participate");
            notificationModel.setContent("您所参与的名为" + activity.getActivityName() + "的活动已取消，如有异议，请联系活动的发起人");
            notificationClient.createNotification(notificationModel);
        }
        activityMapper.deleteActivity(activityId);
        activityImageMapper.deleteActivityImage(activityId);
        commentMapper.deleteCommentByActivity(activityId);
        favoriteMapper.deleteFavoriteByActivity(activityId);
        likesMapper.deleteLikeByActivity(activityId);
        participationRecordMapper.deleteParticipationRecordByActivity(activityId);
        return null;
    }
}
