package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

    private Integer id;

    /**
     * 帐号
     */
    private String username;

    /**
     * 金额
     */
    private Float money;

    /**
     * 提现钱包地址
     */
    private String withdrawWallet;

    /**
     * 类型(1:正常会员, 2:测试会员)
     */
    private Integer type;

}
