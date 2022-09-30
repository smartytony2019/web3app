package com.xinbo.chainblock.service;

import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.admin.NoticeEntity;

public interface NoticeService {

    boolean insert(NoticeEntity entity);
    boolean update(NoticeEntity entity);
    BasePageBo findNoticePage(NoticeEntity entity,long current, long size);
    NoticeEntity find(int id);
    boolean delete(int id);
}
