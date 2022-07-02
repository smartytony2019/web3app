package com.xinbo.chainblock.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tony
 * @date 6/24/22 6:26 下午
 * @desc 结果返回集
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = -1841231686851699945L;

    /**
     * 状态码
     */
    @ApiModelProperty("状态码 0:成功,非0:失败")
    private int code;

    /**
     * 信息
     */
    @ApiModelProperty("信息")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private T data;
}
