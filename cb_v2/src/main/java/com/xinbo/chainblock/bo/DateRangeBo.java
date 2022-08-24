package com.xinbo.chainblock.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tony
 * @date 8/21/22 6:18 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRangeBo {

    private Date startTime;

    private Date endTime;

    private Long startTimestamp;

    private Long endTimestamp;

}
