package com.xinbo.chainblock.service;


import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface StatisticsService {

    List<StatisticsEntity> findByUidStr(String date, List<Integer> childList);

    StatisticsEntity findByUid(String date, int childUid);

    List<StatisticsEntity> findList(DateRangeBo dateRangeBo, int uid);

    List<StatisticsEntity> findByDate(String date);

    StatisticsEntity findTotal(DateRangeBo dateRangeBo, int uid);

    BasePageBo findPage(DateRangeBo dateRangeBo, int uid, long current, long size);
}
