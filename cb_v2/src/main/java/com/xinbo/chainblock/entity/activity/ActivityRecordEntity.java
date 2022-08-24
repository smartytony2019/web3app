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
@TableName("t_activity_record")
public class ActivityRecordEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类目id
     */
    @TableField("activity_id")
    private Integer activityId;


    /**
     * 类目编码
     */
    @TableField("activity_name")
    private String activityName;


    /**
     * 类目中文
     */
    @TableField("activity_name_zh")
    private String activityNameZh;


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
     * 天数
     */
    @TableField("days")
    private Integer days;


    /**
     * 状态(0:未处理 1:成功 2:驳回)
     */
    @TableField("status")
    private Integer status;


    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
