package com.yangzhuo.redpacket.dao;

import com.yangzhuo.redpacket.domain.RedPacket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


/**
 * @author yangzhuo
 */

@Mapper
@Repository

public interface AccountDao {
    String TABLE_NAME = " account ";
    String SELECT_FIELDS = " id , user_id, amount, create_time, update_time";

    /**
     * 获取余额
     * @param userId 红包编号
     * @return 红包的具体信息
     */
    @Select({" select ",SELECT_FIELDS," from ",TABLE_NAME," where id = #{userId}"})
    RedPacket getAccount(long userId);

    /**
     * 扣钱
     * @param userId 用户编号,account 金额
     * @return 更新记录数
     */
    @Update({" update ",TABLE_NAME," set amount = amount-#{account} where user_id = #{userId} and amount > #{account}"})
    int decreaseAccount(long userId,int account);


    /**
     * 加钱
     * @param userId 用户编号,account 金额
     * @return 更新记录数
     */
    @Update({" update ",TABLE_NAME," set amount = amount+#{account} where user_id = #{userId}"})
    int addAccount(long userId,int account);
}
