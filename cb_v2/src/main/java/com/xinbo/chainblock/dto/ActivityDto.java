package com.xinbo.chainblock.dto;

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
public class ActivityDto {

    private Integer id;

    /**
     * 类目id
     */
    private Integer cateId;


    /**
     * 类目编码
     */
    private String cateName;


    /**
     * 标题
     */
    private String title;


    /**
     * 内容
     */
    private String content;


    /**
     * 序号
     */
    private Integer sorted;


    /**
     * 限制项(1:首充, 2:注册送, 10:其它)
     */
    private Integer type;


    /**
     * 语言
     */
    private String language;


    /**
     * 开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;


    /**
     * 结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;


    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 限制项(1:首充, 2:注册送, 10:其它)
     */
    private Boolean isEnable;


    /**
     * 限制项(1:首充, 2:注册送, 10:其它)
     */
    private Boolean isDel;

}
