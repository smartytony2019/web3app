package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionVo {
    private Integer id;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private String nameDefault;
    private String title;
    private String icon;
    private Integer code;
    private Integer nodeType;
    private Integer sort;
    private Integer level;
    private Integer parentId;
    private String parentPath;
    private Boolean isDelete;
}
