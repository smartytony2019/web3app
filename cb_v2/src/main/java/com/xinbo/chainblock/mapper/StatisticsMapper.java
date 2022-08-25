package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.entity.StatisticsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc 统计
 */
@Mapper
public interface StatisticsMapper extends BaseMapper<StatisticsEntity> {

    int insertOrUpdate(@Param("entity") StatisticsEntity entity);

    List<StatisticsEntity> findByUidStr(@Param("date") String date, @Param("childList") List<Integer> childList);

    StatisticsEntity findByUid(@Param("date") String date, @Param("childUid") int childUid);


    @Select("select * from t_statistics where date = #{date}")
    List<StatisticsEntity> findByDate(@Param("date") String date);

    int batchInsert(@Param("list") List<StatisticsEntity> list);


    @Select("select * from t_statistics where date between #{bo.startTimeStr} and #{bo.endTimeStr} and uid = #{uid}")
    List<StatisticsEntity> findList(@Param("bo") DateRangeBo dateRangeBo, @Param("uid") int uid);
}
