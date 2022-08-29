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
@TableName("t_hash_play")
public class HashPlayEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类目id
     */
    @TableField("cate_id")
    private Integer cateId;

    /**
     * 类目名称编码
     */
    @TableField("cate_name")
    private String cateName;

    /**
     * 类目中文名称
     */
    @TableField("cate_name_zh")
    private String cateNameZh;

    /**
     * 游戏id
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 游戏名称编码
     */
    @TableField("game_name")
    private String gameName;

    /**
     * 游戏中文名称
     */
    @TableField("game_name_zh")
    private String gameNameZh;

    /**
     * 房间名称编码
     */
    @TableField("name")
    private String name;

    /**
     * 房间中文名称
     */
    @TableField("name_zh")
    private String nameZh;

    /**
     * 最低金额
     */
    @TableField("min")
    private Integer min;

    /**
     * 最高金额
     */
    @TableField("max")
    private Integer max;

    /**
     * 最大赔率
     */
    @TableField("max_odds")
    private Float maxOdds;

    /**
     * 类型(1:体验房, 2:初级房, 3:中级房, 4:高级房)
     */
    @TableField("type")
    private Integer type;
}
