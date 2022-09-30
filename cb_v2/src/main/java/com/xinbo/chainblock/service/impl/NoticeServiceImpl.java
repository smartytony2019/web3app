package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.dto.NoticeDto;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.admin.LanguageEntity;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.mapper.LangMapper;
import com.xinbo.chainblock.mapper.NoticeMapper;
import com.xinbo.chainblock.service.NoticeService;
import com.xinbo.chainblock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private LangMapper langMapper;

    @Override
    public boolean insert(NoticeEntity entity) {
        return noticeMapper.insert(entity) > 0;
    }

    @Override
    public boolean update(NoticeEntity entity) {
        return noticeMapper.updateById(entity) > 0;
    }

    @Override
    public BasePageBo findNoticePage(NoticeEntity entity,long current, long size) {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        Page<NoticeEntity> page = new Page<>(current, size);
        IPage<NoticeEntity> iPage = noticeMapper.selectPage(page,this.createWrapper(entity));
        List<NoticeEntity> noticeDtoList = iPage.getRecords();
        return BasePageBo.builder().total(iPage.getTotal()).records(noticeDtoList).build();
    }

    @Override
    public NoticeEntity find(int id) {
        return noticeMapper.selectById(id);
    }

    @Override
    public boolean delete(int id) {
        return noticeMapper.deleteById(id)>0;
    }

    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<NoticeEntity> createWrapper(NoticeEntity entity) {
        LambdaQueryWrapper<NoticeEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(NoticeEntity::getId, entity.getId());
        }

        if (!StringUtils.isEmpty(entity.getLangCode())) {
            wrappers.eq(NoticeEntity::getLangCode, entity.getLangCode());
        }

        if (!StringUtils.isEmpty(entity.getType()) && entity.getType() > 0) {
            wrappers.eq(NoticeEntity::getType, entity.getType());
        }

        return wrappers;
    }
}
