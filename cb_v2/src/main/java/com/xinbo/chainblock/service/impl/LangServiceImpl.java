package com.xinbo.chainblock.service.impl;

import com.xinbo.chainblock.entity.admin.LanguageEntity;
import com.xinbo.chainblock.mapper.LangMapper;
import com.xinbo.chainblock.service.LangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LangServiceImpl implements LangService {

    @Autowired
    private LangMapper langMapper;


    @Override
    public LanguageEntity find(int id) {
        return langMapper.selectById(id);
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
    public List<LanguageEntity> findAll() {
        return langMapper.findAll();
    }
}
