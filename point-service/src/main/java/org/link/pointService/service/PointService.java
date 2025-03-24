package org.link.pointService.service;


import org.link.pointService.dto.PointTransactionDTO;
import org.link.pointService.dto.PointDTO;

import java.util.List;

public interface PointService {
    Integer initPoint(String userId);

    Integer updatePointsAndTransaction(String userId, Integer changeAmount);

    List<PointTransactionDTO> getAllAPointTransaction(String userId);

    PointDTO getUserPoint(String userId);
}
