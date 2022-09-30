package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.admin.LanguageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LangMapper extends BaseMapper<LanguageEntity> {

    @Select("select * from t_language")
    List<LanguageEntity> findAll();

    @Select("select * from t_language where lang_code=#{langCode}")
    LanguageEntity findByLangCode(@Param("langCode")String langCode);
}
