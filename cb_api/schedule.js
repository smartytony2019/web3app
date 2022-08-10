let common = require("./utils/commonUtil")
process.env.NODE_ENV = common.env();
process.env.TZ = 'Asia/Shanghai';

let schedule = require('node-schedule');
let dayjs = require('dayjs')
let sqlite = require("./utils/sqlite3Util")
let config = require("./config/config")
let trxModel = require("./model/trxModel")



class Result {

  constructor() {
  }

  /**
   * 开奖
   * @returns void
   */
  async open() {
    console.log(` ************** 开奖 - 开始...  ${dayjs().format("YYYY-MM-DD HH:mm:ss")}`);
    try {
  
  
      //Step 2: 查询是否已开奖
      let query = `select * from t_hash_result where txID = ''`;
      let item = await sqlite.get(query);
      if(item === undefined || item.sn === undefined) {  //说明已开奖
        return;
      }
  
      console.log(item);
      //Step 3: 转帐开奖
      //Step 3.1 根据当前环境获取配置
      let account = config[process.env.NODE_ENV];
      if(account == undefined || account.trx.privateKey == null) {
        return;
      }
  
      //Step 3.2: 转帐
      let network = account.trx.network;
      let fromAddress = account.trx.fromAddress;
      let amount = parseInt(10000);
      let toAddress = account.trx.toAddress;
      let privateKey = account.trx.privateKey;
      let result = await trxModel.transactionOfTrx(fromAddress, amount, toAddress, privateKey);
      if(result == undefined || result.result == false || result.txid == undefined) {
        return;
      }
  
      //Step 4: 保存数据库
      let sql = `UPDATE t_hash_result SET txID='${result.txid}', network='${network}' where sn='${item.sn}'`;
      await sqlite.run(sql)


      console.log(` ************** 开奖 - 结束...  ${dayjs().format("YYYY-MM-DD HH:mm:ss")}`);
    }catch(error) {
      console.error(error);
    }
  }

  /**
   * 查询块高度
   * @returns void
   */
  async queryBlockInfo() {
    try {
      console.log('************** 查询块高度 - starting...  '+dayjs().format("YYYY-MM-DD HH:mm:ss"));
      let query = `select * from t_hash_result where block_height = '' limit 1`;
      let result = await sqlite.get(query)
      console.log("result", result);
      if(result === undefined || result.txID === '') {
        return;
      }
    
      let transactionInfo = await trxModel.getTransactionInfo(result.txID);
      console.log("transactionInfo", transactionInfo);
      if(transactionInfo == undefined || transactionInfo.id == undefined) {
        return;
      }

      let blockInfo = await trxModel.getBlockHash(transactionInfo.blockNumber);
      console.log("blockInfo", blockInfo);
      if(blockInfo == undefined || blockInfo.blockID == undefined) {
        return;
      }
    
      let open_time = dayjs().format("YYYY-MM-DD HH:mm:ss");
      let open_timestamp = await common.parseTimestamp(open_time);
      let sql = `update t_hash_result set block_height = '${transactionInfo.blockNumber}', block_hash = '${blockInfo.blockID}', open_time='${open_time}', open_timestamp='${open_timestamp}' where txID = '${transactionInfo.id}'`;
      await sqlite.run(sql);
    
      console.log('************** 查询块高度   -    end...  '+transactionInfo.id);
    } catch(error) {
      console.error(error);
    }
  }
}



//开奖
schedule.scheduleJob('0/5 * * * * *', async function(){
  let result = new Result();
  await result.open();
});


//***********************  查询块信息  ***********************
schedule.scheduleJob('0/5 * * * * *', async function() {
  let result = new Result();
  await result.queryBlockInfo();
});
