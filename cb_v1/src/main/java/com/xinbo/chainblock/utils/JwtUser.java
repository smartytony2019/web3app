package com.xinbo.chainblock.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 熊二
 * @date 2021/2/5 16:51
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUser implements Serializable {

    private Integer uid;

    private String username;

    private String salt;

    /**
     * 权限
     */
    private List<String> authority;
}
