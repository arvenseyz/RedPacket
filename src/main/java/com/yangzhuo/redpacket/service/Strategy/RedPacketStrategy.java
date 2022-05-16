package com.yangzhuo.redpacket.service.Strategy;

import com.yangzhuo.redpacket.domain.RedPacket;

public interface RedPacketStrategy {
    int getAmount(RedPacket redPacket);
}
