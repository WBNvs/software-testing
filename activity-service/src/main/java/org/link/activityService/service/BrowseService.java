package org.link.activityService.service;

import org.link.activityService.dto.ActivityDetailDTO;
import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.AdminViewActivityDetailDTO;

import java.util.List;

public interface BrowseService {

    List<ActivityListDTO> getActivitiesList(String userId, String category);

    ActivityDetailDTO getActivityDetail(String userId, Integer activityId);

    //管理员查看活动详情页面
    AdminViewActivityDetailDTO getAdminViewActivityDetail(Integer activityId);
}
