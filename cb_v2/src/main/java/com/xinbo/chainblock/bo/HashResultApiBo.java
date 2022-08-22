package com.xinbo.chainblock.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author tony
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
public class HashResultApiBo {

    @JSONField(name="sn")
    private String sn;

    @JSONField(name="merchant_id")
    private Integer merchantId;

    @JSONField(name="to_address")
    private String toAddress;

    @JSONField(name="txID")
    private String txID;

    @JSONField(name="block_hash")
    private String blockHash;

    @JSONField(name="block_height")
    private String blockHeight;

    @JSONField(name="open_time")
    private Date openTime;

    @JSONField(name="open_timestamp")
    private Long openTimestamp;

    @JSONField(name="network")
    private String network;

}
