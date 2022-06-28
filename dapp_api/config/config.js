
module.exports = {

  //开发环境: nile(测试网络)
  development: {
    trx : {
      //open_from TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y
      network    : 'nile',
      fullNode   : 'https://api.nileex.io/',
      solidityNode   : 'https://api.nileex.io',
      eventServer   : 'https://event.nileex.io/',
      fromAddress: 'TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y',
      toAddress  : 'TPYXWcPZ9DC9R4PvQniTPSjUegUyBD3kJJ',
      contractAddress: 'TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR',
      privateKey : '0d35dba8af935d575924cd0d3afd2479248de12aa0f13f547e2e9debcdd025c5'
    }
  },

  //测试环境: nile(测试网络)
  test: {
    trx : {
      //open_from TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y
      network    : 'nile',
      fullNode   : 'https://api.nileex.io/',
      solidityNode   : 'https://api.nileex.io',
      eventServer   : 'https://event.nileex.io/',
      fromAddress: 'TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y',
      toAddress  : 'TPYXWcPZ9DC9R4PvQniTPSjUegUyBD3kJJ',
      contractAddress: 'TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR',
      privateKey : '0d35dba8af935d575924cd0d3afd2479248de12aa0f13f547e2e9debcdd025c5'
    }
  },

  //生产环境: main(主网)
  production: {
    trx: {
      network    : 'main',
      fullNode   : 'https://api.trongrid.io/',
      solidityNode   : 'https://api.trongrid.io',
      eventServer   : 'https://event.trongrid.io/',
      fromAddress: 'TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y',
      toAddress  : 'TPYXWcPZ9DC9R4PvQniTPSjUegUyBD3kJJ',
      contractAddress: 'TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t',
      privateKey : '0d35dba8af935d575924cd0d3afd2479248de12aa0f13f547e2e9debcdd025c5'
    }
  }

}

