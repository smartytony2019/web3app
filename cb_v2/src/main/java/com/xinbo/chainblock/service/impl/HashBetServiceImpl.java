package com.xinbo.chainblock.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.HashBetDto;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.enums.ItemEnum;
import com.xinbo.chainblock.mapper.HashBetMapper;
import com.xinbo.chainblock.mapper.StatisticsMapper;
import com.xinbo.chainblock.mapper.MemberFlowMapper;
import com.xinbo.chainblock.mapper.MemberMapper;
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
    private MemberMapper memberMapper;
    @Autowired
    private MemberFlowMapper memberFlowMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;


    @Override
    public HashBetDto findById(int id) {
        HashBetEntity entity = hashBetMapper.selectById(id);
        return MapperUtil.to(entity, HashBetDto.class);
    }

    @Override
    public boolean insert(HashBetEntity entity) {
        return hashBetMapper.insert(entity) > 0;
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
    public boolean bet(HashBetEntity bet, MemberEntity member, MemberFlowEntity memberFlow) {
        int rows = hashBetMapper.insert(bet);
        if (rows <= 0) {
            return false;
        }

        rows = memberMapper.increment(member);
        if (rows <= 0) {
            return false;
        }

        rows = memberFlowMapper.insert(memberFlow);
        if (rows <= 0) {
            return false;
        }

        return true;
    }

    @Override
    public List<HashBetEntity> find(HashBetEntity entity) {
        return hashBetMapper.selectList(this.createWrapper(entity));
    }

    @Override
    public BasePage findPage(HashBetEntity entity, long current, long size) {
        Page<HashBetEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        IPage<HashBetEntity> iPage = hashBetMapper.selectPage(page, this.createWrapper(entity));
        return BasePage.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), HashBetDto.class)).build();
    }

    @Override
    public BasePage findPage(HashBetEntity entity, long current, long size, Date start, Date end) {
        Page<HashBetEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        LambdaQueryWrapper<HashBetEntity> wrapper = this.createWrapper(entity);
        if (!ObjectUtils.isEmpty(start) && !ObjectUtils.isEmpty(end)) {
            wrapper.ge(HashBetEntity::getCreateTime, start).le(HashBetEntity::getCreateTime, end);
        }

        IPage<HashBetEntity> iPage = hashBetMapper.selectPage(page, wrapper);
        return BasePage.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), HashBetDto.class)).build();
    }

    @Override
    public List<HashBetEntity> unsettle(String num, int size) {
        return hashBetMapper.unsettle(num, size);
    }

    @Transactional
    @Override
    public boolean settle(List<HashBetEntity> list) {
        for (HashBetEntity bet : list) {
            //更新注单表
            bet.setStatus(1);
            int rows = hashBetMapper.settle(bet);
            if (rows <= 0) {
                throw new RuntimeException("settle: update lottery bet exception");
            }

            //更新会员金额
            MemberEntity memberEntity = memberMapper.selectById(bet.getUid());
            float beforeMoney = memberEntity.getMoney();
            float afterMoney = memberEntity.getMoney() + bet.getPayoutMoney();
            float flowMoney = bet.getPayoutMoney();

            memberEntity.setMoney(bet.getPayoutMoney());
            rows = memberMapper.increment(memberEntity);
            if (rows <= 0) {
                throw new RuntimeException("settle: update user exception");
            }

            //添加帐变
            MemberFlowEntity userFlowEntity = MemberFlowEntity.builder()
                    .username(memberEntity.getUsername())
                    .beforeMoney(beforeMoney)
                    .afterMoney(afterMoney)
                    .flowMoney(flowMoney)
                    .item(ItemEnum.HASH_BET_SETTLE.getCode())
                    .itemZh(ItemEnum.HASH_BET_SETTLE.getMsg())
                    .createTime(new Date())
                    .remark("")
                    .build();
            rows = memberFlowMapper.insert(userFlowEntity);
            if (rows <= 0) {
                throw new RuntimeException("settle: update user flow exception");
            }

            //更新统计
            StatisticsEntity statisticsEntity = StatisticsEntity.builder()
                    .date(DateUtil.format(new Date(), "yyyyMMdd"))
                    .uid(bet.getUid())
                    .username(bet.getUsername())
                    .betMoney(bet.getMoney())
                    .betPayoutMoney(bet.getPayoutMoney())
                    .betProfitMoney(bet.getProfitMoney())
                    .updateTime(new Date())
                    .build();

            statisticsMapper.insertOrUpdate(statisticsEntity);

        }

        return true;
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
        if (!StringUtils.isEmpty(entity.getHashBlockResult())) {
            wrappers.eq(HashBetEntity::getHashBlockResult, entity.getHashBlockResult());
        }
        if (!StringUtils.isEmpty(entity.getGameId()) && entity.getGameId() > 0) {
            wrappers.eq(HashBetEntity::getGameId, entity.getGameId());
        }
        return wrappers;
    }
}
