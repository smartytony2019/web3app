package com.xinbo.chainblock.controller.api;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.BetStatus;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.dto.HashResultDto;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOddsEntity;
import com.xinbo.chainblock.entity.hash.HashPlayEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.enums.ItemEnum;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.CommonUtils;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetSubmitVo;
import com.xinbo.chainblock.vo.BetVo;
import com.xinbo.chainblock.vo.HashResultVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController("ApiHashResultController")
@RequestMapping("/api/hashResult")
public class HashResultController {


    @Autowired
    private HashResultService hashResultService;


    @JwtIgnore
    @Operation(summary = "findRecord", description = "查询开奖记录")
    @PostMapping("findRecord")
    public R<Object> findRecord(@RequestBody HashResultVo vo) {
        HashResultEntity entity = MapperUtil.to(vo, HashResultEntity.class);
        entity.setUid(3);
        List<HashResultEntity> list = hashResultService.findRecord(entity);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashResultDto.class)).build();
    }

}
