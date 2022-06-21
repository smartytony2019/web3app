

const Web3 = require('web3');
const Tx = require('ethereumjs-tx').Transaction;


const PROJECT_ID = '5c36494f830148a4a2679e30ba9ca178';


(async function () {

  const web3 = new Web3(new Web3.providers.HttpProvider(`https://rinkeby.infura.io/v3/`+PROJECT_ID))


  //创建帐号
  // let accounts = web3.eth.accounts.create();
  // console.log(accounts) //address:0x5abe74E88F3AecE7b34621A691EE701F4D7B143A privateKey:0x7e048ccaeb42ccea62ed84b8836b7285e60df0271f6bd137c18f8d055667c911


  //根据私钥获取帐号  
  // let account = web3.eth.accounts.privateKeyToAccount('0x7e048ccaeb42ccea62ed84b8836b7285e60df0271f6bd137c18f8d055667c911');
  // console.log(account)

  


})();




