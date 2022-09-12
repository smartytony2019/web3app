package com.xinbo.chainblock.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_lang")
public class LanguageEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 语言编码
     */
    @TableField("lang_code")
    private String langCode;

    /**
     * 语言名称
     */
    @TableField("lang_name")
    private String langName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
}
