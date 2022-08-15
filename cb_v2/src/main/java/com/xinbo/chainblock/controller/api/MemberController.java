package com.xinbo.chainblock.controller.api;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.utils.JwtUser;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.MemberLoginVo;
import com.xinbo.chainblock.vo.MemberTransferVo;
import com.xinbo.chainblock.vo.RegisterVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController("ApiMemberController")
@RequestMapping("api/member")
public class MemberController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @JwtIgnore
    @Operation(summary = "register", description = "注册")
    @PostMapping("register")
    public R<Object> register(@RequestBody RegisterVo vo) {
        MemberEntity entity = MemberEntity.builder()
                .username(vo.getUsername())
                .pwd(vo.getPwd())
                .createTime(new Date())
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
            JwtUser jwtUser = JwtUser.builder()
                    .uid(entity.getId())
                    .username(entity.getUsername())
                    .build();
            String token = JwtUtil.generateToken(jwtUser);
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
        JwtUser jwtUser = JwtUtil.getJwtUser();
        String balance = memberService.balanceUSDT(jwtUser.getUid());
        return R.builder().code(StatusCode.SUCCESS).data(balance).build();
    }

    @JwtIgnore
    @Operation(summary = "balance", description = "TRX&USDT余额")
    @PostMapping("balance")
    public R<Object> balance() {
        MemberEntity entity = MemberEntity.builder()
                .id(19)
                .username("demo5566")
                .build();
        Map<String, String> map = memberService.balance(entity.getId());
        return R.builder().code(StatusCode.SUCCESS).data(map).build();
    }



    @JwtIgnore
    @Operation(summary = "transfer", description = "资金转换")
    @PostMapping("transfer")
    public R<Object> transfer(@RequestBody MemberTransferVo vo) {
        try {
            MemberEntity entity = MemberEntity.builder()
                    .id(19)
                    .username("demo5566")
                    .build();

            String result = "";
            // 资金帐户 => 交易帐户
            if(vo.getDirect() == 1) {
                result = memberService.fundingAccount2TradingAccount(entity.getId(), vo.getMoney());
            }

            // 交易帐户 => 资金帐户
            if(vo.getDirect() == 2) {
                result = memberService.tradingAccount2FundingAccount(entity.getId(), vo.getMoney());
            }

            return R.builder().code(StatusCode.SUCCESS).data(result).build();
        }catch (Exception ex) {
            return R.builder().code(StatusCode.FAILURE).build();
        }
    }
}
