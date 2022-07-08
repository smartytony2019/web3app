package com.xinbo.chainblock.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author tony
 * @date 7/8/22 5:33 下午
 * @desc file desc
 */
@Data
@Builder
@AllArgsConstructor
public class BusinessException extends Exception {

    private Integer code;
    private String msg;

}
