package com.xinbo.chainblock.bo;

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
public class BaseApiBo<T> {

    private Integer code;

    private T data;

    private String msg;

}
