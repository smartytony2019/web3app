package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface FinanceMapper extends BaseMapper<FinanceEntity> {

    int batchInsert(@Param("list") List<FinanceEntity> list);

    /**
     * 获取未入帐
     *
     * @return
     */
    @Select("select * from t_finance where is_account = 0 order by block_timestamp asc limit 10")
    List<FinanceEntity> findUnaccounted();

}
