package com.xinbo.chainblock.service;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface MemberService {

    boolean insert();


    MemberEntity findById(int id);

    boolean increment(MemberEntity entity);

    boolean register(MemberEntity entity, int code);

    BasePage findPage(MemberEntity entity, long current, long size);
}
