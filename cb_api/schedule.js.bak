let common = require("./utils/commonUtil")
process.env.NODE_ENV = common.env();
process.env.TZ = 'Asia/Shanghai';

let schedule = require('node-schedule');
let dayjs = require('dayjs')
let sqlite = require("./utils/sqlite3Util")
let config = require("./config/config")
let trxModel = require("./model/trxModel")



class Result {

  constructor(game_id) {
    this.game_id = game_id;
  }

  /**
   * 开奖
   * @returns void
   */
  async open() {
    console.log(`game_id:${this.game_id} ************** 开奖 - 开始...  ${dayjs().format("YYYY-MM-DD HH:mm:ss")}`);
    try {
  
      //Step 1: 获取期号
      let current = await common.parseSecond(dayjs().format('HH:mm:ss'))
      let query = `select * from t_expect where game_id = ${this.game_id} and ${current} > end_timestamp order by num desc limit 1`;
      let expect = await sqlite.get(query);
      if(expect == undefined || expect.game_id <= 0) {
        throw 'get expect error';
      }
  
  
      //Step 2: 查询是否已开奖
      let num = `${dayjs().format("YYYYMMDD")}${expect.num.padStart(4, '0')}`;
      // let open_timestamp = await common.parseTimestamp(date);
      query = `select count(*) as record from t_hash_result where game_id = ${this.game_id} and num = '${num}' limit 1`;
      let open_result = await sqlite.get(query);
      if(open_result != undefined && open_result.record > 0) {  //说明已开奖
        return;
      }
  
      //Step 3: 转帐开奖
      //Step 3.1 根据当前环境获取配置
      let account = config[process.env.NODE_ENV];
      if(account == undefined || account.trx.privateKey == null) {
        return;
      }
  
      //Step 3.2: 转帐
      let network = account.trx.network;
      let fromAddress = account.trx.fromAddress;
      let amount = parseInt(expect.num);
      let toAddress = account.trx.toAddress;
      let privateKey = account.trx.privateKey;
      let result = await trxModel.transactionOfTrx(fromAddress, amount, toAddress, privateKey);
      if(result == undefined || result.result == false || result.txid == undefined) {
        return;
      }
  
      //Step 4: 保存数据库
      let open_time = dayjs().format("YYYY-MM-DD HH:mm:ss");
      let open_timestamp = await common.parseTimestamp(open_time);
      let values = `${this.game_id}, ${num}, '${result.txid}', '', '', '${open_time}', ${open_timestamp}, '${network}'`;
      query = `INSERT INTO t_hash_result(game_id, num, txID, block_hash, block_height, open_time, open_timestamp, network) VALUES(${values})`;
      await sqlite.run(query)
    }catch(error) {
      console.error(error);
    }
  
    console.log(`game_id:${this.game_id} ************** 开奖 - 结束...  ${dayjs().format("YYYY-MM-DD HH:mm:ss")}`);
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
      if(result == undefined || result.txID == '') {
        return;
      }
    
      let transactionInfo = await trxModel.getTransactionInfo(result.txID);
      if(transactionInfo == undefined || transactionInfo.id == undefined) {
        return;
      }

      let blockInfo = await trxModel.getBlockHash(transactionInfo.blockNumber);
      console.log("blockInfo", blockInfo);
      if(blockInfo == undefined || blockInfo.blockID == undefined) {
        return;
      }
    
      query = `update t_hash_result set block_height = '${transactionInfo.blockNumber}', block_hash = '${blockInfo.blockID}' where txID = '${transactionInfo.id}'`;
      await sqlite.run(query);
    
      console.log('************** 查询块高度   -    end...  '+transactionInfo.id);
    } catch(error) {
      console.error(error);
    }
  }
}



//开奖
schedule.scheduleJob('0/5 * * * * *', async function(){
  let result = new Result(5);
  await result.open();
});


//***********************  查询块信息  ***********************
schedule.scheduleJob('0/5 * * * * *', async function() {
  let result = new Result();
  await result.queryBlockInfo();
});
