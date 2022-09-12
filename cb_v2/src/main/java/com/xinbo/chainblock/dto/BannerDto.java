package com.xinbo.chainblock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerDto {
    private Integer id;
    private String img;
    private String href;
    private String title;
    private String desc;
    private Integer sort;
    private String langName;
}
