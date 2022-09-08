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
public class HashOfflineBetEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    @TableField("sn")
    private String sn;

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
     * 网络
     */
    @TableField("network")
    private String network;

    /**
     * 注单赔率
     */
    @TableField("odds")
    private Float odds;

    /**
     * 注单金额
     */
    @TableField("money")
    private Float money;


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
     * 标记(1:赢, 2:输, 3: 和)
     */
    @TableField("flag")
    private Integer flag;

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
