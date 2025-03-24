package org.link.activityService.mapper;

import org.apache.ibatis.annotations.*;

import org.link.activityService.dto.ReportDTO;
import org.link.activityService.entity.Report;

import java.util.List;

@Mapper
public interface ReportMapper {
    //插入举报
    @Insert("insert into report (user_id , activity_id,reason) values (#{userId} , #{dto.activityId}, #{dto.reason})")
    void addReport(String userId,@Param("dto") ReportDTO reportDTO);

    //查询状态为未审核的举报
    @Select("select * from report where status = 'PENDING'")
    List<Report> findPendingReport();

    //根据举报id查询举报
    @Select("select * from report where report_id=#{reportId} ")
    Report findReportById(Integer reportId);

    //更新举报状态为已审核
    @Update("update report set status='RESOLVED'where report_id=#{reportId}")
    void updateReportStatus(Integer reportId);
}
