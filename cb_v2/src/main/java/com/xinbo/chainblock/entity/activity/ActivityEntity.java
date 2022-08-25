package com.xinbo.chainblock.entity.activity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("t_activity")
public class ActivityEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类目id
     */
    @TableField("cate_id")
    private Integer cateId;


    /**
     * 类目编码
     */
    @TableField("cate_name")
    private String cateName;


    /**
     * 类目中文
     */
    @TableField("cate_name_zh")
    private String cateNameZh;


    /**
     * 活动名称
     */
    @TableField("name")
    private String name;


    /**
     * 内容
     */
    @TableField("content")
    private String content;


    /**
     * 序号
     */
    @TableField("sorted")
    private Integer sorted;


    /**
     * 限制项(1:首充, 2:注册送, 10:其它)
     */
    @TableField("type")
    private Integer type;


    /**
     * 语言
     */
    @TableField("language")
    private String language;


    /**
     * 开始时间
     */
    @TableField("begin_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;


    /**
     * 结束时间
     */
    @TableField("finish_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;


    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 限制项(1:首充, 2:注册送, 10:其它)
     */
    @TableField("is_enable")
    private Boolean isEnable;

}
