package com.xinbo.chainblock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class AgentRebateDto {

    private Integer id;

    /**
     * 最低业绩
     */
    private Integer min;
    /**
     * 最高业绩
     */
    private Integer max;
    /**
     * 回扣
     */
    private Integer rebate;


}
