package com.xinbo.chainblock.service;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.MemberRecordEntity;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface MemberFlowService {


    BasePageBo findPage(MemberFlowEntity entity, long current, long size);

    BasePageBo findPage(MemberFlowEntity entity, long current, long size, Date start, Date end);

    boolean insert(MemberFlowEntity entity);

    boolean delete(Integer id);

    boolean update(MemberFlowEntity entity);
}
