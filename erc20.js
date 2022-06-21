var Tx = require('ethereumjs-tx').Transaction;
var Web3 = require('web3')

var fs = require('fs');
var web3 = new Web3(new Web3.providers.HttpProvider(`https://rinkeby.infura.io/v3/d3df59f4140d47b087775e2c1b4be8ed`))

// web3 version 
console.log(`web3 version: ${web3.version}`)



function getERC20Balance(fromAddress,contractObj,bookmark) {
    contractObj.methods.balanceOf(fromAddress).call().then(
        function (wei) {
            balance = web3.utils.fromWei(wei, 'ether');
            console.log(fromAddress, bookmark + balance);
    });
}

function getPriKey(prikeyPath) {
    const privKeyFile = fs.readFileSync(prikeyPath).toString().trim();
    const privKey = new Buffer.from(privKeyFile, 'hex');    
    return privKey
}


function getERC20RawTx(fromAddress,toAddress,contractAddress,contractObj,sendNum,nonceNum, privKey) {
    //raw Tx
    var rawTransaction = {
        "from": fromAddress,
        "nonce": web3.utils.toHex(nonceNum),
        "gasLimit": web3.utils.toHex(8000000),
        "gasPrice": web3.utils.toHex(10e9),
        "to": contractAddress,
        "value": "0x0",
        "data": contractObj.methods.transfer(toAddress, sendNum).encodeABI(),
        "chainId": 0x04 //4:Rinkeby, 3:Ropsten, 1:mainnet
    };

    var tx = new Tx(rawTransaction, {'chain':'rinkeby'});
    tx.sign(privKey);
    var serializedTx = tx.serialize();
    return serializedTx;
}

async function signERC20Transaction(fromAddress,toAddress,contractAddress,contractObj,sendNum,nonceNum, privKey) {
    var serializedTx = getERC20RawTx(fromAddress,toAddress,contractAddress,contractObj,sendNum,nonceNum, privKey)

    // Comment out these three lines if you don't really want to send the TX right now
    console.log(`Attempting to send signed tx:  ${serializedTx.toString('hex')}`);
    var receipt = await web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'));
    console.log(`Receipt info:  ${JSON.stringify(receipt, null, '\t')}`);
}

function sleep(delay) {
    return new Promise(reslove => {
      setTimeout(reslove, delay)
    })
}

//an async function 
const sendERC20 = async () => {

    //the number of sended token
    //20 milli = 0.02 ether
    var transferAmount = web3.utils.toWei('2000000', 'wei');


    var myAddress = "0xcf5330F0c3ce7590C71cDd17739Ad37Cb932C3D0";
    var destAddress = "0x309ad20D059b371532856296d61279dF65a1f1FE";

    // get the nonce
    var nonceCnt = await web3.eth.getTransactionCount(myAddress);
    console.log(`num transactions so far: ${nonceCnt}`);

    var abiArray = JSON.parse(fs.readFileSync("./ABI.json"));
    var contractAddress = "0x3d2986709F64B9A485bCFa23B6d7fC1CF85221A4";
    var contract = new web3.eth.Contract(abiArray, contractAddress, { from: myAddress });

    // begin token numbers
    await getERC20Balance(myAddress,contract,"Balance before send: ");

    const privkey = Buffer.from('b6c6b9dd8d94d41d3a470d769c9cc31e4035aa450320a8769b69eb97c18179ea', 'hex');
    await signERC20Transaction(myAddress,destAddress,contractAddress,contract,transferAmount,nonceCnt, privkey)

    sleep(100)  //100ms

    // end token numbers
    await getERC20Balance(myAddress,contract,"Balance before send: ");
}

sendERC20();