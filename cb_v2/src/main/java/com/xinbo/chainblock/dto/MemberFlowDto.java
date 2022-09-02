package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinbo.chainblock.annotation.Translate;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberFlowDto {

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 帐变前金额
     */
    private Float beforeMoney;

    /**
     * 帐变后金额
     */
    private Float afterMoney;

    /**
     * 流水金额
     */
    private Float flowMoney;

    /**
     * 帐变项
     */
    @Translate
    private String item;

    /**
     * 帐变编码
     */
    private Integer itemCode;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建时间戳
     */
    private Long createTimestamp;

    /**
     * 扩展字段
     */
    private String ext;

}
