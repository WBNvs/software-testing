package org.link.pointService.dto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PointTransactionDTO {
    private Integer transactionId; // 变动记录唯一标识符
    private Integer points;           // 积分变动值
    private Timestamp time;          // 变动时间
}
