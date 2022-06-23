package com.xinbo.chainblock.entity.terminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 熊二
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
