package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.ExpectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface AgentMapper extends BaseMapper<AgentEntity> {
    @Select("select * from t_agent where uid = #{code} limit 1")
    AgentEntity findByUid(@Param("code") int code);
}