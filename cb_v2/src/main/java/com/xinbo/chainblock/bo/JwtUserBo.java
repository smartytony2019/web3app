package com.xinbo.chainblock.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tony
 * @date 2021/2/5 16:51
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserBo implements Serializable {

    private Integer uid;

    private String username;

    private String roleType;

    private String salt;

    private String language;

    private String device;

}
