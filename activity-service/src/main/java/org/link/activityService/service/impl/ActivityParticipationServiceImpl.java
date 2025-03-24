package org.link.activityService.service.impl;

import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.ParticipatorListDTO;
import org.link.activityService.entity.Activity;
import org.link.activityService.entity.Likes;
import org.link.activityService.entity.ParticipationRecord;
import org.link.activityService.mapper.ActivityMapper;
import org.link.activityService.mapper.LikesMapper;
import org.link.activityService.mapper.ParticipationRecordMapper;
import org.link.activityService.service.ActivityParticipationService;
import org.link.feign.clients.NotificationClient;
import org.link.feign.clients.PointClient;
import org.link.feign.clients.UserClient;
import org.link.feign.model.NotificationModel;
import org.link.feign.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityParticipationServiceImpl implements ActivityParticipationService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private PointClient pointClient;
    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ParticipationRecordMapper participationRecordMapper;
    @Autowired
    private LikesMapper likesMapper;

    @Override
    public String addParticipation(String userId, Integer activityId) {
        //查看活动状态、人员等信息
        Activity activity = activityMapper.findActivityDetail(activityId);
        if (activity.getStatus().equals("OVER")||activity.getStatus().equals("ABNORMAL")){
            return "活动已结束";
        } else if (activity.getStatus().equals("LOCKED")) {
            return "活动已被锁定";
        }
        if (activity.getOrganizerId().equals(userId)){
            return "无法申请加入自己发起的活动";
        }
        if (activity.getCurrentNum() >= activity.getMaxNum()){
            return "活动人员已满";
        }
        ParticipationRecord participationRecord = participationRecordMapper.findParticipationByActivityUser(activityId, userId);
        if (participationRecord != null){
            return "请勿重复申请加入某活动";
        }
        NotificationModel notificationModel = new NotificationModel(activity.getOrganizerId(), "Manage");
        notificationModel.setContent("您所发布的名为" + activity.getActivityName() + "的活动有新的人员申请加入");
        notificationClient.createNotification(notificationModel);
        pointClient.updatePoint(userId,3);
        participationRecordMapper.addParticipationRecord(userId, activityId);
        return null;
    }

    @Override
    public List<ActivityListDTO> findParticipation(String userId, String participationStatus) {
        List<ActivityListDTO> activityListDTOList = new ArrayList<>();
        List<Integer> activityIdList = participationRecordMapper.findParticipationByStatusUser(userId, participationStatus);
        for (Integer activityId : activityIdList){
            Activity activity = activityMapper.findActivityByActivityId(activityId);
            Likes like = likesMapper.findLike(activity.getActivityId(), userId);
            activityListDTOList.add(new ActivityListDTO(activity, like));
        }
        return activityListDTOList;
    }

    @Override
    public String applyForExit(String userId, Integer activityId) {
        if (pointClient.updatePoint(userId,-3) == -1){
            return "积分不足，无法申请退出活动";
        }
        Activity activity = activityMapper.findActivityByActivityId(activityId);
        NotificationModel notificationModel = new NotificationModel(userId, "Manage");
        notificationModel.setContent("您所发布的名为" + activity.getActivityName() + "的活动有人员申请退出");
        notificationClient.createNotification(notificationModel);
        participationRecordMapper.updateForExit(userId, activityId);
        return null;
    }

    @Override
    public List<ParticipatorListDTO> findParticipationParticipators(Integer activityId) {
        List<ParticipatorListDTO> participatorListDTOList = new ArrayList<>();
        List<String> participatorIdList = participationRecordMapper.findParticipatorsIdByActivity(activityId);
        //查找发起人id
        Activity activity = activityMapper.findActivityByActivityId(activityId);

        participatorIdList.add(0, activity.getOrganizerId());

        List<UserModel> userModelList = userClient.getParticipators(participatorIdList);
        for (int i = 0; i < userModelList.size(); i++) {
            UserModel userModel = userModelList.get(i);
            if (i != 0)
            {
                userModel.setContactInfo("**********");
            }
            participatorListDTOList.add(new ParticipatorListDTO(userModel));
        }
        return participatorListDTOList;

    }
}
