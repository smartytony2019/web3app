const web3 = require("../helper/web3Helper").getWeb3();
const tronWeb = require("../helper/trxHelper").getTronWeb();
const config = require("../config/config");

module.exports = {

  /**
   * 创建帐号
   * @returns Object
   */
  async createAccount() {
    let result = null;
    try {
      result = await tronWeb.createAccount();
    } catch(error) {
      console.error("createAccount error", error);
    }
    return result;
  },


  /**
   * 获取TRX余额
   * @param {String} address 地址
   * @returns TRX余额
   */
  async getBalanceOfTrx(address) {
    let result = 0;
    try {
      let account = await tronWeb.trx.getAccount(address);
      if (account != null && account.balance != null) {
        result = web3.utils.fromWei(`${account.balance}`, "Mwei");
      }
    } catch (error) {
      console.error("getBalanceOfTrx error", error);
    }
    return result;
  },
  
  /**
   * 获取TRC20余额(USDT)
   * @param {string} address 地址
   * @returns USDT余额
   */
  async getBalanceOfTrc20(address) {
    let result = 0;
    try {
      tronWeb.setPrivateKey(config.privateKey);
      let contract = await tronWeb.contract().at(config.contractAddress);
      let balance = await contract.balanceOf(address).call();
      result = web3.utils.fromWei(`${balance.toString()}`, "ether");
    } catch (error) {
      console.error("getBalanceOfUSDT error", error);
    }
    return result;
  },



  /**
   * TRX转帐
   * @param {String} fromAddress 转款帐户
   * @param {String} amount 金额
   * @param {String} toAddress 收款帐户
   * @returns Object
   */
  async transactionOfTrx(fromAddress, amount, toAddress) {
    let result = null;
    try {
      //Step 1: 创建一个未签名的TRX转账交易
      const tradeobj = await tronWeb.transactionBuilder.sendTrx(
        toAddress,
        amount,
        fromAddress
      );
      if (tradeobj == null || tradeobj.txID == null) {
        throw "创建未签名失败";
      }

      //Step 2: 签名
      const signedtxn = await tronWeb.trx.sign(tradeobj, config.privateKey);
      if (signedtxn == null || signedtxn.txID == null) {
        throw "签名失败";
      }

      //Step 3: 广播
      const receipt = await tronWeb.trx.sendRawTransaction(signedtxn);
      if (receipt == null || receipt.txid == null) {
        throw "广播失败";
      }

      result = receipt;
    } catch (error) {
      console.error("transactionOfTrx error", error);
    }
    return result;
  },
  
  /**
   * TRC20转帐(USDT)
   * @param {String} contractAddress 合约地址
   * @param {String} fromAddress 转款帐户
   * @param {String} amount 金额
   * @param {String} toAddress 收款帐户
   * @returns Object
   */
  async transactionOfTrc20(contractAddress, fromAddress, amount, toAddress) {
    let result = null;
    try {
      //Step 1: 调用智能合约
      const parameter = [
        { type: "address", value: toAddress },
        { type: "uint256", value: tronWeb.toHex(amount) },
      ];
      var tx = await tronWeb.transactionBuilder.triggerSmartContract(contractAddress, "transfer(address,uint256)", {}, parameter, tronWeb.address.toHex(fromAddress));
      console.log("tx", tx);
      if(tx == null || tx.result == null || tx.result.result != true) {
        throw '调用智能合约失败';
      }


      //Step 2: 交易签名
      var signedTx = await tronWeb.trx.sign(tx.transaction, config.privateKey);
      console.log("signedTx", signedTx);
      if(signedTx == null || signedTx.txID == null) {
        throw '交易签名失败';
      }


      //Step 3: 交易广播上链
      var broastTx = await tronWeb.trx.sendRawTransaction(signedTx);
      console.log("broastTx", broastTx);
      if(broastTx == null || broastTx.txid == null || broastTx.result != true) {
        throw '交易广播上链失败';
      }

      result = broastTx;
    }catch(error) {
      console.error("transactionOfTrc20 error", error);
    }
    return result;
  },


  /**
   * 获取交易信息
   * @param {String} txID 交易ID
   * @returns Object
   */
  async getTransactionInfo(txID) {
    let result = null;
    try {
      result = await tronWeb.trx.getTransactionInfo(txID);
    }catch(error) {
      console.error("transactionOfTrc20 error", error);
    }
    return result;
  }


};
