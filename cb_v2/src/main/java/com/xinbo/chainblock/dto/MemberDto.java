package com.xinbo.chainblock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:21 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

    private Integer id;

    private String username;

    @JsonIgnore
    private String pwd;

    private Float money;

    private String salt;

    private Integer version;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date start;

    private Date end;

}
