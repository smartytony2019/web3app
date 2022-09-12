package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.admin.LanguageEntity;

import java.util.List;

public interface LangService {

    LanguageEntity find(int id);
    boolean insert(LanguageEntity entity);
    boolean update(LanguageEntity entity);
    List<LanguageEntity> findAll();
}
