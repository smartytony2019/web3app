package com.xinbo.chainblock.modal.Do.terminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 6/23/22 2:53 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity<T> {

    private Integer code;

    private T data;

    private String msg;

}
