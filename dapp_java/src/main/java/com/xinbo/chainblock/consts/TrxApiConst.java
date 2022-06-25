package com.xinbo.chainblock.consts;

/**
 * @author tony
 * @date 6/23/22 5:39 下午
 * @desc file desc
 */
public interface TrxApiConst {

    //创建帐号
    String CREATE_ACCOUNT = "/trx/createAccount";

    //获取TRX余额
    String GET_BALANCE_TRX = "/trx/getBalanceOfTrx";

    //获取Trc20余额(USDT)
    String GET_BALANCE_TRC20 = "/trx/getBalanceOfTrc20";

    //TRX转帐
    String TRANSACTION_TRX = "/trx/transactionOfTrx";

    //TRC20转帐(USDT)
    String TRANSACTION_TRC20 = "/trx/transactionOfTrc20";

    //获取交易信息
    String GET_TRANSACTION_INFO = "/trx/getTransactionInfo";


    //获取交易记录
    String GET_TRANSACTIONS_RECORD = "%s/accounts/%s/transactions/trc20?only_confirmed=true&only_to=true&limit=200&min_timestamp=%s";



}
