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
@TableName("t_lottery_bet")
public class LotteryBetEntity {

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
     * 彩种id
     */
    @TableField("cate_name_code")
    private String cateNameCode;

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
     * 玩法编码id
     */
    @TableField("play_code_id")
    private Integer playCodeId;

    /**
     * 注单赔率
     */
    @TableField("bet_odds")
    private Float betOdds;

    /**
     * 注单编码
     */
    @TableField("bet_code")
    private String betCode;

    /**
     * 注单金额
     */
    @TableField("bet_money")
    private Float betMoney;

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
}
