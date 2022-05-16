package com.yangzhuo.redpacket.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 红包表
 * @author yangzhuo
 */

@Getter
@Setter
@NoArgsConstructor

public class Account implements Serializable{
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户
     */
    private Long userId;
    /**
     * 余额
     */
    private Double amount;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
    /**
     * 版本
     */
    private Integer version;
}
