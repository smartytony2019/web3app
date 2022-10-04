package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinbo.chainblock.annotation.Translate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tony
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HashOfflineBetDto {

    private Integer id;

    /**
     * 编号
     */
    private String sn;

    /**
     * 用户名
     */
    private String username;

    /**
     * 彩种id
     */
    private Integer cateId;

    /**
     * 类目编码
     */
    @Translate
    private String cateName;

    /**
     * 彩种id
     */
    private Integer gameId;

    /**
     * 彩种id
     */
    @Translate
    private String gameName;


    /**
     * 交易id
     */
    private String transactionId;

    /**
     * 块哈希
     */
    private String blockHash;

    /**
     * 块哈希
     */
    private String blockHeight;

    /**
     * 网络
     */
    private String network;

    /**
     * 投注内容
     */
    @Translate
    private String content;

    /**
     * 注单赔率
     */
    private Float odds;

    /**
     * 注单金额
     */
    private Float money;


    /**
     * 赢利金额
     */
    private Float profitMoney;

    /**
     * 派彩金额
     */
    private Float payoutMoney;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建时间戳
     */
    private Long createTimestamp;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新时间戳
     */
    private Long updateTimestamp;

    /**
     * 标记(1:赢, 2:输, 3: 和)
     */
    private Integer result;

    /**
     * 状态(0:未结算,1:已结算,2:作废)
     */
    private Integer status;
}
