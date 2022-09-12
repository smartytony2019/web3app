package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerVo {

    private  Integer id;
    private String langCode;
    private String img;
    private String href;
    private String title;
    private String desc;
    private Boolean isEnable;
    private Integer sort;
}
