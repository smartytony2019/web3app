package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.service.ActivityService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.ActivityVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("ApiActivityController")
@RequestMapping("api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @JwtIgnore
    @Operation(summary = "insert", description = "插入")
    @PostMapping("insert")
    public R<Object> insert() {
        boolean isSuccess = activityService.insert(ActivityEntity.builder().build());
        return isSuccess ? R.builder().data(StatusCode.SUCCESS).build() : R.builder().data(StatusCode.FAILURE).build();
    }


    @JwtIgnore
    @Operation(summary = "find", description = "插入")
    @PostMapping("find")
    public R<Object> find(@RequestBody ActivityVo vo) {
        ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);
        entity = activityService.find(entity);
        return R.builder().data(StatusCode.SUCCESS).data(entity).build();
    }

    @JwtIgnore
    @Operation(summary = "findPage", description = "获取分页")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody ActivityVo vo, @PathVariable long current, @PathVariable long size) {
        ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);
        BasePageBo basePageBo = activityService.findPage(entity, current, size);
        return R.builder().data(StatusCode.SUCCESS).data(basePageBo).build();
    }





}
