package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetSubmitVo {

    private int gameId;

    private int playId;

    private int playCodeId;

    private String num;

    private float money;

}
