package com.xinbo.chainblock.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.BannerDto;
import com.xinbo.chainblock.entity.admin.BannerEntity;
import com.xinbo.chainblock.entity.admin.LanguageEntity;
import com.xinbo.chainblock.service.BannerService;
import com.xinbo.chainblock.service.LangService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BannerVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController("BannerController")
@RequestMapping("/admin/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private LangService langService;

    @Operation(summary = "insert", description = "新增轮播图")
    @PostMapping("insert")
    public R<Object> insert(@RequestBody BannerVo vo){
        BannerEntity entity= MapperUtil.to(vo,BannerEntity.class);
        entity.setCreateTime(DateUtil.date());
        boolean isSuccess=bannerService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "update", description = "更新轮播图")
    @PostMapping("update")
    public R<Object> update(@RequestBody BannerVo vo){
        BannerEntity entity = MapperUtil.to(vo, BannerEntity.class);
        boolean isSuccess = bannerService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "findAll", description = "获取所有轮播图")
    @PostMapping("findAll")
    public R<Object> findAll(@RequestBody BannerVo vo){
        BannerEntity entity = MapperUtil.to(vo, BannerEntity.class);
        List<BannerEntity> bannerEntityList = bannerService.findAll(entity).stream()
                .map(item->{
                    LanguageEntity languageEntity= langService.findByLangCode(item.getLangCode());
                    item.setLangName(languageEntity.getLangName());
                    return item;
                }).collect(Collectors.toList());
        Collections.sort(bannerEntityList,(obj1,obj2)->obj1.getSort()-obj2.getSort());
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(bannerEntityList, BannerDto.class)).build();
    }

    @Operation(summary = "delete", description = "删除轮播图")
    @PostMapping("delete/{id}")
    public R<Object> delete(@PathVariable int id){
        boolean isSuccess = bannerService.delete(id);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "find", description = "查找单条记录")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        BannerEntity entity = bannerService.find(id);
        return R.builder().code(StatusCode.SUCCESS).data(entity).build();
    }
}
