const Web3 = require('web3');
const Tx = require('ethereumjs-tx').Transaction;
module.exports = {
  instance() {
    // const web3 = new Web3(Web3.givenProvider || "https://rinkeby.infura.io/v3/d3df59f4140d47b087775e2c1b4be8ed");
    const web3 = new Web3(new Web3.providers.HttpProvider(`https://rinkeby.infura.io/v3/d3df59f4140d47b087775e2c1b4be8ed`))
    return web3;
  },

  getWSSInstance() {
    const web3 = new Web3(`wss://rinkeby.infura.io/ws/v3/d3df59f4140d47b087775e2c1b4be8ed`)
    return web3;
  },

  tokenContract(tokenContractABI, contractAddress) {
    const web3 = this.instance();
    const tokenContract = new web3.eth.Contract(tokenContractABI, contractAddress);
    return tokenContract;
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

  async sendERC20Transaction(from, amount, to, privateKey, tokenContractABI, contractAddress) {


    const web3 = this.instance();
    let tokenContract = this.tokenContract(tokenContractABI, contractAddress);
    let count = await web3.eth.getTransactionCount(from);
    let nonce = await web3.utils.toHex(count);
    let txParms = {
      "from": from,
      "nonce": nonce,
      "gasLimit": web3.utils.toHex(8000000),
      "gasPrice": web3.utils.toHex(10e9),
      "to": contractAddress,
      "value": '0x0',
      "data": tokenContract.methods.transfer(to, web3.utils.toWei(amount, 'wei')).encodeABI()
    }
    // let gas = await web3.eth.estimateGas(txParms)+1000;
    // txParms.gas = gas;

    let tx = new Tx(txParms, {chain: 'rinkeby'});
    tx.sign(Buffer.from(privateKey, 'hex'));
    let serializedTx = tx.serialize();
    let raw = '0x' + serializedTx.toString('hex')
    try {
      await web3.eth.sendSignedTransaction(raw).on('receipt', console.log);
    }catch(error) {
      console.log(error)
    }


    // // 获取一下预估gas
    // let gas = await web3.eth.estimateGas(txParms);
    // txParms.gas = gas;
    // console.log(JSON.stringify(txParms));
    
    // // 用密钥对账单进行签名
    // let signTx = await web3.eth.accounts.signTransaction(txParms,privateKey)
    // console.log(JSON.stringify(signTx));

    // // 将签过名的账单进行发送
    // try {
    //     await web3.eth.sendSignedTransaction(signTx.rawTransaction, function(error, hash){
    //         if (!error) {
    //           console.log('转帐成功', hash)
    //         } else {
    //           console.log('转帐失败', error)
    //         }
    //     })
    // } catch (error) {
    //     console.log(error)
    // }

  },

  async getBalance(address) {
    const web3 = this.instance();
    return await web3.eth.getBalance(address)
  },


  async getErc20TransfersByBlock() {

  },

  async sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }


}