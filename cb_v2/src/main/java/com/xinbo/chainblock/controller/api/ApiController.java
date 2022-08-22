package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.AccountApiBo;
import com.xinbo.chainblock.bo.BaseApiBo;
import com.xinbo.chainblock.bo.TransactionApiBo;
import com.xinbo.chainblock.bo.TransactionInfoApiBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.service.WalletService;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tony
 * @date 6/23/22 5:48 下午
 * @desc file desc
 */
@RestController
public class ApiController {

    @Autowired
    private TrxApi trxApi;

    @Autowired
    private WalletService walletService;

    @JwtIgnore
    @Operation(summary = "genMainAccount", description = "生成主帐号")
    @GetMapping("genMainAccount")
    public R<Object> genMainAccount() {
        AccountApiBo account = trxApi.createAccount();
        WalletEntity entity = WalletEntity.builder()
                .addressHex(account.getAddress().getHex())
                .addressBase58(account.getAddress().getBase58())
                .publicKey(account.getPublicKey())
                .privateKey(account.getPrivateKey())
                .type(1)
                .isMain(true)
                .build();
        boolean insert = walletService.insert(entity);
        return R.builder().code(StatusCode.SUCCESS).data(insert).build();
    }


    @Operation(summary = "createAccount")
    @GetMapping("createAccount")
    public R<Object> createAccount() {
        AccountApiBo account = trxApi.createAccount();
        return R.builder().code(StatusCode.SUCCESS).data(account).build();
    }

    @Operation(summary = "getBalanceOfTrx")
    @GetMapping("getBalanceOfTrx/{fromAddress}")
    public R<Object> getBalanceOfTrx(@PathVariable String fromAddress) {
        String balanceOfTrx = trxApi.getBalanceOfTrx(fromAddress);
        return R.builder().code(StatusCode.SUCCESS).data(balanceOfTrx).build();
    }

    @Operation(summary = "getBalanceOfTrc20")
    @GetMapping("getBalanceOfTrc20/{fromAddress}/{privateKey}")
    public R<Object> getBalanceOfTrc20(@PathVariable String fromAddress, @PathVariable String privateKey) {
        String balanceOfTrx = trxApi.getBalanceOfTrc20("", fromAddress, privateKey);
        return R.builder().code(StatusCode.SUCCESS).data(balanceOfTrx).build();
    }

    @Operation(summary = "transactionOfTrx")
    @GetMapping("transactionOfTrx/{fromAddress}/{privateKey}/{amount}/{toAddress}")
    public R<Object> transactionOfTrx(@PathVariable String fromAddress, @PathVariable String privateKey, @PathVariable float amount, @PathVariable String toAddress) {
        TransactionApiBo entity = trxApi.transactionOfTrx(fromAddress, privateKey, amount, toAddress);
        return R.builder().code(StatusCode.SUCCESS).data(entity).build();
    }


    @Operation(summary = "transactionOfTrc20")
    @GetMapping("transactionOfTrc20")
    public R<Object> transactionOfTrc20() {
        String contractAddress = "TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR";
        String fromAddress = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu";
        String toAddress = "TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe";
        String privateKey = "f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703";
        double amount = 1.985;
        BaseApiBo<TransactionApiBo> entity = trxApi.transactionOfTrc20(contractAddress,fromAddress, privateKey, String.valueOf(amount), toAddress);
        return R.builder().code(StatusCode.SUCCESS).data(entity).build();
    }




    @Operation(summary = "getTransactionInfo")
    @GetMapping("getTransactionInfo/{txID}")
    public R<Object> getTransactionInfo(@PathVariable String txID) {
        TransactionInfoApiBo entity = trxApi.getTransactionInfo(txID);
        return R.builder().code(StatusCode.SUCCESS).data(entity).build();
    }


}
