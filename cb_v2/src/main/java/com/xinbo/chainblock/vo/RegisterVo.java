package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 6/30/22 6:27 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVo {

    private String username;

    private String withdrawWallet;

    private String pwd;

    private String confirmPwd;

    private String code;

    private String captchaVerification;

}
