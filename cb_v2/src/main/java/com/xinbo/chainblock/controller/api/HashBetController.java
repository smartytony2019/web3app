package com.xinbo.chainblock.controller.api;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOddsEntity;
import com.xinbo.chainblock.entity.hash.HashPlayEntity;
import com.xinbo.chainblock.enums.ItemEnum;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.CommonUtils;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetSubmitVo;
import com.xinbo.chainblock.vo.BetVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController("ApiHashBetController")
@RequestMapping("/api/hashBet")
public class HashBetController {

    @Autowired
    private GameService gameService;

    @Autowired
    private HashPlayService hashPlayService;

    @Autowired
    private HashOddsService hashOddsService;

    @Autowired
    private HashBetService hashBetService;

    @Autowired
    private MemberService memberService;

    @JwtIgnore
    @Operation(summary = "submit")
    @PostMapping("submit")
    public R<Object> submit(@RequestBody @Valid BetSubmitVo vo) {
        R<Object> r = R.builder().code(StatusCode.FAILURE).build();

        try {
            //********************************************************************************
            //Step 1: 判断数据是否合法
            if (StringUtils.isEmpty(vo.getPlayId()) || vo.getPlayId() <= 0) {
                throw new BusinessException(0, "数据不合法");
            }

            if (StringUtils.isEmpty(vo.getCodes())) {
                throw new BusinessException(0, "数据不合法");
            }

            if (StringUtils.isEmpty(vo.getMoney()) || vo.getMoney() <= 0) {
                throw new BusinessException(0, "数据不合法");
            }

            MemberEntity jwt = MemberEntity.builder()
                    .username("jackC")
                    .id(3)
                    .version(1)
                    .build();

            HashPlayEntity playEntity = hashPlayService.findById(vo.getPlayId());
            if (ObjectUtils.isEmpty(playEntity) || playEntity.getId() <= 0) {
                throw new BusinessException(0, "玩法错误");
            }

            List<String> codes = StrUtil.split(vo.getCodes(), ",");
            List<HashOddsEntity> odds = hashOddsService.findByCode(codes);
            if (CollectionUtils.isEmpty(odds) || odds.size() != codes.size()) {
                throw new BusinessException(0, "赔率错误");
            }

            MemberEntity memberEntity = memberService.findById(jwt.getId());
            if (ObjectUtils.isEmpty(memberEntity) || memberEntity.getId() <= 0) {
                throw new BusinessException(0, "会员不存在");
            }

            //投注数量
            int betAmount = codes.size();

            //投注金额
            float moneyAmount = vo.getMoney() * betAmount;

            //判断帐上金额是否足够
            if (moneyAmount > memberEntity.getMoney()) {
                throw new BusinessException(0, "金额不足");
            }

            //********************************************************************************
            //Step 2: 构造数据
            String sn = IdUtil.getSnowflake().nextIdStr();
            HashBetEntity entity = HashBetEntity.builder()
                    .sn(sn)
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .cateId(playEntity.getCateId())
                    .cateName(playEntity.getCateName())
                    .cateNameZh(playEntity.getCateNameZh())
                    .gameId(playEntity.getGameId())
                    .gameName(playEntity.getGameName())
                    .gameNameZh(playEntity.getGameNameZh())
                    .playId(playEntity.getId())
                    .playName(playEntity.getName())
                    .playNameZh(playEntity.getNameZh())
                    .content(vo.getCodes())
                    .contentZh(odds.get(0).getNameZh())
                    .odds(odds.get(0).getOdds())
                    .betAmount(betAmount)
                    .money(vo.getMoney())
                    .moneyAmount(moneyAmount)
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
//            boolean isSuccess = hashBetService.insert(entity);


            MemberEntity member = MemberEntity.builder()
                    .money(moneyAmount)
                    .id(memberEntity.getId())
                    .version(memberEntity.getVersion())
                    .build();


            MemberFlowEntity memberFlow = MemberFlowEntity.builder()
                    .sn(sn)
                    .username(memberEntity.getUsername())
                    .beforeMoney(memberEntity.getMoney())
                    .afterMoney(memberEntity.getMoney()+moneyAmount)
                    .flowMoney(moneyAmount)
                    .item(ItemEnum.HASH_BET.getCode())
                    .itemZh(ItemEnum.HASH_BET.getMsg())
                    .createTime(new Date())
                    .build();

            boolean isSuccess = hashBetService.bet(entity, member, memberFlow);

            if (isSuccess) {
                r.setCode(StatusCode.SUCCESS);
                r.setData(sn);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }


    @Operation(summary = "find", description = "获取注单")
    @PostMapping("find")
    public R<Object> find(@RequestBody BetVo vo) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        List<HashBetEntity> lotteryBetDtoList = hashBetService.find(entity);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(lotteryBetDtoList, HashBetEntity.class)).build();
    }


    @Operation(summary = "findPage", description = "获取注单")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody BetVo vo, @PathVariable long current, @PathVariable long size) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        BasePage basePage = hashBetService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePage).build();
    }


    @Operation(summary = "test", description = "获取注单")
    @GetMapping("test/{language}/{key}")
    public R<Object> test(@PathVariable("language") String language, @PathVariable("key") String key) {
        String values = CommonUtils.translate(language, key);
        return R.builder().code(StatusCode.SUCCESS).data(values).build();
    }

}
