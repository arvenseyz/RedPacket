package com.yangzhuo.redpacket.service;

import com.yangzhuo.redpacket.domain.RedPacket;
/**
 * @author yangzhuo
 */

public interface RedPacketService {
    /**
     * 获取红包
     * @param id 红包编号
     * @return 红包信息
     */
    RedPacket getRedPacket(Long id);

    /**
     * 加入悲观锁后的获取红包信息
     * @param id 红包编号
     * @return 红包具体信息
     */
    RedPacket getRedPacketForUpdate(Long id);

    /**
     * 扣减红包
     * @param id 编号
     * @return 影响记录数
     */
   int decreaseRedPacket(Long id,int amount);

    RedPacket queryGroupRedPacket(Long groupId);
}
