package org.link.activityService.mapper;

import org.apache.ibatis.annotations.*;
import org.link.activityService.entity.ParticipationRecord;

import java.util.List;

@Mapper
public interface ParticipationRecordMapper {

    @Select("select * from participation_record where activity_id = #{activityId} and participator_id = #{userId}")
    ParticipationRecord findParticipationByActivityUser(Integer activityId, String userId);

    @Select("select * from participation_record where activity_id = #{activityId}")
    List<ParticipationRecord> findParticipationByActivity(Integer activityId);

    @Select("select participator_id from participation_record where activity_id = #{activityId} and status != 'JOIN_REQ' ")
    List<String> findParticipatorsIdByActivity(Integer activityId);

    @Insert("insert into participation_record (participator_id , activity_id) values (#{userId} , #{activityId})")
    void addParticipationRecord(String userId, Integer activityId);

    @Select("select participator_id from participation_record where activity_id = #{activityId} and status = #{participationStatus} ")
    List<String> findManagementApplications(Integer activityId, String participationStatus);

    @Update("UPDATE participation_record SET status = 'JOINED' WHERE activity_id = #{activityId} AND participator_id = #{applicantId}")
    void approveJoinApplication(Integer activityId, String applicantId);


    @Select("select activity_id from participation_record where participator_id = #{userId} and status = #{participationStatus}")
    List<Integer> findParticipationByStatusUser(String userId, String participationStatus);

    @Update("UPDATE participation_record SET status = 'EXIT_REQ' WHERE activity_id = #{activityId} AND participator_id = #{userId}")
    void updateForExit(String userId, Integer activityId);

    @Delete("DELETE FROM participation_record WHERE activity_id = #{activityId} AND participator_id = #{applicantId}")
    void approveExitApplication(Integer activityId, String applicantId);

    @Update("UPDATE participation_record SET status = 'JOINED' WHERE activity_id = #{activityId} AND participator_id = #{applicantId}")
    void disapproveExitApplication(Integer activityId, String applicantId);

    @Delete("DELETE FROM participation_record WHERE activity_id = #{activityId}")
    void deleteParticipationRecordByActivity(Integer activityId);
}
