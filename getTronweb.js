const TronWeb = require('tronweb');

const fullNode = 'https://api.nileex.io/';
const solidityNode = 'https://api.nileex.io';
const eventServer = 'https://event.nileex.io/';
const privateKey = 'f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703';

let tronWeb = new TronWeb(
    fullNode,
    solidityNode,
    eventServer,
    privateKey
);


module.exports = tronWeb;