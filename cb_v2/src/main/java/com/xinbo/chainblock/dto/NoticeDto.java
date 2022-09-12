package com.xinbo.chainblock.dto;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private Integer id;
    private Integer langId;
    private String langName;
    private String title;
    private String content;
    private String operator;
    private Integer isEnable;
    private Integer isTop;
    private Integer type;
    private Date createTime;
    private Boolean isRead;
}
