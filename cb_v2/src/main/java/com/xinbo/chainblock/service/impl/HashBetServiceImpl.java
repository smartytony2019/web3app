package com.xinbo.chainblock.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.core.algorithm.AlgorithmCode;
import com.xinbo.chainblock.dto.HashBetDto;
import com.xinbo.chainblock.entity.SystemFlowEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.enums.MemberFlowItemEnum;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.HashBetService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class HashBetServiceImpl extends ServiceImpl<HashBetMapper, HashBetEntity> implements HashBetService {

    @Autowired
    private HashBetMapper hashBetMapper;
    @Autowired
    private HashResultMapper hashResultMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberFlowMapper memberFlowMapper;
    @Autowired
    private SystemFlowMapper systemFlowMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;


    @Override
    public HashBetEntity findById(int id) {
        HashBetEntity entity = hashBetMapper.selectById(id);
        return entity;
    }

    @Override
    public boolean insert(HashBetEntity entity) {
        return hashBetMapper.insert(entity) > 0;
    }

    @Override
    public boolean batchInsert(List<HashBetEntity> list) {
        return hashBetMapper.batchInsert(list) > 0;
    }

    /**
     * 投注入库
     *
     * @param bet
     * @param member
     * @param memberFlow
     * @return
     */
    @Transactional
    @Override
    public boolean bet(HashBetEntity bet, MemberEntity member, MemberFlowEntity memberFlow, HashResultEntity result) {
        // 插入注单
        int rows = hashBetMapper.insert(bet);
        if (rows <= 0) {
            return false;
        }

        // 加减会员金额
        rows = memberMapper.increment(member);
        if (rows <= 0) {
            return false;
        }

        // 添加会员流水
        rows = memberFlowMapper.insert(memberFlow);
        if (rows <= 0) {
            return false;
        }

        // 添加一条未开奖记录
        rows = hashResultMapper.insert(result);
        if (rows <= 0) {
            return false;
        }

        return true;
    }

    @Override
    public HashBetEntity find(HashBetEntity entity) {
        return hashBetMapper.selectOne(this.createWrapper(entity));
    }

    @Override
    public List<HashBetEntity> findList(HashBetEntity entity) {
        return hashBetMapper.selectList(this.createWrapper(entity));
    }

    @Override
    public BasePageBo findPage(HashBetEntity entity, long current, long size) {
        Page<HashBetEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        IPage<HashBetEntity> iPage = hashBetMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), HashBetDto.class)).build();
    }

    @Override
    public BasePageBo findPage(HashBetEntity entity, long current, long size, Date start, Date end) {
        Page<HashBetEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        LambdaQueryWrapper<HashBetEntity> wrapper = this.createWrapper(entity);
        if (!ObjectUtils.isEmpty(start) && !ObjectUtils.isEmpty(end)) {
            wrapper.ge(HashBetEntity::getCreateTime, start).le(HashBetEntity::getCreateTime, end);
        }

        IPage<HashBetEntity> iPage = hashBetMapper.selectPage(page, wrapper);
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), HashBetDto.class)).build();
    }

    @Override
    public HashBetEntity unsettle() {
        return hashBetMapper.unsettle();
    }

    @Transactional
    @Override
    public boolean settle(HashBetEntity bet, HashResultEntity result) {

        //更新注单表
        int rows = hashBetMapper.settle(bet);
        if (rows <= 0) {
            return false;
        }

        //添加开奖结果
        rows = hashResultMapper.updateBySn(result);
        if (rows <= 0) {
            return false;
        }

        //更新会员金额
        MemberEntity memberEntity = memberMapper.selectById(bet.getUid());
        float beforeMoney = memberEntity.getMoney();
        float afterMoney = memberEntity.getMoney() + bet.getPayoutMoney();
        float flowMoney = bet.getPayoutMoney();

        memberEntity.setMoney(bet.getPayoutMoney());
        rows = memberMapper.increment(memberEntity);
        if (rows <= 0) {
            return false;
        }

        // 订单为赢才添加帐变
        if (bet.getFlag() == AlgorithmCode.WIN) {
            //添加帐变
            MemberFlowEntity userFlowEntity = MemberFlowEntity.builder()
                    .sn(bet.getSn())
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .beforeMoney(beforeMoney)
                    .afterMoney(afterMoney)
                    .flowMoney(flowMoney)
                    .item(MemberFlowItemEnum.HASH_BET_SETTLE.getName())
                    .itemCode(MemberFlowItemEnum.HASH_BET_SETTLE.getCode())
                    .itemZh(MemberFlowItemEnum.HASH_BET_SETTLE.getNameZh())
                    .createTime(new Date())
                    .build();
            rows = memberFlowMapper.insert(userFlowEntity);
            if (rows <= 0) {
                return false;
            }
        }


        //更新统计
        StatisticsEntity statisticsEntity = StatisticsEntity.builder()
                .date(DateUtil.format(new Date(), GlobalConst.DATE_YMD))
                .uid(bet.getUid())
                .username(bet.getUsername())
                .betAmount(bet.getMoney())
                .betCount(1)
                .profitAmount(bet.getProfitMoney())
                .payoutAmount(bet.getPayoutMoney())
                .rechargeTrc20Count(0)
                .rechargeTrc20Amount(0F)
                .withdrawTrc20Amount(0F)
                .rechargeTrxCount(0)
                .rechargeTrxAmount(0F)
                .withdrawTrxAmount(0F)
                .commissionAmount(0F)
                .activityAmount(0F)
                .updateTime(new Date())
                .build();

        rows = statisticsMapper.insertOrUpdate(statisticsEntity);
        if (rows <= 0) {
            return false;
        }

        return true;
    }

    @Override
    public HashBetEntity findOrder(String sn) {
        return hashBetMapper.selectOne(this.createWrapper(HashBetEntity.builder().sn(sn).build()));
    }



    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<HashBetEntity> createWrapper(HashBetEntity entity) {
        LambdaQueryWrapper<HashBetEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUid()) && entity.getUid() > 0) {
            wrappers.eq(HashBetEntity::getUid, entity.getUid());
        }
        if (!StringUtils.isEmpty(entity.getSn())) {
            wrappers.eq(HashBetEntity::getSn, entity.getSn());
        }
        if (!StringUtils.isEmpty(entity.getBlockHash())) {
            wrappers.eq(HashBetEntity::getBlockHash, entity.getBlockHash());
        }
        if (!StringUtils.isEmpty(entity.getGameId()) && entity.getGameId() > 0) {
            wrappers.eq(HashBetEntity::getGameId, entity.getGameId());
        }
        if (!StringUtils.isEmpty(entity.getPlayId()) && entity.getPlayId() > 0) {
            wrappers.eq(HashBetEntity::getPlayId, entity.getPlayId());
        }
        if (!StringUtils.isEmpty(entity.getFlag()) && entity.getFlag() > 0) {
            wrappers.eq(HashBetEntity::getFlag, entity.getFlag());
        }
        if (!StringUtils.isEmpty(entity.getStatus()) && entity.getStatus() > 0) {
            wrappers.eq(HashBetEntity::getStatus, entity.getStatus());
        }
        return wrappers;
    }
}
