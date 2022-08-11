

const resultModel = require("../model/resultModel")
const R = require("../utils/R")

module.exports = {
    async find (ctx) {
      let data = ctx.request.body
      let sn = data.sn
      let result = await resultModel.find(sn)
      ctx.body = R.success(result)
    },
  
    async open (ctx) {
      let data = ctx.request.body
      let sn = data.sn
      let toAddress = data.toAddress

      const params = {
        sn,
        toAddress
      }
      let result = await resultModel.insert(params)
      ctx.body = R.success(result)
    }
}


