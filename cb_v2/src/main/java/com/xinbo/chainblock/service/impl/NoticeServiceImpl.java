package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.dto.NoticeDto;
import com.xinbo.chainblock.entity.admin.LangEntity;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.mapper.LangMapper;
import com.xinbo.chainblock.mapper.NoticeMapper;
import com.xinbo.chainblock.service.NoticeService;
import com.xinbo.chainblock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return noticeMapper.insert(entity)>0;
    }

    @Override
    public boolean update(NoticeEntity entity) {
        return noticeMapper.updateById(entity)>0;
    }

    @Override
    public BasePageBo findNoticePage(long current, long size) {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        Page<NoticeDto> page = new Page<>(current, size);
        IPage<NoticeDto> iPage=noticeMapper.findNoticePage(page,jwtUserBo.getUid());
        List<NoticeDto> noticeDtoList=iPage.getRecords().stream()
                .filter(item->1==item.getIsEnable())
                .map(item->{
                    if(item.getIsRead()==null) item.setIsRead(false);
                   LangEntity langEntity = langMapper.selectById(item.getLangId());
                   if(langEntity!=null)
                   item.setLangName(langEntity.getLangName());
                   return item;
                }).collect(Collectors.toList());
        return BasePageBo.builder().total(iPage.getTotal()).records(noticeDtoList).build();
    }
}
