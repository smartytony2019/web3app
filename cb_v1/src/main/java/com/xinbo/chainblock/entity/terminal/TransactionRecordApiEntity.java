package com.xinbo.chainblock.entity.terminal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author tony
 * @date 6/23/22 4:14 下午
 * @desc file desc
 */
@Data
public class TransactionRecordApiEntity {

    private List<Data> data;

    private String success;

    private Object meta;


    @lombok.Data
    public class Data {
        @JSONField(name="transaction_id")
        private String transactionId;


        @JSONField(name="token_info")
        private TokenInfo tokenInfo;


        @JSONField(name="block_timestamp")
        private Long blockTimestamp;
        private String from;
        private String to;
        private String type;
        private String value;

        @lombok.Data
        public class TokenInfo {
            private String symbol;
            private String address;
            private int decimals;
            private String name;
        }
    }

}
