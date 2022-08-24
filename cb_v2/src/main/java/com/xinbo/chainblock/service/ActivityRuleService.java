package com.xinbo.chainblock.service;


import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface ActivityRuleService {

    ActivityRuleEntity findByActivityId(int activityId);
}
