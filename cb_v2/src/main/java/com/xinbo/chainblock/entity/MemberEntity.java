package com.xinbo.chainblock.entity;

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
 * @date 6/24/22 4:21 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_member")
public class MemberEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帐号
     */
    private String username;

    /**
     * 密码
     */
    private String pwd;


    private Float money;

    private String salt;

    private Integer version;


    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;



}
