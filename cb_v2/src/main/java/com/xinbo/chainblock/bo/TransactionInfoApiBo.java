package com.xinbo.chainblock.bo;

import lombok.Data;

/**
 * @author tony
 * @date 6/23/22 3:45 下午
 * @desc file desc
 */
@Data
public class TransactionInfoApiBo {

    private String id;
    private int blockNumber;
    private long blockTimeStamp;
}
