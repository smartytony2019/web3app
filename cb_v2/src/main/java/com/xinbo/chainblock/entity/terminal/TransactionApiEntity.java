package com.xinbo.chainblock.entity.terminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 6/23/22 3:45 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionApiEntity {

    private boolean result;
    private String txid;
}
