package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVo {
    private Integer id;
    private Integer langId;
    private String title;
    private String content;
    private String operator;
    private Boolean isEnable;
    private Boolean isTop;
    private Integer type;
}
