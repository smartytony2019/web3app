


(async function () {

  const web3Helper = require("./web3Helper")
  const from = '0x0ECf538453A4F4530A3921AdC645f8a6ee29674F';
  const amount = "2";
  const to = '0x0A22cB119eB3A2b24CDc214D3ce09cd45a5c3350';
  const privateKey = '0x1b3dcdea068f9679cac65f0a05e29bb21a710ccd6acda2ed4167e2dbb43ec5bd';
  
  //- eth转帐
  await web3Helper.sendTransaction(from, amount, to, privateKey);
  
  
  //- 获取余额
  const balance = await web3Helper.getBalance(to);
  console.log(balance);
  

})();

