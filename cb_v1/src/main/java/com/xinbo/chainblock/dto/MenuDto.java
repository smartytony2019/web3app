package com.xinbo.chainblock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto implements Serializable {
    private String path;
    private String component;
    private String redirect;
    private String name;
    private String meta;

    @Data
    class Meta {
        private String title;
        private String icon;
        private String alwaysShow;
        private String noCache;
    }
}
