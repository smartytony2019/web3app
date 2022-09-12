package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinbo.chainblock.dto.NoticeDto;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoticeMapper extends BaseMapper<NoticeEntity> {

    @Select("select * from t_notice n left join t_user_notice un on n.id=un.notice_id and n.is_enable=1 and un.uid=#{id}")
    Page<NoticeDto> findNoticePage(Page<NoticeDto> page, @Param("id") int id);

}
