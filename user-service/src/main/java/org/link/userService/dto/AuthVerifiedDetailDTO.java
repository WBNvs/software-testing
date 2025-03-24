package org.link.userService.dto;

import lombok.Data;
import org.link.userService.entity.StudentInfo;
import org.link.userService.entity.User;

@Data
public class AuthVerifiedDetailDTO {
    private String userId;
    private String userName;
    private String avatarUrl;
    private String contactInfo;
    private String name;
    private String college;
    private String major;
    private String studentId;
    private String authStatus;

    public AuthVerifiedDetailDTO(User user, StudentInfo studentInfo){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.avatarUrl = user.getAvatarUrl();
        this.contactInfo = user.getContactInfo();
        this.name = studentInfo.getName();
        this.college = studentInfo.getCollege();
        this.major = studentInfo.getMajor();
        this.studentId = studentInfo.getStudentId();
        this.authStatus = studentInfo.getAuthStatus();
    }
}
