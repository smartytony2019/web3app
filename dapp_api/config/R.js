const constant = require("./constant")
module.exports = {

  success(data,msg) {
    return {code: constant.SUCCESS,data: data, msg: msg}
  },

  fail(msg) {
    return {code: constant.FAIL,data: {}, msg: msg}
  }
}