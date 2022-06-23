package com.xinbo.chainblock.entity.terminal;

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
        private String transaction_id;
        private TokenInfo token_info;

        @lombok.Data
        public class TokenInfo {
            private String symbol;
            private String address;
            private int decimals;
            private String name;
        }
    }

}
