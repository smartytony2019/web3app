package com.xinbo.chainblock.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_permission")
public class PermissionEntity {

    /**
     * 主键
     */
    @TableId("id")
    private Integer id;

    /**
     * 名称
     */
    @TableField("path")
    private String path;

    /**
     * 名称
     */
    @TableField("component")
    private String component;

    /**
     * 名称
     */
    @TableField("redirect")
    private String redirect;

    /**
     * 菜单编码
     */
    @TableField("name")
    private String name;

    /**
     * 菜单编码
     */
    @TableField("name_default")
    private String nameDefault;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 菜单编码
     */
    @TableField("code")
    private Integer code;

    /**
     * 节点类型，1文件夹，2页面，3按钮
     */
    @TableField("node_type")
    private Integer nodeType;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 层次
     */
    @TableField("level")
    private Integer level;

    /**
     * 父节点
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 图标地址
     */
    @TableField("parent_path")
    private String parentPath;


    /**
     * 是否删除 1：已删除；0：未删除
     */
    @TableField("is_delete")
    private Integer isDelete;


    @TableField(exist = false)
    private List<PermissionEntity> children;

}
