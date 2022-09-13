package com.xinbo.chainblock.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.entity.admin.UserNoticeEntity;
import com.xinbo.chainblock.service.NoticeService;
import com.xinbo.chainblock.service.UserNoticeService;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.NoticeVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("NoticeController")
@RequestMapping("/admin/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserNoticeService userNoticeService;


    @Operation(summary = "findPage", description = "会员列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@PathVariable long current, @PathVariable long size) {
        BasePageBo basePageBo = noticeService.findNoticePage(current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

    @Operation(summary = "insert", description = "新增公告")
    @PostMapping("insert")
    public R<Object> insert(@RequestBody NoticeVo vo) {
        NoticeEntity entity = MapperUtil.to(vo, NoticeEntity.class);
        entity.setCreateTime(DateUtil.date());
        boolean isSuccess = noticeService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "updateOrDelete", description = "更新或删除公告")
    @PostMapping("updateOrDelete")
    public R<Object> updateOrDelete(@RequestBody NoticeVo vo) {
        NoticeEntity entity = MapperUtil.to(vo, NoticeEntity.class);
        boolean isSuccess = noticeService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "insertUserNotice", description = "新增用户和公告关系记录")
    @PostMapping("insertUserNotice/{noticeId}")
    public R<Object> insertUserNotice(@PathVariable int noticeId) {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        UserNoticeEntity entity = UserNoticeEntity.builder().noticeId(noticeId)
                .uid(jwtUserBo.getUid()).build();
        boolean isSuccess = userNoticeService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

}
