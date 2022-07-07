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
 * 角色菜单关系表
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role_menu")
public class RolePermissionEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 菜单ID
     */
    @TableField("permission_id")
    private Integer permissionId;


}
