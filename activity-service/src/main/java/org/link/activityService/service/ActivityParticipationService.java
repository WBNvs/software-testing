package org.link.activityService.service;

import org.link.activityService.dto.ActivityListDTO;
import org.link.activityService.dto.ParticipatorListDTO;

import java.util.List;

public interface ActivityParticipationService {

    String addParticipation(String userId, Integer activityId);

    List<ActivityListDTO> findParticipation(String userId, String participationStatus);

    String applyForExit(String userId, Integer activityId);

    List<ParticipatorListDTO> findParticipationParticipators(Integer activityId);
}
