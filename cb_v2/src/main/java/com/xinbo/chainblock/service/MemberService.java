package com.xinbo.chainblock.service;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.utils.R;

import java.util.Date;
import java.util.Map;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface MemberService {

    boolean insert();

    MemberEntity findByUsername(String username);

    MemberEntity findById(int id);

    boolean increment(MemberEntity entity);

    boolean register(MemberEntity entity, int code);

    BasePage findPage(MemberEntity entity, long current, long size, Date start, Date end);

    boolean update(MemberEntity entity);

    String balanceUSDT(int uid);

    Map<String,String> balance(int uid);
}
