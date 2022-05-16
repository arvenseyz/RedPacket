package com.yangzhuo.redpacket.service;

import com.yangzhuo.redpacket.dao.RedPacketDao;
import com.yangzhuo.redpacket.domain.RedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 红包服务实现类
 * @author yangzhuo
 */
@Service
public class RedPacketServiceImpl implements RedPacketService{

    private final RedPacketDao redPacketDao;

    @Autowired
    public RedPacketServiceImpl(RedPacketDao redPacketDao) {
        this.redPacketDao = redPacketDao;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public RedPacket getRedPacket(Long id) {

        return redPacketDao.getRedPacket(id);
    }

    @Override
    public RedPacket getRedPacketForUpdate(Long id) {
        return redPacketDao.getRedPacketForUpdate(id);
    }

    /**
     * 使用@Transactional事务注解需要配置回滚或在方法中显式回滚
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int decreaseRedPacket(Long packetId,int amount) {
        return redPacketDao.decreaseRedPacket(packetId,amount);
    }


    @Override
    public RedPacket queryGroupRedPacket(Long groupId){
        return redPacketDao.getRedPacketByGroupId(groupId);
    }
}
