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
@TableName("t_activity_order")
public class ActivityOrderEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类目id
     */
    @TableField("activity_id")
    private Integer activity_id;


    /**
     * 类目编码
     */
    @TableField("activity_name")
    private String activity_name;


    /**
     * 类目中文
     */
    @TableField("activity_name_zh")
    private String activity_name_zh;


    /**
     * 标题
     */
    @TableField("uid")
    private String uid;


    /**
     * 内容
     */
    @TableField("username")
    private String username;


    /**
     * 序号
     */
    @TableField("money")
    private Integer money;


    /**
     * 赠送币种
     */
    @TableField("symbol")
    private String symbol;


    /**
     * 开始时间
     */
    @TableField("cycle")
    private Integer cycle;


    /**
     * 天数
     */
    @TableField("days")
    private Integer days;


    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
