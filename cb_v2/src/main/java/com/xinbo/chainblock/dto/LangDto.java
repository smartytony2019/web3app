package com.xinbo.chainblock.dto;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LangDto {
    private Integer id;
    private String langCode;
    private String langName;
    private DateTime createTime;
}
