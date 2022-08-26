package com.xinbo.chainblock.service;


import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface ActivityRuleService {

    ActivityRuleEntity findBySn(String sn);
}
