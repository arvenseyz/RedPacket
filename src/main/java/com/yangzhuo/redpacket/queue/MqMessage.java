package com.yangzhuo.redpacket.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MqMessage implements Serializable {


    private long redPacketId;

    private long uid;

static public int GetDelayLevel(int seconds){
    return 10;
}
}

