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
    @TableField("activity_title")
    private String activityTitle;



    /**
     * 标题
     */
    @TableField("uid")
    private Integer uid;


    /**
     * 内容
     */
    @TableField("username")
    private String username;


    /**
     * 金额
     */
    @TableField("money")
    private Float money;


    /**
     * 赠送币种
     */
    @TableField("symbol")
    private String symbol;


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


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
