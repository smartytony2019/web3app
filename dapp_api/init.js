// const sqlite3 = require('sqlite3').verbose()
// var db = new sqlite3.Database(
//     './sqlite3/api.db', 
//     sqlite3.OPEN_READWRITE, 
//     function (err) {
//         if (err) {
//             return console.log(err.message)
//         }
//         console.log('connect database successfully')
//     }
// )

// db.run('CREATE TABLE t_expect(game_id int, num varchar(10), start_time varchar(20), end_time varchar(20))', function (err) {
//     if (err) {
//         return console.log(err)
//     }
//     console.log('create table user')
// })

// db.run('INSERT INTO t_expect(game_id, num, start_time, end_time) VALUES(?)', ['jack_tony'], function (err) {
//     if (err) {
//         return console.log('insert data error: ', err.message)
//     }
//     console.log('insert data: ', this)
// })


// db.all("select * from t_expect limit 2",function(err,row) {
//     console.log(row);
// })


(async ()=>{

    let sqlite = require("./utils/sqlite3Util")
    let commonHelper = require("./utils/commonUtil")


    //判断表是否存在(没有则创建)
    let query = `SELECT name FROM sqlite_master WHERE type='table' AND name='t_expect'`;
    let result = await sqlite.hasTable(query);
    if(result == false) {
        query = 'CREATE TABLE t_expect(game_id int, num varchar(10), end_time varchar(20), end_timestamp int)';
        var r = await sqlite.run(query)
        if(r) console.log("t_expect table created")
    }

    query = `SELECT name FROM sqlite_master WHERE type='table' AND name='t_open_result'`;
    result = await sqlite.hasTable(query);
    if(result == false) {
        query = 'CREATE TABLE t_open_result(game_id int, num varchar(10), hash_code varchar(70), open_time timestamp)';
        var r = await sqlite.run(query)
        if(r) console.log("t_open_result table created")
    }

    let minute = 1;
    let gameId = 1;
    let end = 60 * 24 / minute;
    let fixed = 4;
    let rr = await commonHelper.genExpect(gameId, end, fixed, minute)
    for(let i=0; i<rr.length; i++) {
        let item = rr[i];
        let values = `${item.gameId}, '${item.num}', '${item.endTime}', '${item.endTimestamp}'`;
        query = `INSERT INTO t_expect(game_id, num, end_time, end_timestamp) VALUES(${values})`;
        await sqlite.run(query)
    }


    // let values = `5, '0279', '345988d3e54976500fb2bcdd91b4048033b737b30c2aa93204ba66657bc12670', '2022-06-27 00:00:00'`;
    // query = `INSERT INTO t_open_result(game_id, num, hash_code, open_time) VALUES(${values})`;
    // await sqlite.run(query)



    // let dayjs = require('dayjs')
    // let current = await commonHelper.parseSecond(dayjs().format('HH:mm:ss'))
    // query = `select * from t_expect where game_id = 5 and ${current} < end_timestamp order by num asc limit 1`;
    // console.log(query);
    // result = await sqlite.all(query);
    // console.log(result);



})();

