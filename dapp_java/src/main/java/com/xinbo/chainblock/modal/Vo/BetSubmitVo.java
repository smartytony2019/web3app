package com.xinbo.chainblock.modal.Vo;

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
