package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.MemberDto;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.mapper.MemberMapper;
import com.xinbo.chainblock.service.AgentService;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AgentService agentService;


    @Override
    public boolean insert() {
        MemberEntity entity = MemberEntity.builder()
                .username("jack").money(1000F).salt("123").version(1)
                .build();
        memberMapper.insert(entity);
        return true;
    }

    @Override
    public MemberEntity findByUsername(String username) {
        return memberMapper.selectOne(this.createWrapper(MemberEntity.builder().username(username).build()));
    }

    @Override
    public MemberEntity findById(int id) {
        return memberMapper.selectById(id);
    }

    @Override
    public boolean increment(MemberEntity entity) {
        int increment = memberMapper.increment(entity);
        return increment > 0;
    }

    /**
     * 注册
     *
     * @param entity
     * @param code
     * @return
     */
    @Transactional
    @Override
    public boolean register(MemberEntity entity, int code) {
        AgentEntity pAgentEntity = agentService.findByUid(code);
        if (ObjectUtils.isEmpty(pAgentEntity) || pAgentEntity.getId() <= 0) {
            return false;
        }

        boolean isSuccess = memberMapper.insert(entity) > 0;
        if (!isSuccess) {
            return false;
        }

        AgentEntity agentEntity = AgentEntity.builder()
                .uid(entity.getId())
                .username(entity.getUsername())
                .pUid(pAgentEntity.getUid())
                .level(pAgentEntity.getLevel() + 1)
                .build();
        isSuccess = agentService.insert(agentEntity);
        if (!isSuccess) {
            return false;
        }

        return true;
    }

    @Override
    public BasePageBo findPage(MemberEntity entity, long current, long size) {
        Page<MemberEntity> page = new Page<>(current, size);
        LambdaQueryWrapper<MemberEntity> wrapper = this.createWrapper(entity);

        IPage<MemberEntity> iPage = memberMapper.selectPage(page, wrapper);
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), MemberDto.class)).build();
    }

    @Override
    public boolean update(MemberEntity entity) {
        return memberMapper.updateById(entity) > 0;
    }

    @Override
    public MemberEntity info(int uid) {
        return memberMapper.info(uid);
    }

//    @Override
//    public String balanceTrc20(int uid) {
//        WalletEntity walletEntity = walletService.findByUid(uid);
//        return trxApi.getBalanceOfTrc20(contractAddress, walletEntity.getAddressBase58(), walletEntity.getPrivateKey());
//    }
//




    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<MemberEntity> createWrapper(MemberEntity entity) {
        LambdaQueryWrapper<MemberEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(MemberEntity::getId, entity.getId());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(MemberEntity::getUsername, entity.getUsername());
        }
        return wrappers;
    }
}
