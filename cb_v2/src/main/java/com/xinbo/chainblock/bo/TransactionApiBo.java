package com.xinbo.chainblock.bo;

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
public class TransactionApiBo {

    private boolean result;
    private String txid;
}
