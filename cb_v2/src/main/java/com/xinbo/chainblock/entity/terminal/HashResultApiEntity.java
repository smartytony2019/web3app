package com.xinbo.chainblock.entity.terminal;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
