package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.LotteryBetEntity;
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
public interface LotteryBetMapper extends BaseMapper<LotteryBetEntity> {

    @Select("select * from t_lottery_bet where num = #{num} and status = 0 limit #{size}")
    List<LotteryBetEntity> unsettle(@Param("num") String num, @Param("size") int size);

    @Update("update t_lottery_bet set hash_result=#{entity.hashResult},profit_money=#{entity.profitMoney},payout_money=#{entity.payoutMoney},update_time=#{entity.updateTime} where id = #{entity.id}")
    int settle(@Param("entity") LotteryBetEntity entity);
}
