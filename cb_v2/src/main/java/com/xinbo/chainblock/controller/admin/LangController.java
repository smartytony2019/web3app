package com.xinbo.chainblock.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.LangDto;
import com.xinbo.chainblock.entity.admin.LanguageEntity;
import com.xinbo.chainblock.service.LangService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.LangVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("LangController")
@RequestMapping("/admin/lang")
public class LangController {

    @Autowired
    private LangService langService;

    @Operation(summary = "insert", description = "新增语言")
    @PostMapping("insert")
    public R<Object> insert(@RequestBody LangVo vo) {
        LanguageEntity entity = MapperUtil.to(vo, LanguageEntity.class);
        entity.setCreateTime(DateUtil.date());
        boolean isSuccess = langService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "update", description = "更新语言")
    @PostMapping("update")
    public R<Object> update(@RequestBody LangVo vo) {
        LanguageEntity entity = MapperUtil.to(vo, LanguageEntity.class);
        boolean isSuccess = langService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "findAll", description = "查询所有语言")
    @PostMapping("findAll")
    public R<Object> findAll() {
        List<LanguageEntity> langEntityList = langService.findAll();
        List<LangDto> langDtoList = MapperUtil.many(langEntityList, LangDto.class);
        return R.builder().code(StatusCode.SUCCESS).data(langDtoList).build();
    }


}