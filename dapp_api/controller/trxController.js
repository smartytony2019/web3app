
const trxModel = require("../model/trxModel")
const R = require("../config/R")

let fromAddress = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu"; //address _from
let toAddress = "TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe"; //address _to
let contractAddress = "TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR";

module.exports = {
  async createAccount (ctx) {
    let result = await trxModel.createAccount();
    ctx.body = R.success(result)
  },

  async getBalanceOfTrx (ctx) {
    let result = await trxModel.getBalanceOfTrx(fromAddress);
    ctx.body = R.success(result)
  },

  async getBalanceOfTrc20 (ctx) {
    let result = await trxModel.getBalanceOfTrc20(fromAddress);
    ctx.body = R.success(result)
  },


  async transactionOfTrx (ctx) {
    let amount = 1000000; //amount
    let result = await trxModel.transactionOfTrx(fromAddress, amount, toAddress);
    ctx.body = R.success(result)
  },

  
  async transactionOfTrc20 (ctx) {
    let amount = 10 * Math.pow(10, 18);
    let result = await trxModel.transactionOfTrc20(contractAddress, fromAddress, amount, toAddress);
    ctx.body = R.success(result)
  },

  
  async getTransactionInfo (ctx) {
    let txID = 'def2f97efa3c6743eab21873993e5e6615b8a71409eda8f720b0779e19b96c62';
    let result = await trxModel.getTransactionInfo(txID);
    ctx.body = R.success(result)
  }
}
