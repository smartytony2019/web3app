


(async function () {

  const web3Helper = require("./web3Helper")
  const tokenContractABI = require("./ABI.json")
  // const from = '0x0ECf538453A4F4530A3921AdC645f8a6ee29674F';
  // const amount = "2";
  // const to = '0x0A22cB119eB3A2b24CDc214D3ce09cd45a5c3350';
  // const privateKey = '0x1b3dcdea068f9679cac65f0a05e29bb21a710ccd6acda2ed4167e2dbb43ec5bd';

  const from = '0xcf5330F0c3ce7590C71cDd17739Ad37Cb932C3D0';
  let amount = "1";
  const to = '0x309ad20D059b371532856296d61279dF65a1f1FE';
  const privateKey = 'b6c6b9dd8d94d41d3a470d769c9cc31e4035aa450320a8769b69eb97c18179ea';

  //合约地址
  const contractAddress = '0x3d2986709F64B9A485bCFa23B6d7fC1CF85221A4';

  let web3 = web3Helper.instance();

  // let tokenContract = web3Helper.tokenContract(tokenContractABI, contractAddress);
  // let a = await tokenContract.methods.balanceOf(from).call()
  // let b = await web3.eth.getTransactionCount(from);
  // let c = tokenContract.methods.transfer(to, web3.utils.toWei('5000000', 'wei')).encodeABI()
  // console.log(a, b,c);
  // var Tx = require('ethereumjs-tx').Transaction;
  // var pk = Buffer.from('b6c6b9dd8d94d41d3a470d769c9cc31e4035aa450320a8769b69eb97c18179ea', 'hex');
  // let count = await web3.eth.getTransactionCount(from);
  // let nonce = await web3.utils.toHex(count);
  // var rawTx = {
  //   "from": from,
  //   "nonce": nonce,
  //   "gasLimit": web3.utils.toHex(8000000),
  //   "gasPrice": web3.utils.toHex(10e9),
  //   "to": contractAddress,
  //   "value": '0x0',
  //   "data": c,
  //   "chainId": 0x04 //4:Rinkeby, 3:Ropsten, 1:mainnet
  // }

  // var tx = new Tx(rawTx, {'chain':'rinkeby'});
  // tx.sign(pk);
  
  // var serializedTx = tx.serialize();
  // console.log(serializedTx.toString('hex'));
  // // 0xf889808609184e72a00082271094000000000000000000000000000000000000000080a47f74657374320000000000000000000000000000000000000000000000000000006000571ca08a8bbf888cfa37bbf0bb965423625641fc956967b81d12e23709cead01446075a01ce999b56a8a88504be365442ea61239198e23d1fce7d00fcfc5cd3b44b7215f
  // console.log('------- start ---------')
  // var receipt = await web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'));
  // console.log(`Receipt info:  ${JSON.stringify(receipt, null, '\t')}`);






  //- eth转帐
  // await web3Helper.sendTransaction(from, amount, to, privateKey);
  


  //- USDT转帐
  amount = parseInt(amount) * Math.pow(10, 6) + '';
  await web3Helper.sendERC20Transaction(from, amount, to, privateKey,tokenContractABI, contractAddress);
  


  //- 获取余额
  // const balance = await web3Helper.getBalance(to);
  // console.log(balance);
  

})();

