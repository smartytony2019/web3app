(async ()=>{

    let sqlite = require("./utils/sqlite3Util")
    let common = require("./utils/commonUtil")
    let dayjs = require('dayjs')



    //判断表是否存在(没有则创建)
    let query = `SELECT name FROM sqlite_master WHERE type='table' AND name='t_hash_result'`;
    let result = await sqlite.hasTable(query);
    if(result == false) {
        //query = 'CREATE TABLE t_hash_block(sn varchar(100), merchant_id int, num varchar(10), end_time varchar(20), end_timestamp int)';
        query = 'CREATE TABLE t_hash_result(sn varchar(100), to_address varchar(100), txID varchar(70), block_hash varchar(70), block_height varchar(70), open_time timestamp, open_timestamp bigint, network varchar(20))';
        var r = await sqlite.run(query)
        if(r) console.log("t_hash_result table created")
    }

    //202207010143
    // query = `SELECT name FROM sqlite_master WHERE type='table' AND name='t_hash_result'`;
    // result = await sqlite.hasTable(query);
    // if(result == false) {
    //     query = 'CREATE TABLE t_hash_result(game_id int, num varchar(20), txID varchar(70), block_hash varchar(70), block_height varchar(70), open_time timestamp, open_timestamp bigint, network varchar(20))';
    //     let r = await sqlite.run(query)
    //     if(r) console.log("t_hash_result table created")
    // }



    // let minute = 5;
    // let gameId = 5;
    // let end = 60 * 24 / minute;
    // let fixed = 3;
    // let rr = await common.genExpect(gameId, end, fixed, minute)
    // for(let i=0; i<rr.length; i++) {
    //     let item = rr[i];
    //     let values = `${item.gameId}, '${item.num}', '${item.endTime}', '${item.endTimestamp}'`;
    //     query = `INSERT INTO t_expect(game_id, num, end_time, end_timestamp) VALUES(${values})`;
    //     await sqlite.run(query)
    // }


    // let open_time = "2022-06-27 16:00:00";
    // let open_timestamp = await common.parseTimestamp(open_time);
    // let values = `5, '202207010280', 'b91aa7385e39beb5944ac64410fbbd2ffd67f1c1678df0715559c7138f38a74a', '', '', '${open_time}', ${open_timestamp}, 'nile'`;
    // query = `INSERT INTO t_hash_result(game_id, num, txID, block_hash, block_height, open_time, open_timestamp, network) VALUES(${values})`;
    // await sqlite.run(query)



})();

