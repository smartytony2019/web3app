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
 * @desc 游戏类目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_category")
public class CategoryEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 序号
     */
    @TableField("sort")
    private Integer sort;
}
