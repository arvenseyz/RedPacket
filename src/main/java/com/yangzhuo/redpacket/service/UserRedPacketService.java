package com.yangzhuo.redpacket.service;

import com.yangzhuo.redpacket.domain.UserRedPacket;

import java.util.List;

/**
 *@author yangzhuo
 */

public interface UserRedPacketService {

     int grabRedPacket(Long redPacketId,Long userId);

     int sendRedPacket(Long userId,int stock, int amount,String splitType);

     List<UserRedPacket> queryUserRedPacket(Long userId);
}
