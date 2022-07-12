package com.xinbo.chainblock.service;

import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.LotteryCategoryEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface LotteryCategoryService {


    LotteryCategoryEntity findById(int id);


    List<LotteryCategoryEntity> findAll();
}
