package com.xinbo.chainblock.service;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface MemberFlowService {


    BasePage findPage(MemberFlowEntity entity, long current, long size);

    BasePage findPage(MemberFlowEntity entity, long current, long size, Date start, Date end);

}
