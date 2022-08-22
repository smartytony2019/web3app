package com.xinbo.chainblock.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 8/20/22 7:21 下午
 * @desc file desc
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnumItemBo {

    private int code;

    private String name;

    private String nameZh;
}
