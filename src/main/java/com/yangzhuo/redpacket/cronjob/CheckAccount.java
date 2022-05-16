package com.yangzhuo.redpacket.cronjob;


import com.yangzhuo.redpacket.dao.AccountDao;
import com.yangzhuo.redpacket.dao.RedPacketDao;
import com.yangzhuo.redpacket.dao.UserRedPacketDao;
import com.yangzhuo.redpacket.domain.RedPacket;
import com.yangzhuo.redpacket.domain.UserRedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckAccount {

    private final UserRedPacketDao userRedPacketDao;
    private final RedPacketDao redPacketDao;
    private final AccountDao accountDao;

    @Autowired
    public CheckAccount(UserRedPacketDao userRedPacketDao, RedPacketDao redPacketDao, AccountDao accountDao) {
        this.userRedPacketDao = userRedPacketDao;
        this.redPacketDao = redPacketDao;
        this.accountDao = accountDao;
    }

    @Scheduled(cron="0 1 * * *")
    public void scheduledTask() {

        //对账公式：小红包的钱+大红包余额=大红包面值
        //1。获取昨天的大红包
        List<RedPacket> redPackets=redPacketDao.getYesterDayRedPacket();
        for (RedPacket redPacket :
                redPackets) {
            List<UserRedPacket> userRedPackets= userRedPacketDao.getUserRedPackets(redPacket.getRedPacketId());
            int amount = redPacket.getAmount();
            for (UserRedPacket userRedPacket :
                    userRedPackets) {
                amount+=userRedPacket.getAmount();
            }
            if (amount!=redPacket.getInitAmount()){
                //账户不平
                //报警，发送账单
                //do something
            }
        }
    }
}




