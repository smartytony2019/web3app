package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinbo.chainblock.annotation.Translate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tony
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemConfigDto {

    private Integer id;


    /**
     * 键
     */
    private String key;

    /**
     * 键
     */
    private String value;

    /**
     * 数据类型(1: String 2:Integer, 3:Float, 4: Boolean, 5:Date)
     */
    private String type;

    /**
     * 配置分类(1: 系统配置 2:会员配置, 3:游戏配置)
     */
    private Integer cate;

    /**
     * 备注
     */
    private String comment;


}
