package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class HashPlayDto {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类目id
     */
    private Integer cateId;

    /**
     * 类目名称编码
     */
    @Translate
    private String cateName;


    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 游戏名称编码
     */
    @Translate
    private String gameName;


    /**
     * 房间名称编码
     */
    @Translate
    private String name;


    /**
     * 最低金额
     */
    private Integer min;

    /**
     * 最高金额
     */
    private Integer max;

    /**
     * 最大赔率
     */
    private Float maxOdds;

    /**
     * 类型(1:体验房, 2:初级房, 3:中级房, 4:高级房)
     */
    private Integer type;

    /**
     * 图片地址
     */
    private String address;
}
