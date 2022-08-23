package com.xinbo.chainblock.entity.activity;

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
@TableName("t_activity_cate")
public class ActivityCateEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类目id
     */
    @TableField("cate_id")
    private Integer cateId;


    /**
     * 类目编码
     */
    @TableField("name")
    private String name;


    /**
     * 类目中文
     */
    @TableField("name_zh")
    private String nameZh;


}
