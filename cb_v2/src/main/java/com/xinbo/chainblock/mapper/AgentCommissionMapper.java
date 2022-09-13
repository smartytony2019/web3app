package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.entity.AgentCommissionEntity;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface AgentCommissionMapper extends BaseMapper<AgentCommissionEntity> {
    int insertOrUpdate(@Param("list") List<AgentCommissionEntity> list);

    @Select("select IFNULL(sum(total_commission), 0) from t_agent_commission where uid = #{uid} and is_account = 0")
    float findAvailableCommission(@Param("uid") int uid);

    @Select("select IFNULL(sum(total_commission), 0) from t_agent_commission where uid = #{uid} and is_account = 1")
    float findCommissionTotal(@Param("uid") int uid);

    @Update("update t_agent_commission set is_account = 1 where uid = #{uid}")
    int accounted(@Param("uid") int uid);

    @Select("select * from t_agent_commission where uid = #{uid} and date = #{date} limit 1")
    AgentCommissionEntity find(@Param("uid") int uid, @Param("date") String date);

    AgentCommissionEntity findTotal(@Param("bo") DateRangeBo dateRangeBo, @Param("uid") int uid);
}
