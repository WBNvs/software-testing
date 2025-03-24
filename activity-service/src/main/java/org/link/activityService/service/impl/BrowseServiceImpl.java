package org.link.activityService.service.impl;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.link.activityService.dto.ActivityDetailDTO;
import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.AdminViewActivityDetailDTO;
import org.link.activityService.entity.*;
import org.link.activityService.mapper.*;
import org.link.activityService.service.BrowseService;
import org.link.feign.clients.UserClient;
import org.link.feign.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrowseServiceImpl implements BrowseService {
    @Autowired
    private UserClient userClient;

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

    @Override
    public List<ActivityListDTO> getActivitiesList(String userId, String category) {
        List<ActivityListDTO> activityListDTOList = new ArrayList<>();
        List<Activity> activityList = new ArrayList<>();
        if (category.equals("All")){
            activityList = activityMapper.findAllActivity();

        }else {
            activityList = activityMapper.findCategoryActivity(category);
        }
        for (Activity activity : activityList){
            Likes like = likesMapper.findLike(activity.getActivityId(), userId);
            activityListDTOList.add(new ActivityListDTO(activity, like));
        }
        return activityListDTOList;
    }

    //用户视角获取活动详情信息
    @Override
    public ActivityDetailDTO getActivityDetail(String userId, Integer activityId) {
        Activity activity = activityMapper.findActivityDetail(activityId);
        if (activity == null){
            return null;
        }
        Favorite favorite = favoriteMapper.findFavorite(activityId, userId);
        Likes likes = likesMapper.findLike(activityId, userId);
        ParticipationRecord participationRecord = participationRecordMapper.findParticipationByActivityUser(activityId, userId);
        List<ActivityImage> activityImageList = activityImageMapper.findActivityImages(activityId);

        //获取参与者与发起者头像
        List<String> participatorIdList = participationRecordMapper.findParticipatorsIdByActivity(activityId);
        participatorIdList.add(activity.getOrganizerId());
        List<UserModel> userModelList = userClient.getParticipators(participatorIdList);

        ActivityDetailDTO activityDetailDTO = new ActivityDetailDTO(activity,favorite,likes,participationRecord,activityImageList,userModelList);

        return activityDetailDTO;
    }

    //管理员视角查看活动详情页面
    @Override
    public AdminViewActivityDetailDTO getAdminViewActivityDetail(Integer activityId){
        Activity activity = activityMapper.findActivityDetail(activityId);
        if (activity == null){
            return null;
        }
        List<ActivityImage> activityImageList = activityImageMapper.findActivityImages(activityId);

        //获取参与者与发起者头像
        List<String> participatorIdList = participationRecordMapper.findParticipatorsIdByActivity(activityId);
        participatorIdList.add(activity.getOrganizerId());
        List<UserModel> userModelList = userClient.getParticipators(participatorIdList);

        AdminViewActivityDetailDTO adminViewActivityDetailDTO = new AdminViewActivityDetailDTO(activity,activityImageList,userModelList);

        return adminViewActivityDetailDTO;
    }
}
