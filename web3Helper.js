const Web3 = require('web3');

module.exports = {
  instance() {
    const web3 = new Web3(Web3.givenProvider || "http://localhost:8545");
    return web3;
  },

  async sendTransaction(from, amount, to, privateKey) {
    
    const web3 = this.instance();

    // 获取指定账户地址的交易数
    let nonce = await web3.eth.getTransactionCount(from);
    
    // 将 ether 转为 wei
    let value = web3.utils.toWei(amount,'ether');
    
    // 获取当前gasprice
    let gasPrice = await web3.eth.getGasPrice();
    
    // 以太币转账参数    
    let txParms = {
        from: from,
        to: to,
        nonce: nonce,
        gasPrice: gasPrice,
        data: '0x00', // 当使用代币转账或者合约调用时
        value: value // value 是转账金额
    }
    

    // 获取一下预估gas
    let gas = await web3.eth.estimateGas(txParms);
    txParms.gas = gas;
    console.log(JSON.stringify(txParms));
    
    // 用密钥对账单进行签名
    let signTx = await web3.eth.accounts.signTransaction(txParms,privateKey)

    // 将签过名的账单进行发送
    try {
        await web3.eth.sendSignedTransaction(signTx.rawTransaction, function(error, hash){
            if (!error) {
              console.log('转帐成功', hash)
            } else {
              console.log('转帐失败', error)
            }
        })
    } catch (error) {
        console.log(error)
    }
  },

  async getBalance(address) {
    const web3 = this.instance();
    return await web3.eth.getBalance(address)
  }


}