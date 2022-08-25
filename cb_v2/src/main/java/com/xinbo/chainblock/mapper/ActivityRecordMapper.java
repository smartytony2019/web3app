package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRecordEntity;
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
public interface ActivityRecordMapper extends BaseMapper<ActivityRecordEntity> {

    @Select("select * from t_activity_record where activity_id = #{activityId} and uid = #{uid} order by create_time desc limit 1")
    ActivityRecordEntity find(@Param("activityId") int activityId, @Param("uid") int uid);

    int batchInsert(@Param("list") List<ActivityRecordEntity> list);
}
