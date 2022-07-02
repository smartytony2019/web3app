

const resultModel = require("../model/resultModel")
const R = require("../utils/R")

module.exports = {
    async result (ctx) {
        let game_id = ctx.params.game_id
        let result = await resultModel.query(game_id)
        ctx.body = R.success(result)
    }
}


