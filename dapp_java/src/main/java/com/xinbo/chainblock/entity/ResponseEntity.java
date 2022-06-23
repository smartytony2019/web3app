package com.xinbo.chainblock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 熊二
 * @date 6/23/22 2:53 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity<T> {

    private Integer code;

    private T data;

    private String msg;

}
