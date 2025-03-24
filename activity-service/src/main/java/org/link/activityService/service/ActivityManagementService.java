package org.link.activityService.service;

import org.link.activityService.dto.ActivityApplicationDTO;
import org.link.activityService.dto.ActivityDTO;
import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.ParticipatorListDTO;

import java.util.List;

public interface ActivityManagementService {
    List<ActivityListDTO> findManagementActivities(String userId, String activityStatus);

    List<ParticipatorListDTO> findManagementParticipators(Integer activityId);

    List<ActivityApplicationDTO> findManagementApplications(Integer activityId, String participationStatus);


    void approveJoinApplication(Integer activityId, String applicantId);

    void disapproveJoinApplication(Integer activityId, String applicantId);

    void approveExitApplication(Integer activityId, String applicantId);

    void disApproveExitApplication(Integer activityId, String applicantId);

    String launchActivity(String userId, ActivityDTO activityDTO, List<String> pictures);

    String unLaunchActivity(String userId,Integer activityId);
}
