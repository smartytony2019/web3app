package com.xinbo.chainblock.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
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
public class GameVo {

    private Integer id;

    /**
     * 类目id编码
     */
    private Integer cateId;

    /**
     * 类目名称编码
     */
    private String cateName;

    /**
     * 类目中文名称
     */
    private String cateNameZh;

    /**
     * 游戏名称编码
     */
    private String name;

    /**
     * 游戏中文名称
     */
    private String nameZh;

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
    private String algorithmCode;

    /**
     * 赔率
     */
    private Float odds;

}
