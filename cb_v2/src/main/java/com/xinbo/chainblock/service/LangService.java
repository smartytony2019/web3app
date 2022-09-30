package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.admin.LanguageEntity;
import com.xinbo.chainblock.entity.admin.NoticeEntity;

import java.util.List;

public interface LangService {

    LanguageEntity findById(int id);
    LanguageEntity findByLangCode(String langCode);
    boolean insert(LanguageEntity entity);
    boolean update(LanguageEntity entity);
    List<LanguageEntity> findAll(LanguageEntity entity);
    boolean delete(int id);
}
