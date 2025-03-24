package org.link.userService.service;

import org.link.userService.dto.AuthVerifiedDetailDTO;
import org.link.userService.dto.AuthVerifiedListDTO;
import org.link.userService.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface AdminService {
    List<AuthVerifiedListDTO> getAuthList();

    List<AuthVerifiedListDTO> getAuthedList();

    AuthVerifiedDetailDTO getAuthDetail(String userId);

    void handleAuth(String userId, String status);

    //封禁用户
    void lockUser(String userId);
}
