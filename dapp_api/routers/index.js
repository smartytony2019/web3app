const router = require('koa-router')()

const trxController = require("../controller/trxController")

/*  ****** TRX API ****** */
//- 创建帐号
router.get('/createAccount', trxController.createAccount);

//- 获取TRX余额
router.get('/getBalanceOfTrx', trxController.getBalanceOfTrx);

//- 获取Trc20余额(USDT)
router.get('/getBalanceOfTrc20', trxController.getBalanceOfTrc20);

//- TRX转帐
router.get('/transactionOfTrx', trxController.transactionOfTrx);

//- TRC20转帐(USDT)
router.get('/transactionOfTrc20', trxController.transactionOfTrc20);

//- 获取交易信息
router.get('/getTransactionInfo', trxController.getTransactionInfo);

module.exports = router