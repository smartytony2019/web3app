package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.admin.LangEntity;

import java.util.List;

public interface LangService {

    LangEntity find(int id);
    boolean insert(LangEntity entity);
    boolean update(LangEntity entity);
    List<LangEntity> findAll();
}
