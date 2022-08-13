package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinbo.chainblock.annotation.Translate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tony
 * @date 6/30/22 6:36 下午
 * @desc file desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HashBetDto {

    private Integer id;

    /**
     * 编号
     */
    private String sn;

    /**
     * 会员id
     */
    private Integer uid;

    /**
     * 会员名
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
     * 玩法id
     */
    private Integer playId;

    /**
     * 玩法编码
     */
    @Translate
    private String playName;

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
    private Integer betAmount;

    /**
     * 注单金额
     */
    private Float money;


    /**
     * 投注金额
     */
    private Float moneyAmount;


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
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 标记(1:赢, 2:输, 3: 和)
     */
    private Integer flag;

    /**
     * 状态(0:未结算,1:已结算,2:作废)
     */
    private Integer status;

}
