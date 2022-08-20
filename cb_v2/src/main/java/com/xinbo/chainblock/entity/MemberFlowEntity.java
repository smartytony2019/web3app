package com.xinbo.chainblock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_member_flow")
public class MemberFlowEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 订单号
     */
    @TableField("sn")
    private String sn;

    /**
     * 用户名
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 帐变前金额
     */
    @TableField("before_money")
    private Float beforeMoney;

    /**
     * 帐变后金额
     */
    @TableField("after_money")
    private Float afterMoney;

    /**
     * 流水金额
     */
    @TableField("flow_money")
    private Float flowMoney;

    /**
     * 帐变项
     */
    @TableField("item")
    private String item;

    /**
     * 帐变编码
     */
    @TableField("item_code")
    private Integer itemCode;

    /**
     * 帐变中文
     */
    @TableField("item_zh")
    private String itemZh;

    /**
     * 用户名
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 扩展字段
     */
    @TableField("ext")
    private String ext;

}
