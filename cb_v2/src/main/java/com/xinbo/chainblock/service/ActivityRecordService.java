package com.xinbo.chainblock.service;


import com.xinbo.chainblock.entity.activity.ActivityRecordEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface ActivityRecordService {

    ActivityRecordEntity find(int uid);

    ActivityRecordEntity find(int activityId, int uid);

    boolean batchInsert(List<ActivityRecordEntity> list);
}
