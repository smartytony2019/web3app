package com.xinbo.chainblock.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
public class RoleEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否删除 1：已删除；0：未删除
     */
    @TableField("is_delete")
    private Integer isDelete;


}
