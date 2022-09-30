package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xinbo.chainblock.entity.admin.LanguageEntity;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.mapper.LangMapper;
import com.xinbo.chainblock.service.LangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LangServiceImpl implements LangService {

    @Autowired
    private LangMapper langMapper;


    @Override
    public LanguageEntity findById(int id) {
        return langMapper.selectById(id);
    }

    @Override
    public LanguageEntity findByLangCode(String langCode) {
        return langMapper.findByLangCode(langCode);
    }

    @Override
    public boolean insert(LanguageEntity entity) {
        return langMapper.insert(entity)>0;
    }

    @Override
    public boolean update(LanguageEntity entity) {
        return langMapper.updateById(entity)>0;
    }

    @Override
    public List<LanguageEntity> findAll(LanguageEntity entity) {
        return langMapper.selectList(this.createWrapper(entity));
    }

    @Override
    public boolean delete(int id) {
        return langMapper.deleteById(id)>0;
    }

    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<LanguageEntity> createWrapper(LanguageEntity entity) {
        LambdaQueryWrapper<LanguageEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getLangCode())) {
            wrappers.eq(LanguageEntity::getLangCode, entity.getLangCode());
        }

        return wrappers;
    }
}
