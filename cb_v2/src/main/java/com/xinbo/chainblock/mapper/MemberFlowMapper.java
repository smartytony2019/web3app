package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface MemberFlowMapper extends BaseMapper<MemberFlowEntity> {

    int batchInsert(List<MemberFlowEntity> list);
}
