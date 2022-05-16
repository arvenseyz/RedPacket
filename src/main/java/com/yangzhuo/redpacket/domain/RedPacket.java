package com.yangzhuo.redpacket.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 红包表
 * @author yangzhuo
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedPacket implements Serializable {
    /**
     * 红包编号
     */
    private Long redPacketId;
    /**
     * 发红包用户
     */
    private Long userId;
    /**
     * 所在群
     */
    private Long groupId;
    /**
     * 大红包面值
     */
    private int initAmount;
    /**
     * 大红包剩余金额
     */
    private int amount;
    /**
     * 发红包时间
     */
    private Long sendDate;
    /**
     * 小红包总数
     */
    private Integer total;

    /**
     * 剩余小红包个数
     */
    private Integer stock;
    /**
     * 版本
     */
    private String splitType;
    /**
     * 备注
     */
    private String note;


}
