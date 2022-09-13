package com.xinbo.chainblock.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 8/23/22 6:31 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVo extends ActivityRuleVo {

    private Integer id;

    /**
     * 类目id
     */
    private Integer cateCode;


    /**
     * 编号
     */
    private String sn;


    /**
     * 活动名称
     */
    private String title;


    /**
     * 图片
     */
    private String img;


    /**
     * 内容
     */
    private String content;


    /**
     * 序号
     */
    private Integer sorted;


    /**
     * 限制项(1:首充, 2:注册送, 10:其它)
     */
    private Integer type;


    /**
     * 语言
     */
    private String language;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date finishTime;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 限制项(1:首充, 2:注册送, 10:其它)
     */
    private Boolean isEnable;

    private List<ActivityRuleItemVo> items;
}
