
let sqlite = require("../utils/sqlite3Util")

module.exports = {
  async query(game_id) {
    let query = `select * from t_open_result where game_id = ${game_id} order by num desc limit 5`;
    let result = await sqlite.all(query);
    return result;
  }
}
