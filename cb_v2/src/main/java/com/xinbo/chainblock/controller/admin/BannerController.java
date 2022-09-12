package com.xinbo.chainblock.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.BannerDto;
import com.xinbo.chainblock.entity.admin.BannerEntity;
import com.xinbo.chainblock.entity.admin.LangEntity;
import com.xinbo.chainblock.service.BannerService;
import com.xinbo.chainblock.service.LangService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BannerVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("BannerController")
@RequestMapping("/admin/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private LangService langService;

    @Operation(summary = "insertBanner", description = "新增轮播图")
    @PostMapping("insert")
    public R<Object> insertBanner(@RequestBody BannerVo vo){
        BannerEntity entity= MapperUtil.to(vo,BannerEntity.class);
        entity.setCreateTime(DateUtil.date());
        boolean isSuccess=bannerService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "updateOrDeleteBanner", description = "更新或删除轮播图")
    @PostMapping("updateOrDelete")
    public R<Object> updateOrDeleteBanner(@RequestBody BannerVo vo){
        BannerEntity entity = MapperUtil.to(vo, BannerEntity.class);
        boolean isSuccess = bannerService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "findAllBanner", description = "获取所有轮播图")
    @PostMapping("findAll")
    public R<Object> findAllBanner(){
        List<BannerEntity> bannerEntityList = bannerService.findall().stream().filter(item -> true == item.getIsEnable())
                .map(item->{
                    LangEntity langEntity=langService.find(item.getLangId());
                    item.setLangName(langEntity.getLangName());
                    return item;
                })
                .collect(Collectors.toList());
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(bannerEntityList, BannerDto.class)).build();
    }

}
