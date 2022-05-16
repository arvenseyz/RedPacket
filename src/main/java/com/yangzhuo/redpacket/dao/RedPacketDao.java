package com.yangzhuo.redpacket.dao;

import com.yangzhuo.redpacket.domain.RedPacket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author yangzhuo
 */

@Mapper
@Repository
public interface RedPacketDao {
    String TABLE_NAME = " t_red_packet ";
    String SELECT_FIELDS = " red_packet_id , user_id,group_id, amount, init_amount,send_date, total,  stock, version, note ";
    String INSERT_FIELDS = " red_packet_id , user_id,group_id, amount, init_amount,send_date, total,  stock, version, note ";
    /**
     * 获取红包信息
     * @param packetId 红包编号
     * @return 红包的具体信息
     */
    @Select({" select ",SELECT_FIELDS," from ",TABLE_NAME," where packet_id = #{packetId}"})
    RedPacket getRedPacket(long packetId);


    @Select({" select ",SELECT_FIELDS," from ",TABLE_NAME," where send_date between now() and now()-86400*2"})
    List<RedPacket> getYesterDayRedPacket();
    /**
     * 加入悲观锁后获取红包信息的查询语句
     * @param packetId 红包编号
     * @return 红包具体信息
     */
    @Select({" select ",SELECT_FIELDS," from ",TABLE_NAME," where packet_id = #{packetId} for update"})
    RedPacket getRedPacketForUpdate(long packetId);

    /**
     * 扣减红包数
     * @param packetId 红包编号
     * @return 更新记录数
     */
    @Update({" update ",TABLE_NAME," set stock = stock-1 , amount= amount - #{id} where packet_id = #{packetId} and stock > 1"})
    int decreaseRedPacket(long packetId,int amount);

    /**
     * 插入红包信息
     * @param redPacket 红包信息
     * @return 影响记录数
     * 注解@Options 获取插入的主键信息
     */
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{redPacketId},#{userId},#{amount},#{amount},now(),,#{stock},#{stock}#{note})"})
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int sendRedPacket(RedPacket redPacket);


    @Select({" select ",SELECT_FIELDS," from ",TABLE_NAME," where group_id = #{groupId}"})
    RedPacket getRedPacketByGroupId(long groupId);
}
