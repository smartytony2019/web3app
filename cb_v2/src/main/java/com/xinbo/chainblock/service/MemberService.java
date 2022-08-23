package com.xinbo.chainblock.service;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.MemberEntity;

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

    BasePageBo findPage(MemberEntity entity, long current, long size);

    boolean update(MemberEntity entity);

    /**
     * 会员信息
     * @param uid
     * @return
     */
    MemberEntity info(int uid);
}
