
const TronWeb = require('tronweb');
let config = require("../config/config")
module.exports = {
    getTronWeb(){
      let env = process.env.NODE_ENV;
      let conf = config[env];

      let fullNode = conf.trx.fullNode;
      let solidityNode = conf.trx.solidityNode;
      let eventServer = conf.trx.eventServer;

      let tronWeb = new TronWeb(
          fullNode,
          solidityNode,
          eventServer
          // privateKey
      );
      return tronWeb;
    }
}
