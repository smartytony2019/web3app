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
@TableName("t_hash_result")
public class HashResultEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 游戏id
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 玩法id
     */
    @TableField("play_id")
    private Integer playId;

    /**
     * 彩种id
     */
    @TableField("sn")
    private String sn;

    /**
     * 会员数字钱包地址
     */
    @TableField("to_address")
    private String toAddress;

    /**
     * 交易id
     */
    @TableField("txID")
    private String txID;

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
    @TableField("network")
    private String network;

    /**
     * 标记(1:赢, 2:输, 3: 和)
     */
    @TableField("flag")
    private Integer flag;

}
