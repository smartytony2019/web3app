package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberChangePasswordVo {

    private Integer type;

    private String oldPwd;

    private String newPwd;

    private String confirmPwd;

}
