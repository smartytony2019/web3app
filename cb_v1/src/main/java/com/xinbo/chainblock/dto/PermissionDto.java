package com.xinbo.chainblock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDto implements Serializable {

    private static final long serialVersionUID = -4559267810907997111L;

    private String path;
    private String component;
    private String redirect;
    private String name;
    private Meta meta;

    @Data
    @Builder
    public static class Meta {
        private String title;
        private String icon;
        private Boolean alwaysShow;
        private Boolean noCache;
    }

    List<PermissionDto> children;
}
