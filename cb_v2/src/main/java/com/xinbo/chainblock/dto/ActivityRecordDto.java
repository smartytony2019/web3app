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
public class ActivityRecordDto {

    private Integer id;

    /**
     * 类目id
     */
    private Integer activityId;


    /**
     * 类目编码
     */
    private String activityTitle;



    /**
     * 标题
     */
    private Integer uid;


    /**
     * 内容
     */
    private String username;


    /**
     * 金额
     */
    private Float money;


    /**
     * 赠送币种
     */
    private Integer symbol;


    /**
     * 状态(0:未处理 1:成功 2:驳回)
     */
    private Integer status;


    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 备注
     */
    private String remark;
}
