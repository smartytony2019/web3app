package com.xinbo.chainblock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_lottery_game")
public class LotteryGameEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 游戏名称编码
     */
    @TableField("name_code")
    private String nameCode;

    /**
     * 游戏默认名称
     */
    @TableField("name_default")
    private String nameDefault;

    /**
     * 类目id
     */
    @TableField("cate_id")
    private Integer cateId;

    /**
     * 类目编码
     */
    @TableField("cate_name_code")
    private String cateNameCode;

    /**
     * 类目默认名称
     */
    @TableField("cate_name_default")
    private String cateNameDefault;

    /**
     * 是否开启
     */
    private Boolean enable;


}
