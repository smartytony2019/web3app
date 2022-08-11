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
                    value:this.tronWeb.toHex(amount)
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
          var amount = 1000000; //amount
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


    async sendTrx(to,amount,from, privateKey, remark) {
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

    async test(fromAddress,privateKey,toAddress,amount,remark) {

      /*let contract = await this.tronWeb.contract().at("TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR");
      let result = await contract.transfer(
        toAddress,
         this.tronWeb.toHex(55 * Math.pow(10,18))
       ).send({
         feeLimit: 10000000
       }).then(output => {console.log('- Output:', output, '\n');});
       console.log('result: ', result);*/

      //转账方式2
      const parameter = [{type:'address',value:toAddress},{type:'uint256',value:this.tronWeb.toHex(33 * Math.pow(10,18))}]
      var tx  = await this.tronWeb.transactionBuilder.triggerSmartContract("TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR", "transfer(address,uint256)", {},parameter,this.tronWeb.address.toHex(fromAddress));
      // await this.tronWeb.transactionBuilder.addUpdateData(tx.transaction, this.tronWeb.address.toHex("123456"));
      tx.transaction.data = remark
      console.log(tx);
      var signedTx = await this.tronWeb.trx.sign(tx.transaction, privateKey);
      var broastTx = await this.tronWeb.trx.sendRawTransaction(signedTx);
      console.log(broastTx)

      // var contractAddress = "TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR";
      // //选择合约方法
      // let functionSelector = "transfer(address,uint256)"; 
      //   //根据方法构造参数
      // let parameter = [
      //   {type: "address",value: toAddress},
      //   { type: "uint256", value: Math.ceil(amount) }
      // ];
      // //额外参数
      // let options = {
      //    shouldPollResponse: false,
      //    feeLimit: 1000000 //1Trx
      // };
      // // const transaction = await this.tronWeb.transactionBuilder.triggerSmartContract(this.contractAddress, "transfer(address,uint256)", {},parameter,this.tronWeb.address.toHex(fromAddress))
           
      // // 构造智能合约交易信息
      // let res = await this.tronWeb.transactionBuilder.triggerSmartContract(contractAddress, functionSelector, options, parameter,this.tronWeb.address.toHex(fromAddress))
      //     .catch(err1 => {
      //       console.log(err1)
      //      // 构建交易信息失败
      //     return false;
      //   });

      //   console.log('1111', res);
      // // 向转账交易信息中追加 备注信息 
      // await this.tronWeb.transactionBuilder.addUpdateData(res.transaction, "备注信息", 'utf8');
      // console.log('22222')
      // // 对已经添加备注的交易信息进行签名
      // let sign = await this.tronWeb.trx.sign(res.transaction,privateKey).catch(err2 => {
      //   console.log('err2', err2)
      //   //签名失败
      //   return false;
      // });
      // console.log(sign)
      // // 将签名交易广播上链
      // let a = await this.tronWeb.trx.sendRawTransaction(sign).catch(outputErr => {
      //   //交易广播出错
      //   console.log(outputErr);
      //   return false;
      // });

      // console.log('result',a)

    }

    async getBalanceOfContract(fromAddress) {
      const trc20ContractAddress = "TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR";//contract address

      const web3 = new Web3(Web3.givenProvider || "http://localhost:8545");

      try {
          this.tronWeb.setPrivateKey('f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703')
          let contract = await this.tronWeb.contract().at(trc20ContractAddress);
          //Use call to execute a pure or view smart contract method.
          // These methods do not modify the blockchain, do not cost anything to execute and are also not broadcasted to the network.
          let result = await contract.balanceOf(fromAddress).call();
          console.log('result: ', web3.utils.fromWei(`${result.toString()}`, 'ether'));
      } catch(error) {
          console.error("trigger smart contract error",error)
      }
    }

    async createAccount() {
      let account = await instance.createAccount();
      console.log(account);
    }

    async info() {
      let info = await this.tronWeb.trx.getTransactionInfo("2e5955d546aa725fd26532045c186b9bd1558dd034cb8ad1404fd4cad91dc1bb");
      console.log(info);
    }


}



const Web3 = require('web3');

(async function () {

    const web3 = new Web3(Web3.givenProvider || "http://localhost:8545");

    let from = 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu';
    let to = 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe';
    let value = "1";
    let amount = web3.utils.toWei(value,'Mwei');
    let privateKey = 'f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703';

    //let contractAddress = 'TEEgkVMvVoyp3gNyDE7mj3odJXKVNs1bgd';
    //let contractAddress = '412ecde7f620e4106c4904308ce4a12c7703d57307';

    // console.log(instance.address.toHex('TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR'))


    let tronWeb = instance;
    let contractAddress = 'TQcF1rd1BiSFm7F5S6QsZNGZ2vp2rho846';

    tronWeb.setPrivateKey('0d35dba8af935d575924cd0d3afd2479248de12aa0f13f547e2e9debcdd025c5')
    let contract = await tronWeb.contract().at(contractAddress);

    // 查询合约余额
    // let t1 = await contract.getBalanceOfContract().call();
    // console.log(t1.toNumber());

    let callback = (info) => {
      console.log('callback',info);
    };
    let to_address = 'TPYXWcPZ9DC9R4PvQniTPSjUegUyBD3kJJ';
    // console.log('watching', to_address);
    // contract.Transfer().watch((err, res) => {
    //   console.log('res', res);
    //   if(res && res.transaction && res.transaction === txID) {
    //     callback(res);
    //     return;
    //   }
    // })
    let hash = await contract.transferTo(to_address, 1000).send();
    console.log(hash);

    let i = 0;
    let checkConfirm = async()=>{
      if(i>9){
        clearInterval(timer)
      }

      let res = await tronWeb.getEventResult(contractAddress, {eventName:"Transfer",size:10})
      let checkEvent = async () => {
        return new Promise((resolve, reject) => {
          for(let t = 0; t < res.length; t++ ){
            if(hash === res[t].transaction){
              clearInterval(timer)
              resolve(res[t])
            }
          }
          if(i === 10){
            resolve(null)
          }
        });
      }
      let event = await checkEvent()
      if(event !== null){
        console.log('event',event)
      } else{
        console.log("出错啦！请刷新后重试！")
      }

      i += 1;
    };
    let timer = setInterval(checkConfirm, 2000)

    

    // let trx = new Trx(instance, contractAddress);




    // let tmp = await instance.trx.getBlock(27464550);
    // console.log(tmp);

    // trx.info();

    // trx.test(from, privateKey, to, '1000000', "helloworld");
    
    //TRC20转帐
    // let a = await trx.sendRawTransaction(from, privateKey, to, 1000000, "helloworld")
    // console.log(a)

    // trx.transfer();
    // trx.sendTrx(to,1000000, from, privateKey, "helloworld")


    // trx.getBalanceOfContract(from);





})()
