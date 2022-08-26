package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface ActivityRuleItemMapper extends BaseMapper<ActivityRuleItemEntity> {

    @Select("select * from t_activity_rule_item where sn = #{sn}")
    List<ActivityRuleItemEntity> findBySn(@Param("sn") String sn);
}
