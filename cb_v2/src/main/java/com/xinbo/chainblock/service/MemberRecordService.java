package com.xinbo.chainblock.service;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberRecordEntity;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface MemberRecordService {
    BasePage findPage(MemberRecordEntity entity, long current, long size);
    BasePage findPage(MemberRecordEntity entity, long current, long size, Date start, Date end);
}
