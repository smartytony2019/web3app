package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.AgentDomainEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface AgentDomainMapper extends BaseMapper<AgentDomainEntity> {
    @Select("select * from t_agent_domain")
    List<AgentDomainEntity> findAll();
}
