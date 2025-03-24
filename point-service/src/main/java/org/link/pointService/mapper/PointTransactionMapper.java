package org.link.pointService.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.link.pointService.entity.PointTransaction;

import java.util.List;

@Mapper
public interface PointTransactionMapper {
    @Select("select * from point_transaction where user_id = #{userId}")
    List<PointTransaction> findAllTransaction(String userId);

    @Insert("INSERT INTO point_transaction (user_id, points, time) VALUES (#{userId}, #{points}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "transactionId")
    void insertTransaction(PointTransaction pointTransaction);
}
