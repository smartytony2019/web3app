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
@TableName("t_system_config")
public class SystemConfigEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 键
     */
    @TableField("key")
    private String key;

    /**
     * 键
     */
    @TableField("value")
    private String value;

    /**
     * 数据类型(1: String 2:Integer, 3:Float, 4: Boolean, 5:Date)
     */
    @TableField("type")
    private String type;

    /**
     * 配置分类(1: 系统配置 2:会员配置, 3:游戏配置)
     */
    @TableField("cate")
    private String cate;

    /**
     * 备注
     */
    @TableField("comment")
    private String comment;

}
