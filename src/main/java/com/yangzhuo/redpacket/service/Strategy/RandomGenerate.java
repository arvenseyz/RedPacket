package com.yangzhuo.redpacket.service.Strategy;

import com.yangzhuo.redpacket.domain.RedPacket;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomGenerate implements RedPacketStrategy{
    Random random = new Random();
    @Override
    public int getAmount(RedPacket redPacket){
        int amout = 0;
        // 最后一个红包，全部拿走
        if (redPacket.getStock()==1){
            amout=redPacket.getAmount();
        }else {
            //否则1分到剩余平均金额*2
            int min = 1;
            int max = redPacket.getAmount()/redPacket.getStock()*2;
            amout=random.nextInt(max-1)+1;
        }
        return amout;
    }
}
