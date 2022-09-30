package com.xinbo.chainblock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    /**
     * 主键
     */
    private Integer id;


    /**
     * 姓名
     */
    private String username;


    /**
     * 头像
     */
    private String avatar;

    private Boolean isDelete;
}
