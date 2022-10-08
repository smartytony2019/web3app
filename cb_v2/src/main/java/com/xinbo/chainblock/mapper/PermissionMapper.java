package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinbo.chainblock.dto.NoticeDto;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    List<PermissionEntity> findByIds(List<Integer> list);

    List<PermissionEntity> findAll();

    @Select("select * from t_permission where parent_id=#{parentId}")
    Page<PermissionEntity> selectByParentId(Page<PermissionEntity> page,@Param("parentId") int parentId);

}
