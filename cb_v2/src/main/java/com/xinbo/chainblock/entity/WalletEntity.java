package com.xinbo.chainblock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_wallet")
public class WalletEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 位数
     */
    @TableField("username")
    private String username;

    /**
     * 钱包类型 1: trx,2: eth
     */
    @TableField("type")
    private Integer type;

    /**
     * 地址
     */
    @TableField("base58")
    private String base58;

    /**
     * 地址
     */
    @TableField("hex")
    private String hex;

    /**
     * 主钱包
     */
    @TableField("is_main")
    private Boolean isMain;

}
