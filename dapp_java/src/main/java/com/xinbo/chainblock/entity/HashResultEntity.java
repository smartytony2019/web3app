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
@TableName("t_hash_result")
public class HashResultEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 彩种id
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 期号数
     */
    private String num;

    /**
     * 哈希码
     */
    @TableField("block_hash")
    private String blockHash;

    /**
     * 块高度
     */
    @TableField("block_height")
    private String blockHeight;

    /**
     * 开奖时间
     */
    @TableField("open_time")
    private Date openTime;

    /**
     * 开奖时间戳
     */
    @TableField("open_timestamp")
    private Long openTimestamp;

    /**
     * 网络
     */
    private String network;

    /**
     * 状态(0:未结算,1:已结算)
     */
    @TableField("is_settle")
    private Boolean isSettle;

}
