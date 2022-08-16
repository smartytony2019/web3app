package com.xinbo.chainblock.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.dto.MemberDto;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.entity.terminal.AccountApiEntity;
import com.xinbo.chainblock.entity.terminal.BaseEntity;
import com.xinbo.chainblock.entity.terminal.TransactionApiEntity;
import com.xinbo.chainblock.mapper.MemberMapper;
import com.xinbo.chainblock.service.AgentService;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.service.WalletService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    @Autowired
    private TrxApi trxApi;

    @Autowired
    private WalletService walletService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AgentService agentService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${trx.token-info.contract-address}")
    private String contractAddress;

    @Override
    public boolean insert() {
        MemberEntity entity = MemberEntity.builder()
                .username("jack").createTime(new Date()).money(1000F).salt("123").version(1)
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


        //Step 2: 请求终端
        AccountApiEntity account = trxApi.createAccount();
        if (ObjectUtils.isEmpty(account)) {
            return false;
        }

        WalletEntity walletEntity = WalletEntity.builder()
                .uid(entity.getId())
                .username(entity.getUsername())
                .type(1)
                .publicKey(account.getPublicKey())
                .privateKey(account.getPrivateKey())
                .addressBase58(account.getAddress().getBase58())
                .addressHex(account.getAddress().getHex())
                .build();

        isSuccess = walletService.insert(walletEntity);
        if (!isSuccess) {
            return false;
        }

        return true;
    }

    @Override
    public BasePage findPage(MemberEntity entity, long current, long size, Date start, Date end) {
        Page<MemberEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        LambdaQueryWrapper<MemberEntity> wrapper = this.createWrapper(entity);
        if (!ObjectUtils.isEmpty(start) && !ObjectUtils.isEmpty(end)) {
            wrapper.ge(MemberEntity::getCreateTime, start).le(MemberEntity::getCreateTime, end);
        }

        IPage<MemberEntity> iPage = memberMapper.selectPage(page, wrapper);
        return BasePage.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), MemberDto.class)).build();
    }

    @Override
    public boolean update(MemberEntity entity) {
        return memberMapper.updateById(entity) > 0;
    }

    @Override
    public String balanceUSDT(int uid) {
        WalletEntity walletEntity = walletService.findByUid(uid);
        return trxApi.getBalanceOfTrc20(contractAddress, walletEntity.getAddressBase58(), walletEntity.getPrivateKey());
    }

    @Override
    public Map<String, String> balance(int uid) {
        WalletEntity walletEntity = walletService.findByUid(uid);
        // Step 1: 获取资金帐号转帐记录@todo
        redisTemplate.opsForSet().add(RedisConst.MEMBER_FINANCE, JSON.toJSONString(walletEntity));

        String trc20 = trxApi.getBalanceOfTrc20(contractAddress, walletEntity.getAddressBase58(), walletEntity.getPrivateKey());
        String trx = trxApi.getBalanceOfTrx(walletEntity.getAddressBase58());
        Map<String, String> map = new HashMap<>();
        map.put("usdt", trc20);
        map.put("trx", trx);
        return map;
    }

    /**
     * 资金帐户 => 交易帐户
     *
     * @param uid
     * @param money
     * @return
     */
    @Override
    public BaseEntity<TransactionApiEntity> fundingAccount2TradingAccount(int uid, float money) {
        BaseEntity<TransactionApiEntity> result = new BaseEntity<>();
        try {
            // 会员数字钱包
            WalletEntity memberWallet = walletService.findByUid(uid);

            // 主数字钱包
            WalletEntity mainWallet = walletService.findMain();
            if(ObjectUtils.isEmpty(mainWallet)) {
                return result;
            }

            String balanceOfTrc20 = trxApi.getBalanceOfTrc20(contractAddress, memberWallet.getAddressBase58(), memberWallet.getPrivateKey());
            if (StringUtils.isEmpty(balanceOfTrc20)) {
                return result;
            }

            float balance = Float.parseFloat(balanceOfTrc20);
            if (balance < money) {
                return result;
            }

            result = trxApi.transactionOfTrc20(contractAddress, memberWallet.getAddressBase58(), memberWallet.getPrivateKey(), String.valueOf(money), mainWallet.getAddressBase58());
        }catch (Exception ex) {
            log.error("fundingAccount2TradingAccount", ex);
        }
        return result;
    }

    /**
     * 交易帐户 => 资金帐户
     *
     * @param uid
     * @param money
     * @return
     */
    @Override
    public BaseEntity<TransactionApiEntity> tradingAccount2FundingAccount(int uid, float money) {
        BaseEntity<TransactionApiEntity> result = new BaseEntity<>();
        try {
            MemberEntity memberEntity = memberMapper.selectById(uid);

            // 会员数字钱包
            WalletEntity memberWallet = walletService.findByUid(uid);

            // 主数字钱包
            WalletEntity mainWallet = walletService.findMain();


            float balance = memberEntity.getMoney();
            if (balance < money) {
                return result;
            }

            result = trxApi.transactionOfTrc20(contractAddress, mainWallet.getAddressBase58(), mainWallet.getPrivateKey(), String.valueOf(money), memberWallet.getAddressBase58());
        }catch (Exception ex) {
            log.error("tradingAccount2FundingAccount", ex);
        }
        return result;
    }


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
