package com.xinbo.chainblock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer sort;
    private Integer id;
    private Meta meta;
    private List<PermissionEntity> allPermissions;
    private List<PermissionEntity> ownPermissions;

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
