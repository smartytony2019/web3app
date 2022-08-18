package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.MemberEntity;
import org.apache.ibatis.annotations.*;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {

    @Update("update t_member set money = money + #{entity.money}, version = version + 1 where id = #{entity.id} and version = #{entity.version}")
    int increment(@Param("entity") MemberEntity entity);

    @Select("select `username`,`money`,`withdraw_wallet`,`reg_ip`,`reg_time`,`last_login_ip`,`last_login_time` from t_member where uid = #{uid} limit 1")
    MemberEntity info(@Param("uid") int uid);


//    @Insert("insert into t_user(`username`,`pwd`,`salt`,`create_time`) values ('#{entity.username}','#{entity.pwd}','#{entity.salt}','#{entity.createTime}')")
//    @Options(useGeneratedKeys = true,keyProperty = "id", keyColumn = "id")
//    int register(@Param("entity") UserEntity entity);
}
