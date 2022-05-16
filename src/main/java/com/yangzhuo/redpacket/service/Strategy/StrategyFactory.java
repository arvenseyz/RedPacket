package com.yangzhuo.redpacket.service.Strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StrategyFactory {
    @Autowired
    private Map<String, RedPacketStrategy> strategyMap;

    public RedPacketStrategy getBy(String strategyName) {
        return strategyMap.get(strategyName);
    }
}
