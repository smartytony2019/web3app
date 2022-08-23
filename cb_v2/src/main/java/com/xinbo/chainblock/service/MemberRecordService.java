package com.xinbo.chainblock.service;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.MemberRecordEntity;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface MemberRecordService {
    BasePageBo findPage(MemberRecordEntity entity, long current, long size);
    BasePageBo findPage(MemberRecordEntity entity, long current, long size, Date start, Date end);
}
