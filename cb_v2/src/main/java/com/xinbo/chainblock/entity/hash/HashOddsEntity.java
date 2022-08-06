package com.xinbo.chainblock.entity.hash;

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
@TableName("t_hash_odds")
public class HashOddsEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 游戏id
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 名称中文
     */
    @TableField("name_zh")
    private String nameZh;

    /**
     * 赔率
     */
    @TableField("odds")
    private Float odds;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

}
