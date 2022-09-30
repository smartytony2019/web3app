package com.xinbo.chainblock.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.entity.admin.UserNoticeEntity;
import com.xinbo.chainblock.service.NoticeService;
import com.xinbo.chainblock.service.UserNoticeService;
import com.xinbo.chainblock.service.UserService;
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

    @Autowired
    private UserService userService;


    @Operation(summary = "findPage", description = "公告列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody NoticeVo vo,@PathVariable long current, @PathVariable long size) {
        NoticeEntity entity = MapperUtil.to(vo, NoticeEntity.class);
        BasePageBo basePageBo = noticeService.findNoticePage(entity,current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

    @Operation(summary = "insert", description = "新增公告")
    @PostMapping("insert")
    public R<Object> insert(@RequestBody NoticeVo vo) {System.out.println(vo);
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        UserEntity userEntity = userService.findById(jwtUserBo.getUid());
        vo.setOperator(userEntity.getUsername());
        NoticeEntity entity = MapperUtil.to(vo, NoticeEntity.class);
        entity.setCreateTime(DateUtil.date());
        boolean isSuccess = noticeService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "update", description = "更新或删除公告")
    @PostMapping("update")
    public R<Object> update(@RequestBody NoticeVo vo) {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        UserEntity userEntity = userService.findById(jwtUserBo.getUid());
        vo.setOperator(userEntity.getUsername());
        NoticeEntity entity = MapperUtil.to(vo, NoticeEntity.class);
        entity.setCreateTime(DateUtil.date());
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

    @Operation(summary = "find", description = "查找单条记录")
    @PostMapping("find/{noticeId}")
    public R<Object> find(@PathVariable int noticeId) {
        NoticeEntity entity = noticeService.find(noticeId);
        return R.builder().code(StatusCode.SUCCESS).data(entity).build();
    }

    @Operation(summary = "delete", description = "删除")
    @PostMapping("delete/{noticeId}")
    public R<Object> delete(@PathVariable int noticeId) {
        boolean isSuccess = noticeService.delete(noticeId);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }
}
