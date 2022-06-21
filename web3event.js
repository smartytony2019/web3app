


(async function () {

  const web3Helper = require("./web3Helper")
  const tokenContractABI = require("./ABI.json")
  const abiDecoder = require('abi-decoder');
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
  let tokenContract = web3Helper.tokenContract(tokenContractABI, contractAddress);

  const topics0 = '0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef';
  const topics1 = '0x000000000000000000000000cf5330f0c3ce7590c71cdd17739ad37cb932c3d0';
  const topics2 = '0x000000000000000000000000309ad20d059b371532856296d61279df65a1f1fe';
    let options = {
        fromBlock: 0,                  //Number || "earliest" || "pending" || "latest"
        toBlock: 'latest',
        topics: [topics0,topics1,topics2]
    };

    // while(true) {
    //     const blockLogs = await tokenContract.getPastEvents('Transfer', options);
    //     const transfers = [];
    //     for (const log of blockLogs) {
    //         console.log(log);
    //         // todo get erc20 decimals
    //         // const DECIMALS_OF_ERC20 = null;
    //         // const decodeData = abiDecoder.decodeLogs([log])[0];
    //         // const from = decodeData.events[0].value;
    //         // const to = decodeData.events[1].value;
    //         // const raw_value = new Decimal(decodeData.events[2].value);
    //         // const decimal = Decimal.pow(10, DECIMALS_OF_ERC20);
    //         // const value = raw_value.div(decimal);
    //         // console.debug(`from=${from} to=${to} value=${value} contract=${log.address}`);
    //         // transfers.push({from, to, value, contract: log.address});
    //     }
    //     console.log(transfers);

    //     await web3Helper.sleep(1000);
    // }


    // web3.eth.getPastLogs('Transfer',{
    //     address: contractAddress,
    //     topics: [topics0]
    // })
    // .then(console.log);

    web3 = web3Helper.getWSSInstance();
    var subscription = web3.eth.subscribe('logs', {
        address: to,
        topics: [topics0]
    }, function(error, result){
        if (!error) {
            console.log(result);
        }
    });





// options = {
//     filter: {
//         value: [],
//     },
//     fromBlock: 0
// };
//     tokenContract.events.Transfer(options)
//     .on('data', event => console.log('data', event))
//     .on('changed', changed => console.log('changed', changed))
//     .on('error', err => console.log('err', err))
//     .on('connected', connected => console.log('connected',connected))

})();

