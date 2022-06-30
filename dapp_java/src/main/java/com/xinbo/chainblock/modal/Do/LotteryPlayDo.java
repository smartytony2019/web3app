package com.xinbo.chainblock.modal.Do;

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
@TableName("t_lottery_play")
public class LotteryPlayDo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 玩法名称编码
     */
    @TableField("name_code")
    private String nameCode;

    /**
     * 玩法默认名称
     */
    @TableField("name_default")
    private String nameDefault;
}
