// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0;

contract cbTransfer {

  address public owner;

  constructor() {
    owner = msg.sender;
  }

  modifier restricted() {
    require(
      msg.sender == owner,
      "This function is restricted to the contract's owner"
    );
    _;
  }

  //转帐 
  event Transfer(address indexed from, address indexed to, uint value);
  function transferTo(address payable _address, uint256 _val) payable public restricted {
    emit Transfer(msg.sender, _address, _val);
    _address.transfer(_val);
  }

  function getBalanceOfAccount(address _addr) public view returns(uint256) {
    return address(_addr).balance;
  }

  // 向合约账户转账
  //function transderToContract(uint256 _val) payable public {
  //    payable(address(this)).transfer(_val);
  //}
  
  // 获取合约账户余额 
  function getBalanceOfContract() public view returns (uint256) {
      return address(this).balance;
  }

  fallback() external payable {}
  
  receive() external payable {}

}