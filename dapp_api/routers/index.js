const router = require('koa-router')()

const trxController = require("../controller/trxController")

/*  ****** TRX API ****** */
//- 获取TRX余额
router.get('/getBalanceOfTrx', trxController.getBalanceOfTrx);

//- 获取Trc20余额(USDT)
router.get('/getBalanceOfTrc20', trxController.getBalanceOfTrc20);

//- TRX转帐
router.get('/transactionOfTrx', trxController.transactionOfTrx);

//- TRC20转帐(USDT)
router.get('/transactionOfTrc20', trxController.transactionOfTrc20);

module.exports = router