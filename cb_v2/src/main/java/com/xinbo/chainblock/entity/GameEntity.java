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
 * @desc 游戏
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_game")
public class GameEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类目id编码
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
     * 游戏名称编码
     */
    @TableField("name")
    private String name;

    /**
     * 游戏中文名称
     */
    @TableField("name_zh")
    private String nameZh;

    /**
     * 是否开启
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 算法
     */
    @TableField("algorithm")
    private String algorithm;

    /**
     * 赔率
     */
    @TableField("odds")
    private Float odds;
}
