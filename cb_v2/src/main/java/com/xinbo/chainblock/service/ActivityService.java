package com.xinbo.chainblock.service;


import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface ActivityService {

    boolean insert(ActivityEntity entity);

    List<ActivityEntity> findAll();

    List<ActivityEntity> findAll(int skip, int size);

    ActivityEntity findById(int id);

    ActivityEntity find(ActivityEntity entity);

    BasePageBo findPage(ActivityEntity entity, long current, long size);

    ActivityEntity findByType(int type);

    boolean create(ActivityEntity entity);

    boolean update(ActivityEntity entity);

    boolean delete(String sn);
}
