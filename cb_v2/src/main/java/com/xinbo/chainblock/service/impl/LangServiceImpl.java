package com.xinbo.chainblock.service.impl;

import com.xinbo.chainblock.entity.admin.LangEntity;
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
    public LangEntity find(int id) {
        return langMapper.selectById(id);
    }

    @Override
    public boolean insert(LangEntity entity) {
        return langMapper.insert(entity)>0;
    }

    @Override
    public boolean update(LangEntity entity) {
        return langMapper.updateById(entity)>0;
    }

    @Override
    public List<LangEntity> findAll() {
        return langMapper.findAll();
    }
}
