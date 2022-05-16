package com.yangzhuo.redpacket.service.Strategy;

import com.yangzhuo.redpacket.domain.RedPacket;
import org.springframework.stereotype.Component;


@Component
public class AverageGenerate implements RedPacketStrategy{
    @Override
    public int getAmount(RedPacket redPacket) {
        return redPacket.getAmount()/redPacket.getStock();
    }
}
