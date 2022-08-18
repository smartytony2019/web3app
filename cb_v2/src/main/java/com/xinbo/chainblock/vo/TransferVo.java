package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 8/15/22 5:39 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferVo {

    private int direction;

    private float money;


}
