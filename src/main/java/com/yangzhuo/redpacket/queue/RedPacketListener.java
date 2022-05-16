package com.yangzhuo.redpacket.queue;


import com.yangzhuo.redpacket.dao.AccountDao;
import com.yangzhuo.redpacket.dao.RedPacketDao;
import com.yangzhuo.redpacket.dao.UserRedPacketDao;
import com.yangzhuo.redpacket.domain.RedPacket;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


@Slf4j
@Component
@RocketMQMessageListener(
        topic = "RedPacketTopic",
        consumerGroup = "common_consumer_group")
public class RedPacketListener implements RocketMQListener<MqMessage> {

    private final UserRedPacketDao userRedPacketDao;
    private final RedPacketDao redPacketDao;
    private final AccountDao accountDao;

    Random random = new Random();
    @Autowired
    public RedPacketListener(UserRedPacketDao userRedPacketDao, RedPacketDao redPacketDao, AccountDao accountDao) {
        this.userRedPacketDao = userRedPacketDao;
        this.redPacketDao = redPacketDao;
        this.accountDao = accountDao;
    }
    @Override
    @Transactional
    public void onMessage(MqMessage message) {
        //1.看看红包是不是抢完了
        RedPacket redPacket = redPacketDao.getRedPacketForUpdate(message.getRedPacketId());
        if (redPacket.getStock()==0){
            return;
        }
        //不然就还给主人
        //2.. 更新账户
        accountDao.addAccount(message.getUid(),redPacket.getAmount());
        //3.. 更新大红包余额和库存
        redPacketDao.decreaseRedPacket(redPacket.getRedPacketId(), redPacket.getAmount());
    }
}