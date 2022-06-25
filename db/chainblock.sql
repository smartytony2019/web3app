

drop database if exists cb;

create database cb;


use cb;

create table t_user(
  id int primary key auto_increment,
  name varchar(50) comment '用户名',
  pwd varchar(100) comment '密码',
  money decimal(10,4) comment '金额',
  salt varchar(100) comment '盐',
  version int comment '版本',
  create_time timestamp comment '创建时间'
);

create table t_recharge (
    id int primary key auto_increment,
    transaction_id varchar(100) comment '转帐id',
    token_symbol varchar(20) comment '币种',
    token_decimals int comment '位数',
    token_name varchar(20) comment '币种名',
    block_timestamp timestamp comment '块时间戳',
    from varchar(100) comment '转帐地址',
    to varchar(100) comment '收款地址',
    type varchar(20) comment '转帐类型',
    value decimal(20,6) comment '转帐金额',
    UNIQUE KEY unique_transaction_id (transaction_id)
) comment '充值';



create table t_wallet(
    id int primary key auto_increment,
    address varchar(100),
    username varchar(50),
    type int(10)
) comment '钱包';



