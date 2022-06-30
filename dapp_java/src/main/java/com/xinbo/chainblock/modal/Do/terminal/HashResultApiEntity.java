package com.xinbo.chainblock.modal.Do.terminal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author tony
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
public class HashResultApiEntity {

    @JSONField(name="game_id")
    private Integer gameId;

    private String num;

    private String txID;

    @JSONField(name="block_hash")
    private String blockHash;

    @JSONField(name="block_height")
    private String blockHeight;

    @JSONField(name="open_time")
    private Date openTime;

    @JSONField(name="open_timestamp")
    private Long openTimestamp;

    private String network;

}
