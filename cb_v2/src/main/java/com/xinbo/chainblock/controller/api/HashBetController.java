package com.xinbo.chainblock.controller.api;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.consts.BetStatus;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.dto.HashBetDto;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOddsEntity;
import com.xinbo.chainblock.entity.hash.HashPlayEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.enums.MemberFlowItemEnum;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.CommonUtils;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetSubmitVo;
import com.xinbo.chainblock.vo.BetVo;
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

@RestController("ApiHashBetController")
@RequestMapping("/api/hashBet")
public class HashBetController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private HashResultService hashResultService;

    @Autowired
    private HashPlayService hashPlayService;

    @Autowired
    private HashOddsService hashOddsService;

    @Autowired
    private HashBetService hashBetService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TrxApi trxApi;

    @JwtIgnore
    @Operation(summary = "submit")
    @PostMapping("submit")
    public R<Object> submit(@RequestBody @Valid BetSubmitVo vo) throws BusinessException {
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
                    .username("jackB2")
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

            WalletEntity walletEntity = walletService.findByUid(memberEntity.getId());
            if (ObjectUtils.isEmpty(walletEntity) || walletEntity.getId() <= 0) {
                throw new BusinessException(0, "数字钱包不存在");
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
            // Step 2: 构造数据
            // Step 2.1: 注单表
            String sn = IdUtil.getSnowflake().nextIdStr();
            HashBetEntity bet = HashBetEntity.builder()
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
                    .algorithm(playEntity.getAlgorithm())
                    .build();


            // Step 2.2: 会员表
            MemberEntity member = MemberEntity.builder()
                    .money(moneyAmount)
                    .id(memberEntity.getId())
                    .version(memberEntity.getVersion())
                    .build();


            // Step 2.3: 会员流水表
            MemberFlowEntity memberFlow = MemberFlowEntity.builder()
                    .sn(sn)
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .beforeMoney(memberEntity.getMoney())
                    .afterMoney(memberEntity.getMoney() + moneyAmount)
                    .flowMoney(moneyAmount * -1)
                    .item(MemberFlowItemEnum.HASH_BET.getName())
                    .itemCode(MemberFlowItemEnum.HASH_BET.getCode())
                    .itemZh(MemberFlowItemEnum.HASH_BET.getNameZh())
                    .createTime(new Date())
                    .build();


            HashResultEntity result = HashResultEntity.builder()
                    .sn(bet.getSn())
                    .toAddress(walletEntity.getAddressBase58())
                    .gameId(playEntity.getGameId())
                    .playId(playEntity.getId())
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .build();

            boolean isSuccess = trxApi.resultOpen(sn, walletEntity.getAddressBase58());
            if (!isSuccess) {
                throw new BusinessException(1000, "终端生成开奖结果失败");
            }

            isSuccess = hashBetService.bet(bet, member, memberFlow, result);
            if (!isSuccess) {
                throw new BusinessException(1000, "数据库操作失败");
            }

            return R.builder().code(StatusCode.SUCCESS).data(sn).build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return R.builder().code(StatusCode.FAILURE).data(ex.getMessage()).build();
        }
    }


    @JwtIgnore
    @Operation(summary = "findOrder", description = "查询订单")
    @GetMapping("findOrder/{sn}")
    public R<Object> findOrder(@PathVariable String sn) {
        HashBetEntity order = hashBetService.findOrder(sn);
        if (!ObjectUtils.isEmpty(order) && order.getStatus() == BetStatus.SETTLE) {
            // 说明已开奖
//            hashResultService.find

        }
        return R.builder().code(StatusCode.SUCCESS).data(order.getFlag()).build();
    }


    @JwtIgnore
    @Operation(summary = "find", description = "获取注单")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable Integer id) {
        HashBetEntity hashBetEntity = hashBetService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(hashBetEntity, HashBetDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findList", description = "获取注单列表")
    @PostMapping("findList")
    public R<Object> findList(@RequestBody BetVo vo) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        List<HashBetEntity> lotteryBetDtoList = hashBetService.findList(entity);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(lotteryBetDtoList, HashBetDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findPage", description = "获取注单")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody BetVo vo, @PathVariable long current, @PathVariable long size) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        DateRangeBo dateRangeBo = CommonUtils.toConvertDate(vo.getType());
        BasePageBo basePageBo = hashBetService.findPage(entity, current, size, dateRangeBo.getStartTime(), dateRangeBo.getEndTime());
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

}
