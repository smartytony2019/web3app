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
@TableName("t_agent")
public class AgentEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上级用户id
     */
    @TableField("p_uid")
    private Integer pUid;

    /**
     * 用户id
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 下级用户
     */
    @TableField("child")
    private String child;

}
