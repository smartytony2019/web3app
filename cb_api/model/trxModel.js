const web3 = require("../utils/web3Util").getWeb3();
const tronWeb = require("../utils/trxUtil").getTronWeb();
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
   * @param {string} contractAddress 合约地址
   * @param {string} address 查询地址
   * @param {string} privateKey 私钥
   * @returns USDT余额
   */
  async getBalanceOfTrc20(contractAddress, address, privateKey) {
    let result = 0;
    try {
      tronWeb.setPrivateKey(privateKey);
      let contract = await tronWeb.contract().at(contractAddress);
      let balance = await contract.balanceOf(address).call();
      result = web3.utils.fromWei(`${balance.toString()}`, "ether");
    } catch (error) {
      console.error("getBalanceOfTrc20 error", error);
    }
    return result;
  },



  /**
   * 合约转帐
   * @param {String} contractAddress 合约地址
   * @param {String} amount 金额
   * @param {String} toAddress 收款帐户
   * @param {String} privateKey 私钥
   * @returns Object
   */
   async transferTo(contractAddress, amount, toAddress, privateKey) {
    let result = null;
    try {

      // 设置私钥
      tronWeb.setPrivateKey(privateKey)

      // 获取合约实例
      let contract = await tronWeb.contract().at(contractAddress);

      // 转帐交易
      let hash = await contract.transferTo(toAddress, amount).send();

      result = hash;
    } catch (error) {
      console.error("transferTo error", error);
    }
    return result;
  },


  /**
   * TRX转帐
   * @param {String} fromAddress 转款帐户
   * @param {String} amount 金额
   * @param {String} toAddress 收款帐户
   * @param {String} privateKey 私钥
   * @returns Object
   */
  async transactionOfTrx(fromAddress, amount, toAddress, privateKey) {
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
      const signedtxn = await tronWeb.trx.sign(tradeobj, privateKey);
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
   * @param {String} privateKey 私钥
   * @returns Object
   */
  async transactionOfTrc20(contractAddress, fromAddress, amount, toAddress, privateKey) {
    let result = null;
    try {
      //Step 1: 调用智能合约
      const parameter = [
        { type: "address", value: toAddress },
        { type: "uint256", value: tronWeb.toHex(amount) },
      ];
      var tx = await tronWeb.transactionBuilder.triggerSmartContract(contractAddress, "transfer(address,uint256)", {}, parameter, tronWeb.address.toHex(fromAddress));
      if(tx == null || tx.result == null || tx.result.result != true) {
        throw '调用智能合约失败';
      }


      //Step 2: 交易签名
      var signedTx = await tronWeb.trx.sign(tx.transaction, privateKey);
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
  },

  /**
   * 获取块哈希
   * @param {Integer} blockHeight 块高度
   * @returns Object
   */
  async getBlockHash(blockHeight) {
    let result = null;
    try {
      result = await tronWeb.trx.getBlock(blockHeight);
    }catch(error) {
      console.error("getBlockHash error", error);
    }
    return result;
  },

  /**
   * 获取事件信息
   * @param {String} contractAddress 合约地址
   * @param {String} eventName 事件名称
   * @returns Array
   */
  async getEventResult(contractAddress, eventName) {
    let result = null;
    try {
      result = await tronWeb.getEventResult(contractAddress, {eventName: eventName, size: 10})
    }catch(error) {
      console.error("getEventResult error", error);
    }
    return result;
  },

  /**
   * 检查地址是否合法
   * @param {String} address 地址
   * @returns Boolean
   */
  async checkAddressIsValid(address) {
    if(address == null || address == "" || typeof address != 'string') {
      return false;
    }

    let firstWorld = address.substring(0, 1);
    if(firstWorld !== 'T') {
      return false;
    }

    let len = address.length
    if(len != 34) {
      return false;
    }

    return true;
  },


  /**
   * 检查私钥是否合法
   * @param {String} address 地址
   * @returns Boolean
   */
  async decodePrivateKey(privateKey) {
    if(privateKey == null || privateKey == "" || typeof privateKey != 'string') {
      return null;
    }

    let len = privateKey.length
    if(len != 64) {
      return null;
    }

    return privateKey;
  }



};
