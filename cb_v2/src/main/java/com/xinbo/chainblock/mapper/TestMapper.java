package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.activity.ActivityRecordEntity;
import com.xinbo.chainblock.entity.activity.TestEntity;

public interface TestMapper extends BaseMapper<TestEntity> {

    int insert(TestEntity testEntity);
}
