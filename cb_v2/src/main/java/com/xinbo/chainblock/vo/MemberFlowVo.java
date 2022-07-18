package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tony
 * @date 6/30/22 6:27 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberFlowVo {

    private String username;

    private int item;

    private Date start;

    private Date end;

}
