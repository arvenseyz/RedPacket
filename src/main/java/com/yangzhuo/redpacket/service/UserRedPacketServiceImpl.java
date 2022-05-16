package com.yangzhuo.redpacket.service;

import com.alibaba.fastjson.JSON;
import com.yangzhuo.redpacket.dao.AccountDao;
import com.yangzhuo.redpacket.dao.RedPacketDao;
import com.yangzhuo.redpacket.dao.UserRedPacketDao;
import com.yangzhuo.redpacket.domain.RedPacket;
import com.yangzhuo.redpacket.domain.UserRedPacket;
import com.yangzhuo.redpacket.queue.MqMessage;
import com.yangzhuo.redpacket.service.Strategy.StrategyFactory;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author yangzhuo
 */

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

    private final UserRedPacketDao userRedPacketDao;
    private final RedPacketDao redPacketDao;
    private final AccountDao accountDao;
    @Autowired
    private ApplicationContext context;
    private RocketMQTemplate rocketMQTemplate;


    @Autowired
    public UserRedPacketServiceImpl(UserRedPacketDao userRedPacketDao, RedPacketDao redPacketDao, AccountDao accountDao) {
        this.userRedPacketDao = userRedPacketDao;
        this.redPacketDao = redPacketDao;
        this.accountDao = accountDao;
    }
    private static final int FAILED = 0;
    private static final int SUCCESS = 1;




    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int sendRedPacket(Long userId,int stock, int amount,String splitType) {
        //扣钱
        //1. 更新账户
        int success=accountDao.decreaseAccount(userId,amount);
        if (success==0){
            throw new RuntimeException("account error");
        }
        //2. 生成红包
        RedPacket redPacket = new RedPacket();
        redPacket.setRedPacketId(Snowflake.GetId());
        redPacket.setSplitType(splitType);
        redPacket.setUserId(userId);
        redPacket.setSendDate(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        redPacket.setTotal(stock);
        redPacket.setStock(stock);
        redPacket.setAmount(amount);
        redPacket.setInitAmount(amount);
        success=redPacketDao.sendRedPacket(redPacket);
        if (success==0){
            throw new RuntimeException("account error");
        }
        //3. 延时消息
        MqMessage msg = new MqMessage();
        msg.setRedPacketId(redPacket.getRedPacketId());
        msg.setUid(userId);
        SendResult result=rocketMQTemplate.syncSend("myTest", JSON.toJSONString(redPacket),MqMessage.GetDelayLevel(seconds));
        if (!result.getSendStatus().equals(SendStatus.SEND_OK)){
            throw new RuntimeException("account error");
        }
        return SUCCESS;

    }

    /**
     *
     * @param redPacketId 红包编号
     * @param userId 抢红包用户信息
     * @return 影响记录数
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int grabRedPacket(Long redPacketId, Long userId) {
        //1. 锁一个红包
        RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);
        if (redPacket.getStock()==0){
            throw new RuntimeException("account error");
        }
        //2. 生成红包金额(单位分）
        int amount=context.getBean(StrategyFactory.class).getBy(redPacket.getSplitType()).getAmount(redPacket);
        //3. 更新账户
        accountDao.addAccount(userId,amount);
        //4. 插入红包记录
        UserRedPacket userRedPacket = new UserRedPacket();
        userRedPacket.setRedPacketId(redPacketId);
        userRedPacket.setUserId(userId);
        userRedPacket.setAmount(amout);
        int success=userRedPacketDao.grabRedPacket(new UserRedPacket());
        if (success==0) {
            throw new RuntimeException("account error");
        }
        //5. 更新大红包余额和库存
        success=redPacketDao.decreaseRedPacket(redPacketId,amount);
        if (success==0) {
            throw new RuntimeException("account error");
        }
        return SUCCESS;
    }

    @Override
    public List<UserRedPacket> queryUserRedPacket(Long userId){
        return userRedPacketDao.getUserRedPacketsByUserId(userId);
    }

}


