const trxModel = require("../model/trxModel")
const R = require("../utils/R")

// let fromAddress = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu"; //address _from
// let toAddress = "TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe"; //address _to
// let contractAddress = "TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR";

module.exports = {

  /**
   * 创建帐号
   * @param {Object} ctx
   */
  async createAccount (ctx) {
    let result = await trxModel.createAccount();
    ctx.body = R.success(result)
  },


  /**
   * 获取TRX余额
   * @param {Object} ctx 
   */
  async getBalanceOfTrx (ctx) {
    let data = ctx.request.body
    let fromAddress = data.fromAddress
    console.log('getBalanceOfTrx', fromAddress);
    let isValid = await trxModel.checkAddressIsValid(fromAddress);
    if(!isValid) {
      ctx.body = R.fail('地址不合法')
      return;
    }

    let result = await trxModel.getBalanceOfTrx(fromAddress);
    ctx.body = R.success(result)
  },


  /**
   * 获取Trc20余额(USDT)
   * @param {Object} ctx 
   */
  async getBalanceOfTrc20 (ctx) {
    let data = ctx.request.body

    let contractAddress = data.contractAddress
    let isValid = await trxModel.checkAddressIsValid(contractAddress);
    if(!isValid) {
      ctx.body = R.fail('合约地址不合法')
      return;
    }

    let fromAddress = data.fromAddress
    isValid = await trxModel.checkAddressIsValid(fromAddress);
    if(!isValid) {
      ctx.body = R.fail('地址不合法')
      return;
    }

    let privateKey = await trxModel.decodePrivateKey(data.privateKey);
    if(privateKey == null) {
      ctx.body = R.fail('私钥不合法')
      return;
    }

    let result = await trxModel.getBalanceOfTrc20(contractAddress, fromAddress, privateKey);
    ctx.body = R.success(result)
  },


  /**
   * TRX转帐
   * @param {Object} ctx 
   */
  async transactionOfTrx (ctx) {
    let data = ctx.request.body
    let fromAddress = data.fromAddress
    let isValid = await trxModel.checkAddressIsValid(fromAddress);
    if(!isValid) {
      ctx.body = R.fail('fromAddress地址不合法')
      return;
    }
    
    let toAddress = data.toAddress
    isValid = await trxModel.checkAddressIsValid(toAddress);
    if(!isValid) {
      ctx.body = R.fail('toAddress地址不合法')
      return;
    }
    
    let amount = data.amount
    if(amount == null || typeof amount != 'number' || amount <= 0) {
      ctx.body = R.fail('amount不合法')
      return;
    }

    let privateKey = await trxModel.decodePrivateKey(data.privateKey);
    if(privateKey == null) {
      ctx.body = R.fail('私钥不合法')
      return;
    }

    let result = await trxModel.transactionOfTrx(fromAddress, amount, toAddress, privateKey);
    ctx.body = R.success(result)
  },

  
  /**
   * TRC20转帐(USDT)
   * @param {Object} ctx 
   */
  async transactionOfTrc20 (ctx) {
    let data = ctx.request.body
    let contractAddress = data.contractAddress
    let isValid = await trxModel.checkAddressIsValid(contractAddress);
    if(!isValid) {
      ctx.body = R.fail('合约地址不合法')
      return;
    }

    let fromAddress = data.fromAddress
    isValid = await trxModel.checkAddressIsValid(fromAddress);
    if(!isValid) {
      ctx.body = R.fail('fromAddress地址不合法')
      return;
    }
    
    let toAddress = data.toAddress
    isValid = await trxModel.checkAddressIsValid(toAddress);
    if(!isValid) {
      ctx.body = R.fail('toAddress地址不合法')
      return;
    }
    
    let amount = data.amount
    if(amount == null || typeof amount != 'number' || amount <= 0) {
      ctx.body = R.fail('amount不合法')
      return;
    }

    let privateKey = await trxModel.decodePrivateKey(data.privateKey);
    if(privateKey == null) {
      ctx.body = R.fail('私钥不合法')
      return;
    }


    let result = await trxModel.transactionOfTrc20(contractAddress, fromAddress, amount, toAddress, privateKey);
    ctx.body = R.success(result)
  },

  
  /**
   * 获取交易信息
   * @param {Object} ctx 
   */
  async getTransactionInfo (ctx) {
    let data = ctx.request.body
    let txID = data.txID;
    let result = await trxModel.getTransactionInfo(txID);
    ctx.body = R.success(result)
  }
}
