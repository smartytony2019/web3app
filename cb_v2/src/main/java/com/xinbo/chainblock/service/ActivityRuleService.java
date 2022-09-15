package com.xinbo.chainblock.service;


import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface ActivityRuleService {

    ActivityRuleEntity findBySn(String sn);
    List<ActivityRuleItemEntity> findItemsBySn(String sn);

    boolean operate(ActivityRuleEntity entity, List<ActivityRuleItemEntity> itemEntities);

    ActivityRuleEntity find(ActivityRuleEntity entity);
}
