package com.xinbo.chainblock.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class HashBetVo {

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
    private String cateName;

    /**
     * 类目默认名称
     */
    private String cateNameZh;

    /**
     * 彩种id
     */
    private Integer gameId;

    /**
     * 彩种id
     */
    private String gameName;

    /**
     * 彩种id
     */
    private String gameNameZh;

    /**
     * 玩法id
     */
    private Integer playId;

    /**
     * 玩法编码
     */
    private String playName;

    /**
     * 玩法编码
     */
    private String playNameZh;

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
    private String content;

    /**
     * 投注内容(中文)
     */
    private String contentZh;

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 标记(1:赢, 2:输, 3: 和)
     */
    private Integer flag;

    /**
     * 状态(0:未结算,1:已结算,2:作废)
     */
    private Integer status;

    /**
     * 算法
     */
    private String algorithmCode;
}
