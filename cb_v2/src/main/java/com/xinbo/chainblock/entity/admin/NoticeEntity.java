package com.xinbo.chainblock.entity.admin;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_notice")
public class NoticeEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 语言表id
     */
    @TableField("lang_id")
    private Integer langId;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 公告内容
     */
    @TableField("content")
    private String content;

    /**
     * 操作员
     */
    @TableField("operator")
    private String operator;

    /**
     * 是否启用：1启用 0关闭
     */
    @TableField("is_enable")
    private boolean isEnable;

    /**
     * 是否置顶：1置顶 0不置顶
     */
    @TableField("is_top")
    private boolean isTop;

    /**
     * 类型：1用户公告 2首页公告
     */
    @TableField("type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private DateTime createTime;

}
