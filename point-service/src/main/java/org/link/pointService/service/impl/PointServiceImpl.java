package org.link.pointService.service.impl;

import org.link.pointService.dto.*;
import org.link.pointService.entity.*;
import org.link.pointService.mapper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointServiceImpl implements org.link.pointService.service.PointService {
    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private PointTransactionMapper pointTransactionMapper;

    @Override
    public Integer initPoint(String userId) {
        Point point = pointMapper.findPoint(userId);
        if (point == null)
        {
            pointMapper.initPoint(userId);
            point = new Point();
            point.setPoint(0);
        }
        return point.getPoint();
    }

    @Override
    public Integer updatePointsAndTransaction(String userId, Integer changeAmount) {
        // 查找用户积分
        Point point = pointMapper.findPoint(userId);
        if (point == null) {
            throw new RuntimeException("不存在该用户");
        }


        Integer updatedPoints = point.getPoint() + changeAmount;

        if(updatedPoints<0){
            //如果更新后的积分数量小于零，说明不支持此次积分更新，返回-1
            return -1;
        }
        else{
            // 更新用户积分
            pointMapper.updatePoint(userId, updatedPoints);

            // 创建积分变动记录
            PointTransaction transaction = new PointTransaction();
            transaction.setUserId(userId);
            transaction.setPoints(changeAmount);
            transaction.setTime(Timestamp.valueOf(LocalDateTime.now()));

            // 插入积分变动记录
            pointTransactionMapper.insertTransaction(transaction);

            //返回变动后的积分值
            return updatedPoints;
        }
    }


    @Override
    public List<PointTransactionDTO> getAllAPointTransaction(String userId){

        List<PointTransactionDTO> pointTransactionDTOList = new ArrayList<>();
        List<PointTransaction> pointTransactionList = new ArrayList<>();

        pointTransactionList = pointTransactionMapper.findAllTransaction(userId);
        for (PointTransaction pointTransaction : pointTransactionList){
            PointTransactionDTO pointTransactionDTO = new PointTransactionDTO();

            pointTransactionDTO.setPoints(pointTransaction.getPoints());
            pointTransactionDTO.setTransactionId(pointTransaction.getTransactionId());
            pointTransactionDTO.setTime(pointTransaction.getTime());

            pointTransactionDTOList.add(pointTransactionDTO);
        }
        return pointTransactionDTOList;
    }

    @Override
    public PointDTO getUserPoint(String userId){
        Point point = pointMapper.findPoint(userId);
        PointDTO pointDTO = new PointDTO();
        pointDTO.setPoint(point.getPoint());

        return pointDTO;
    }
}
