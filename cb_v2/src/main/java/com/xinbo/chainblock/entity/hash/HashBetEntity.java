package com.xinbo.chainblock.entity.hash;

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
@TableName("t_hash_bet")
public class HashBetEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    @TableField("sn")
    private String sn;

    /**
     * 会员id
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 会员名
     */
    @TableField("username")
    private String username;

    /**
     * 彩种id
     */
    @TableField("cate_id")
    private Integer cateId;

    /**
     * 类目编码
     */
    @TableField("cate_name")
    private String cateName;

    /**
     * 类目默认名称
     */
    @TableField("cate_name_zh")
    private String cateNameZh;

    /**
     * 彩种id
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 彩种id
     */
    @TableField("game_name")
    private String gameName;

    /**
     * 彩种id
     */
    @TableField("game_name_zh")
    private String gameNameZh;

    /**
     * 玩法id
     */
    @TableField("play_id")
    private Integer playId;

    /**
     * 玩法编码
     */
    @TableField("play_name")
    private String playName;

    /**
     * 玩法编码
     */
    @TableField("play_name_zh")
    private String playNameZh;

    /**
     * 交易id
     */
    @TableField("transaction_id")
    private String transactionId;

    /**
     * 块哈希
     */
    @TableField("block_hash")
    private String blockHash;

    /**
     * 块哈希
     */
    @TableField("block_height")
    private String blockHeight;

    /**
     * 结果
     */
    @TableField("result")
    private Integer result;

    /**
     * 网络
     */
    @TableField("network")
    private String network;

    /**
     * 投注内容
     */
    @TableField("content")
    private String content;

    /**
     * 投注内容(中文)
     */
    @TableField("content_zh")
    private String contentZh;

    /**
     * 注单赔率
     */
    @TableField("odds")
    private Float odds;

    /**
     * 注单金额
     */
    @TableField("bet_amount")
    private Integer betAmount;

    /**
     * 注单金额
     */
    @TableField("money")
    private Float money;

    /**
     * 投注金额
     */
    @TableField("money_amount")
    private Float moneyAmount;


    /**
     * 赢利金额
     */
    @TableField("profit_money")
    private Float profitMoney;

    /**
     * 派彩金额
     */
    @TableField("payout_money")
    private Float payoutMoney;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建时间戳
     */
    @TableField("create_timestamp")
    private Long createTimestamp;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新时间戳
     */
    @TableField("update_timestamp")
    private Long updateTimestamp;

    /**
     * 状态(0:未结算,1:已结算,2:作废)
     */
    @TableField("status")
    private Integer status;

    /**
     * 算法
     */
    @TableField("algorithm_code")
    private String algorithmCode;
}
