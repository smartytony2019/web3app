
let sqlite = require("../utils/sqlite3Util")

module.exports = {
  async find(sn) {
    let query = `select * from t_hash_result where sn = '${sn}' limit 1`;
    let result = await sqlite.get(query);
    return result;
  },
  async insert(params) {
    let sql = `insert into t_hash_result(sn, merchant_id, block_height, txID) values ('${params.sn}', ${params.merchant_id}, '', '')`;
    let result = await sqlite.run(sql);
    return result;
  },
}
 