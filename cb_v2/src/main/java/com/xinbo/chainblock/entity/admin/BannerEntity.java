package com.xinbo.chainblock.entity.admin;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_banner")
public class BannerEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 语言表id
     */
    @TableField("lang_code")
    private String langCode;

    /**
     * 图片路径
     */
    @TableField("img")
    private String img;

    /**
     * 跳转路径
     */
    @TableField("href")
    private String href;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 描述
     */
    @TableField("description")
    private String desc;

    /**
     * 是否启用：1 启用 0 禁用
     */
    @TableField("is_enable")
    private Boolean isEnable;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
