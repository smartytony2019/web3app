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
@TableName("t_expect")
public class ExpectEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 彩种id
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 地址
     */
    private String num;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private String endTime;

}
