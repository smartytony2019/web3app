package com.xinbo.chainblock.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tony
 * @date 7/1/22 4:49 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasePage implements Serializable {


    /**
     * 總條數
     */
    private long total;


    /**
     * 数据
     */
    private Object records;

}
