package com.xinbo.chainblock.modal.Dto;

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
public class LotteryBetDto {

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
    private Integer cateId;

    /**
     * 类目编码
     */
    private String cateNameCode;

    /**
     * 类目默认名称
     */
    private String cateNameDefault;

    /**
     * 彩种id
     */
    private Integer gameId;

    /**
     * 彩种id
     */
    private String gameNameCode;

    /**
     * 彩种id
     */
    private String gameNameDefault;

    /**
     * 玩法id
     */
    private Integer playId;

    /**
     * 玩法编码
     */
    private String playNameCode;

    /**
     * 玩法编码
     */
    private String playNameDefault;

    /**
     * 玩法编码id
     */
    private Integer playCodeId;

    /**
     * 玩法编码id
     */
    private String playCodeNameCode;

    /**
     * 玩法编码id
     */
    private String playCodeNameDefault;

    /**
     * 开奖结果
     */
    private String hashResult;

    /**
     * 期号
     */
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态(0:未结算,1:已结算,2:作废)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
