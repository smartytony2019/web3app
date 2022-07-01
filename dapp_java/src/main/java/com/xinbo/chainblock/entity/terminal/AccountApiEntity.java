package com.xinbo.chainblock.entity.terminal;

import lombok.Data;

/**
 * @author tony
 * @date 6/23/22 2:57 下午
 * @desc file desc
 */
@Data
public class AccountApiEntity {

    private String privateKey;

    private String publicKey;

    private Address address;


    @Data
    class Address {
        private String base58;
        private String hex;
    }

}
