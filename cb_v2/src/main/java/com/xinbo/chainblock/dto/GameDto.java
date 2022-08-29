package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinbo.chainblock.annotation.Translate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class GameDto {

    private Integer id;

    /**
     * 类目id编码
     */
    private Integer cateId;

    /**
     * 类目名称编码
     */
    @Translate
    private String cateName;

    /**
     * 游戏名称编码
     */
    @Translate
    private String name;


    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 地址
     */
    private String address;

    /**
     * 算法
     */
    private String algorithm;

    /**
     * 赔率
     */
    private Float odds;
}
