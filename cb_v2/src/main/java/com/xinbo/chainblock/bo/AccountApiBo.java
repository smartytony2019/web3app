package com.xinbo.chainblock.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 6/23/22 2:57 下午
 * @desc file desc
 */
@Data
public class AccountApiBo {

    private String privateKey;

    private String publicKey;

    private Address address;


    @Data
    public class Address {
        private String base58;
        private String hex;
    }
}
