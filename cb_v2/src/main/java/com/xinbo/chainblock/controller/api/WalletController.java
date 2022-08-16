package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.TransferEntity;
import com.xinbo.chainblock.entity.terminal.BaseEntity;
import com.xinbo.chainblock.entity.terminal.TransactionApiEntity;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.service.TransferService;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.TransferVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController("ApiWalletController")
@RequestMapping("api/wallet")
public class WalletController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TransferService transferService;


    @JwtIgnore
    @Operation(summary = "internal", description = "内部转换(资金帐户<=>交易帐户)")
    @PostMapping("internal")
    public R<Object> internal(@RequestBody TransferVo vo) {
        try {
            MemberEntity entity = MemberEntity.builder()
                    .id(19)
                    .username("demo5566")
                    .build();

            BaseEntity<TransactionApiEntity> result = null;
            // 资金帐户 => 交易帐户
            if (vo.getDirect() == 1) {
                result = memberService.fundingAccount2TradingAccount(entity.getId(), vo.getMoney());
            }

            // 交易帐户 => 资金帐户
            if (vo.getDirect() == 2) {
                result = memberService.tradingAccount2FundingAccount(entity.getId(), vo.getMoney());
            }

            if(ObjectUtils.isEmpty(result)) {
                throw new BusinessException(1, "");
            }

            if(result.getCode() == 0) {
                long expired = DateUtil.currentSeconds() + (30 * 60); //时间戳(秒级)
                TransferEntity transferEntity = TransferEntity.builder()
                        .uid(entity.getId())
                        .username(entity.getUsername())
                        .type(vo.getDirect())
                        .transactionId(result.getData().getTxid())
                        .money(vo.getMoney())
                        .expired(expired)
                        .status(0)
                        .build();
                transferService.insert(transferEntity);
                return R.builder().code(StatusCode.SUCCESS).data(result.getData().getTxid()).build();
            } else {
                return R.builder().code(StatusCode.FAILURE).msg(result.getMsg()).build();
            }
        } catch (Exception ex) {
            return R.builder().code(StatusCode.FAILURE).build();
        }
    }


    @JwtIgnore
    @Operation(summary = "external", description = "外部转换(交易帐户<=>第三方)")
    @PostMapping("external")
    public R<Object> external(@RequestBody TransferVo vo) {
        try {
            MemberEntity entity = MemberEntity.builder()
                    .id(19)
                    .username("demo5566")
                    .build();

            BaseEntity<TransactionApiEntity> result = null;
            // 资金帐户 => 交易帐户
            if (vo.getDirect() == 1) {
                result = memberService.fundingAccount2TradingAccount(entity.getId(), vo.getMoney());
            }

            // 交易帐户 => 资金帐户
            if (vo.getDirect() == 2) {
                result = memberService.tradingAccount2FundingAccount(entity.getId(), vo.getMoney());
            }

            if(ObjectUtils.isEmpty(result)) {
                throw new BusinessException(1, "");
            }

            if(result.getCode() == 0) {
                long expired = DateUtil.currentSeconds() + (30 * 60); //时间戳(秒级)
                TransferEntity transferEntity = TransferEntity.builder()
                        .uid(entity.getId())
                        .username(entity.getUsername())
                        .type(vo.getDirect())
                        .transactionId(result.getData().getTxid())
                        .money(vo.getMoney())
                        .expired(expired)
                        .status(0)
                        .build();
                transferService.insert(transferEntity);
                return R.builder().code(StatusCode.SUCCESS).data(result.getData().getTxid()).build();
            } else {
                return R.builder().code(StatusCode.FAILURE).msg(result.getMsg()).build();
            }
        } catch (Exception ex) {
            return R.builder().code(StatusCode.FAILURE).build();
        }
    }



    @GetMapping("find/{transactionId}")
    public R<Object> find(@PathVariable String transactionId) {
        TransferEntity entity = transferService.findByTransactionId(transactionId);
        if(ObjectUtils.isEmpty(entity)) {
            return R.builder().code(StatusCode.FAILURE).build();
        }
        return R.builder().code(StatusCode.SUCCESS).data(entity.getStatus()).build();
    }

}
