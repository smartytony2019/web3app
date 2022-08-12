package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HashResultDto {

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
     * 游戏id
     */
    private Integer gameId;

    /**
     * 玩法id
     */
    private Integer playId;

    /**
     * 彩种id
     */
    private String sn;

    /**
     * 会员数字钱包地址
     */
    private String toAddress;

    /**
     * 交易id
     */
    private String txID;

    /**
     * 哈希码
     */
    private String blockHash;

    /**
     * 块高度
     */
    private String blockHeight;

    /**
     * 开奖时间
     */
    private Date openTime;

    /**
     * 开奖时间戳
     */
    private Long openTimestamp;

    /**
     * 网络
     */
    private String network;

    /**
     * 标记(1:赢, 2:输, 3: 和)
     */
    private Integer flag;

}
