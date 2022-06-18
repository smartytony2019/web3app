let instance = require("./getTronweb")


//-- 获取余额
//tronWeb.trx.getBalance(address).then(res=>{
//    console.log(res)
//});



//-- 创建一个未签名的TRX转账交易
//tronWeb.transactionBuilder.sendTrx(to,amount,from).then(res=>{
//    console.log(res)
//})


//-- 创建一个未签名的 TRC10转账交易
//tronWeb.transactionBuilder.sendToken(to,amount,tokenID,from).then(res=>{
//    console.log(res)
//})


//4156fd2d5ae75717375aac0c6ec4051fff00505886

//tronWeb.contract().at(contractAddress).then(res=>{
//    console.log(res)
//})


class Trx {
    tronWeb
    contractAddress

    constructor(tronWeb, contractAddress) {
        this.tronWeb = tronWeb
        this.contractAddress = contractAddress
    }

    async getContract() {
       return await this.tronWeb.contract().at(this.contractAddress)
    }

    async sendRawTransaction(fromAddress,privateKey,toAddress,amount,remark){
        try {
            const parameter = [
                {
                    type:'address',
                    value:toAddress
                },
                {
                    type:'uint256',
                    value:amount
                }
            ]
            const transaction = await this.tronWeb.transactionBuilder.triggerSmartContract(this.contractAddress, "transfer(address,uint256)", {},parameter,this.tronWeb.address.toHex(fromAddress))
            transaction.transaction.data = remark
            let signedTx  = await this.tronWeb.trx.sign(transaction.transaction,privateKey)
            await this.tronWeb.trx.sendRawTransaction(signedTx);
            return signedTx.txID
        }catch(e) {
            console.log(e)
        }
    }


    async triggerSmartContract() {
        try {
            let address = 'TYXxNR3UtgD3s2ADTQAhBE9AEBf7fKfe6B';
            let contract = await this.tronWeb.contract().at(this.contractAddress);
            let result = await contract.balanceOf(address).call();
            console.log('result: ', result);
        } catch(error) {
            console.error("trigger smart contract error",error)
        }
    }

    async transfer() {
        try {

          const privateKey = "f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703"; 
          var fromAddress = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu"; //address _from
          var toAddress = "TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe"; //address _to
          var amount = 10000000; //amount
          console.log('current block', await this.tronWeb.trx.getCurrentBlock());
          //创建一个未签名的TRX转账交易
          const tradeobj = await this.tronWeb.transactionBuilder.sendTrx(
                toAddress,
                amount,
                fromAddress
          );
          //签名
          const signedtxn = await this.tronWeb.trx.sign(
                tradeobj,
                privateKey
          );
          //广播
          const receipt = await this.tronWeb.trx.sendRawTransaction(
                signedtxn
          ).then(output => {console.log('- Output:', output, '\n');});

        } catch(error) {
            console.error("trigger smart contract error",error)
        }
    }


    async sendTrx(to,amount,from, privateKey) {
      try {
        console.log('------------ 创建交易  -----------------')
        let unsignedTxn = await this.tronWeb.transactionBuilder.sendTrx(to,amount,from)
        console.log(unsignedTxn);


        console.log('------------ 签名交易  -----------------')
        const signedTxn = await this.tronWeb.trx.sign(unsignedTxn, privateKey);
        console.log(signedTxn)


        console.log('------------ 广播交易  -----------------')
        const receipt = await this.tronWeb.trx.sendRawTransaction(signedTxn);
        console.log(receipt)

      }catch(e) {
        console.error("sendTrx error",e)
      }

    }

}



const Web3 = require('web3');

(async function () {

    const web3 = new Web3(Web3.givenProvider || "http://localhost:8545");

    let from = 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu';
    let to = 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe';
    let value = "1";
    let amount = web3.utils.toWei(value,'ether');
    let tokenID = '1000086'
    let privateKey = 'f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703';

    //let contractAddress = 'TEEgkVMvVoyp3gNyDE7mj3odJXKVNs1bgd';
    //let contractAddress = '412ecde7f620e4106c4904308ce4a12c7703d57307';

    // console.log(instance.address.toHex('TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR'))
    let contractAddress = instance.address.toHex('TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR');

    let trx = new Trx(instance,contractAddress);
    
    //TRC20转帐
    // let a = await trx.sendRawTransaction(address, privateKey, to, amount, "helloworld")
    // console.log(a)

    trx.transfer();
    // trx.sendTrx(to,100000, from, privateKey)


})()
