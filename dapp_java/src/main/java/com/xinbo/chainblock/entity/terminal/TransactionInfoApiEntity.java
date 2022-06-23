package com.xinbo.chainblock.entity.terminal;

import lombok.Data;

/**
 * @author tony
 * @date 6/23/22 3:45 下午
 * @desc file desc
 */
@Data
public class TransactionInfoApiEntity {

    private String id;
    private int blockNumber;
    private long blockTimeStamp;
}
