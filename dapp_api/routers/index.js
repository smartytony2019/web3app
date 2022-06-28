const router = require('koa-router')()

const trxController = require("../controller/trxController")
const resultController = require("../controller/resultController")

/*  ****** TRX API ****** */
//- 创建帐号
router.post('/trx/createAccount', trxController.createAccount);

//- 获取TRX余额
router.post('/trx/getBalanceOfTrx', trxController.getBalanceOfTrx);

//- 获取Trc20余额(USDT)
router.post('/trx/getBalanceOfTrc20', trxController.getBalanceOfTrc20);

//- TRX转帐
router.post('/trx/transactionOfTrx', trxController.transactionOfTrx);

//- TRC20转帐(USDT)
router.post('/trx/transactionOfTrc20', trxController.transactionOfTrc20);

//- 获取交易信息
router.post('/trx/getTransactionInfo', trxController.getTransactionInfo);



/*  ****** TRX API ****** */
//- 获取开奖结果
router.get('/result/:game_id', resultController.result);


module.exports = router