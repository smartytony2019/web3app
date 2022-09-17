package com.xinbo.chainblock.controller.admin;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.ActivityDto;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRecordEntity;
import com.xinbo.chainblock.enums.*;
import com.xinbo.chainblock.service.ActivityRecordService;
import com.xinbo.chainblock.service.ActivityService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.ActivityRecordVo;
import com.xinbo.chainblock.vo.ActivityVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminActivityRecordController")
@RequestMapping("/admin/activity/record")
public class ActivityRecordController {


    @Autowired
    private ActivityRecordService activityRecordService;


    @Operation(summary = "findPage", description = "查找分页")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody ActivityRecordVo vo, @PathVariable long current, @PathVariable long size) {
        ActivityRecordEntity entity = MapperUtil.to(vo, ActivityRecordEntity.class);
        BasePageBo basePageBo = activityRecordService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

}
