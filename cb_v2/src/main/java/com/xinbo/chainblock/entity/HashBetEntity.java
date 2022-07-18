package com.xinbo.chainblock.entity;

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
    @TableField("cate_id")
    private Integer cateId;

    /**
     * 类目编码
     */
    @TableField("cate_name_code")
    private String cateNameCode;

    /**
     * 类目默认名称
     */
    @TableField("cate_name_default")
    private String cateNameDefault;

    /**
     * 彩种id
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 彩种id
     */
    @TableField("game_name_code")
    private String gameNameCode;

    /**
     * 彩种id
     */
    @TableField("game_name_default")
    private String gameNameDefault;

    /**
     * 玩法id
     */
    @TableField("play_id")
    private Integer playId;

    /**
     * 玩法编码
     */
    @TableField("play_name_code")
    private String playNameCode;

    /**
     * 玩法编码
     */
    @TableField("play_name_default")
    private String playNameDefault;

    /**
     * 玩法编码id
     */
    @TableField("play_code_id")
    private Integer playCodeId;

    /**
     * 玩法编码id
     */
    @TableField("play_code_name_code")
    private String playCodeNameCode;

    /**
     * 玩法编码id
     */
    @TableField("play_code_name_default")
    private String playCodeNameDefault;

    /**
     * 开奖结果
     */
    @TableField("hash_result")
    private String hashResult;

    /**
     * 期号
     */
    @TableField("num")
    private String num;

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
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 状态(0:未结算,1:已结算,2:作废)
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @TableField("status")
    private String remark;
}
