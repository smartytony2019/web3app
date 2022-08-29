package com.xinbo.chainblock.controller.api;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.BaseApiBo;
import com.xinbo.chainblock.bo.TransactionApiBo;
import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.dto.MemberDto;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.enums.MemberFlowItemEnum;
import com.xinbo.chainblock.enums.MemberTypeEnum;
import com.xinbo.chainblock.enums.TransferEnum;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.service.WalletService;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.MemberLoginVo;
import com.xinbo.chainblock.vo.TransferVo;
import com.xinbo.chainblock.vo.RegisterVo;
import com.xinbo.chainblock.vo.WithdrawVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController("ApiMemberController")
@RequestMapping("api/member")
public class MemberController {


    @Autowired
    private TrxApi trxApi;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${trx.token-info.contract-address}")
    private String contractAddress;

    @JwtIgnore
    @Operation(summary = "register", description = "注册")
    @PostMapping("register")
    public R<Object> register(@RequestBody RegisterVo vo) {
        MemberEntity entity = MemberEntity.builder()
                .username(vo.getUsername())
                .pwd(vo.getPwd())
                .version(1)
                .salt("1234")
                .money(0F)
                .build();

        boolean isSuccess = memberService.register(entity, vo.getCode());
        if (isSuccess) {
            redisTemplate.opsForList().leftPush(RedisConst.MEMBER_REGISTER, JSONObject.toJSONString(entity));
            return R.builder().data(StatusCode.SUCCESS).build();
        } else {
            return R.builder().data(StatusCode.REGISTER_ERROR).build();
        }
    }

    @JwtIgnore
    @Operation(summary = "login", description = "注册")
    @PostMapping("login")
    public R<Object> login(@RequestBody MemberLoginVo vo) {
        try {
            String username = vo.getUsername();
            String pwd = vo.getPwd();

            //Step 1: 验证数据是否合法
            MemberEntity entity = memberService.findByUsername(username);
            if (ObjectUtils.isEmpty(entity) || entity.getId() <= 0) {
                return R.builder().code(StatusCode.FAILURE).msg("username not found").build();
            }

            String encodePwd = DigestUtil.md5Hex(DigestUtil.md5Hex(pwd) + entity.getSalt());
            if (!encodePwd.equals(entity.getPwd())) {
                return R.builder().code(StatusCode.FAILURE).msg("wrong password").build();
            }


            //生成token
            JwtUserBo jwtUserBo = JwtUserBo.builder()
                    .uid(entity.getId())
                    .username(entity.getUsername())
                    .build();
            String token = JwtUtil.generateToken(jwtUserBo);
            Map<String, String> map = new HashMap<>();
            map.put("token", String.format("Bearer %s", token));
            return R.builder().code(StatusCode.SUCCESS).data(map).build();
        } catch (Exception ex) {

        }
        return R.builder().code(StatusCode.FAILURE).build();
    }


    @JwtIgnore
    @Operation(summary = "balanceUSDT", description = "USDT余额")
    @PostMapping("balanceUSDT")
    public R<Object> balanceUSDT() {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
//        String balance = memberService.balanceUSDT(jwtUser.getUid());
        return R.builder().code(StatusCode.SUCCESS).data(0).build();
    }

    @JwtIgnore
    @Operation(summary = "balance", description = "TRX&USDT余额")
    @PostMapping("balance/{deep}")
    public R<Object> balance(@PathVariable int deep) {

        int uid = 19;
        String key = String.format(RedisConst.MEMBER_BALANCE, uid);

        // deep为1则拿缓存数据
        if(deep == 0) {
            String json = redisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(json)) {
                JSONObject object = JSONObject.parseObject(json);
                return R.builder().code(StatusCode.SUCCESS).data(object).build();
            }
        }


        JSONObject object = new JSONObject();
        WalletEntity walletEntity = walletService.findByUid(uid);

        // 资金帐户余额
        String trc20 = trxApi.getBalanceOfTrc20(contractAddress, walletEntity.getAddressBase58(), walletEntity.getPrivateKey());
        String trx = trxApi.getBalanceOfTrx(walletEntity.getAddressBase58());
        object.put("fundingAccount", Float.parseFloat(trc20));
        object.put("trx", Float.parseFloat(trx));


        // 交易帐户余额
        MemberEntity memberEntity = memberService.findById(uid);
        object.put("tradingAccount", memberEntity.getMoney());

        // 总资产
        object.put("total", Float.parseFloat(trc20) + memberEntity.getMoney());

        //缓存1分钟
        redisTemplate.opsForValue().set(key, JSON.toJSONString(object), 5, TimeUnit.MINUTES);

        // Step 1: 获取资金帐号转帐记录@todo
        redisTemplate.opsForSet().add(RedisConst.MEMBER_FINANCE, JSON.toJSONString(walletEntity));
        return R.builder().code(StatusCode.SUCCESS).data(object).build();
    }


    @JwtIgnore
    @Operation(summary = "wallet", description = "钱包地址")
    @PostMapping("wallet")
    public R<Object> wallet() {
        int uid = 19;
        WalletEntity entity = walletService.findByUid(uid);
        String result = !ObjectUtils.isEmpty(entity) ? entity.getAddressBase58() : "";
        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }


    @JwtIgnore
    @Operation(summary = "transfer", description = "资金转换")
    @PostMapping("transfer")
    public R<Object> transfer(@RequestBody TransferVo vo) {
        try {
            /* ******************************  Step 1: 判断数据是否合法  **************************************** */
            Map<Integer, EnumItemBo> map = TransferEnum.toMap();
            EnumItemBo enumItemBo = map.get(vo.getDirection());
            if (StringUtils.isEmpty(enumItemBo.getCode())) {
                throw new BusinessException(1, "数据不合法!");
            }

            if (vo.getMoney() <= 0) {
                throw new BusinessException(1, "数据不合法!!");
            }

            // @todo 模拟数据
            MemberEntity entity = MemberEntity.builder()
                    .id(19)
                    .username("demo5566")
                    .build();

            /* ******************************  Step 2: 开始划转  **************************************** */
            BaseApiBo<TransactionApiBo> result = null;
            // Step 2.1 资金帐户 => 交易帐户
            if (vo.getDirection() == TransferEnum.FUNDING2TRADING.getCode()) {
                // 会员数字钱包
                WalletEntity memberWallet = walletService.findByUid(entity.getId());

                // 主数字钱包
                WalletEntity mainWallet = walletService.findMain();
                if (ObjectUtils.isEmpty(mainWallet)) {
                    throw new BusinessException(1, "主数字钱包不存在!!");
                }

                String balanceOfTrc20 = trxApi.getBalanceOfTrc20(contractAddress, memberWallet.getAddressBase58(), memberWallet.getPrivateKey());
                if (StringUtils.isEmpty(balanceOfTrc20)) {
                    throw new BusinessException(1, "Trx20余额未查找到");
                }

                float balance = Float.parseFloat(balanceOfTrc20);
                if (balance < vo.getMoney()) {
                    throw new BusinessException(1, "余额不足");
                }

                result = trxApi.transactionOfTrc20(contractAddress, memberWallet.getAddressBase58(), memberWallet.getPrivateKey(), String.valueOf(vo.getMoney()), mainWallet.getAddressBase58());
            }

            // Step 2.2 交易帐户 => 资金帐户
            if (vo.getDirection() == TransferEnum.TRADING2FUNDING.getCode()) {
                MemberEntity memberEntity = memberService.findById(entity.getId());

                // 会员数字钱包
                WalletEntity memberWallet = walletService.findByUid(entity.getId());

                // 主数字钱包
                WalletEntity mainWallet = walletService.findMain();


                float balance = memberEntity.getMoney();
                if (balance < vo.getMoney()) {
                    throw new BusinessException(1, "余额不足!!");
                }

                result = trxApi.transactionOfTrc20(contractAddress, mainWallet.getAddressBase58(), mainWallet.getPrivateKey(), String.valueOf(vo.getMoney()), memberWallet.getAddressBase58());
            }

            if (ObjectUtils.isEmpty(result)) {
                throw new BusinessException(1, "划转失败");
            }

            if (StringUtils.isEmpty(result.getCode()) || result.getCode() != StatusCode.SUCCESS) {
                throw new RuntimeException(result.getMsg());
            }

            /* ******************************  Step 3: 拼接数据  **************************************** */
            // Step 3.1: 交易金额变更
            MemberEntity memberEntity = memberService.findById(entity.getId());

            float money = vo.getDirection() == TransferEnum.FUNDING2TRADING.getCode() ? vo.getMoney() : vo.getMoney() * -1;
            // Step 3.2: 交易金额变更
            MemberEntity member = MemberEntity.builder()
                    .id(memberEntity.getId())
                    .money(money)
                    .version(memberEntity.getVersion())
                    .build();


            MemberFlowItemEnum memberFlowItemEnum = vo.getDirection() == TransferEnum.FUNDING2TRADING.getCode() ? MemberFlowItemEnum.TRANSFER_FUNDING2TRADING : MemberFlowItemEnum.TRANSFER_TRADING2FUNDING;
            // Step 3.3: 帐变
            MemberFlowEntity flow = MemberFlowEntity.builder()
                    .sn(result.getData().getTxid())
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .beforeMoney(memberEntity.getMoney())
                    .flowMoney(money)
                    .afterMoney(memberEntity.getMoney() + money)
                    .item(memberFlowItemEnum.getName())
                    .itemCode(memberFlowItemEnum.getCode())
                    .itemZh(memberFlowItemEnum.getNameZh())
                    .createTime(new Date())
                    .build();

            /* ******************************  Step 4: 入Redis队列  **************************************** */
            JSONObject object = new JSONObject();
            object.put("member", member);
            object.put("flow", flow);
            redisTemplate.opsForList().leftPush(RedisConst.MEMBER_TRANSFER, JSON.toJSONString(object));
            return R.builder().code(StatusCode.SUCCESS).data(result.getData().getTxid()).build();
        } catch (RuntimeException ex) {
            return R.builder().code(StatusCode.FAILURE).msg(ex.getMessage()).build();
        } catch (Exception ex) {
            return R.builder().code(StatusCode.FAILURE).build();
        }
    }


    @JwtIgnore
    @Operation(summary = "info", description = "会员信息")
    @GetMapping("info")
    public R<Object> info() {
        int uid = 19;
        MemberEntity entity = memberService.info(uid);

        Map<Integer, EnumItemBo> map = MemberFlowItemEnum.toMap();

        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, MemberDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "withdraw", description = "会员提现")
    @PostMapping("withdraw")
    public R<Object> withdraw(@RequestBody WithdrawVo vo) {
        try {

            int uid = 19;
            MemberEntity memberEntity = memberService.info(uid);
            if(vo.getMoney()>memberEntity.getMoney()) {
                throw new BusinessException(1, "金额不足");
            }

            if(!memberEntity.getIsEnable()) {
                throw new BusinessException(1, "帐户已冻结");
            }

            if(memberEntity.getType() != MemberTypeEnum.NORMAL.getCode()) {
                throw new BusinessException(1, "此帐户禁提现");
            }

            WalletEntity mainWallet = walletService.findMain();
            BaseApiBo<TransactionApiBo> transaction = trxApi.transactionOfTrc20(contractAddress, mainWallet.getAddressBase58(), mainWallet.getPrivateKey(), String.valueOf(vo.getMoney()), memberEntity.getWithdrawWallet());
            if(ObjectUtils.isEmpty(transaction) || transaction.getCode() != StatusCode.SUCCESS) {
                throw new BusinessException(1, "提现失败，请重新提交!");
            }

            // tron调用失败
            if(!transaction.getData().isResult()) {
                throw new BusinessException(1, "提现失败，请重新提交!!");
            }

            return R.builder().code(StatusCode.SUCCESS).build();
        }catch (BusinessException ex) {
            return R.builder().code(StatusCode.FAILURE).msg(ex.getMsg()).build();
        }catch (Exception ex) {
            return R.builder().code(StatusCode.FAILURE).msg("执行异常").build();
        }


    }

}
