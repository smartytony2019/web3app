

drop database if exists cb;

create database cb;


use cb;

create table t_user(
  id int primary key auto_increment,
  username varchar(50) comment '用户名',
  pwd varchar(100) comment '密码',
  money decimal(10,4) comment '金额',
  salt varchar(100) comment '盐',
  version int comment '版本',
  create_time timestamp comment '创建时间'
);


insert into cb.t_user (`username`,`pwd`,`money`,`salt`,`version`,`create_time`)
values
('jack','123456',10000,'123456',1,'2022-06-25 12:00:00');



create table t_recharge (
    id int primary key auto_increment,
    transaction_id varchar(100) comment '转帐id',
    token_symbol varchar(20) comment '币种',
    token_decimals int comment '位数',
    token_address varchar(100) comment '合约地址',
    token_name varchar(20) comment '币种名',
    block_timestamp bigint comment '块时间戳',
    from_address varchar(100) comment '转帐地址',
    to_address varchar(100) comment '收款地址',
    type varchar(20) comment '转帐类型',
    value decimal(20,6) comment '转帐金额',
    UNIQUE KEY unique_transaction_id (transaction_id)
) comment '充值';



create table t_wallet(
    id int primary key auto_increment,
    address varchar(100),
    uid int comment '会员id',
    username varchar(50) comment '会员名',
    type int(10) comment '钱包类型'
) comment '钱包';

insert into cb.t_wallet (`address`,`uid`,`username`,`type`)
values
('TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe',1,'jack', 1);




create table t_expect (
  id int primary key auto_increment,
  game_id int comment '彩种id',
  num varchar(50) comment '期号数',
  start_time varchar(20) comment '开始时间',
  end_time varchar(20) comment '结束时间'
) comment '期号';


create table t_open_result (
  id int primary key auto_increment,
  game_id int comment '彩种id',
  num varchar(50) comment '期号数',
  hash_code varchar(50) comment '哈希码',
  status int comment '状态(0:未开奖,1:已开奖,2:已结算)'
) comment '开奖结果';




/*
create table t_lottery_category(
  id int primary key auto_increment,
  name varchar(50) comment '类目名称'
) comment '彩种类目';


create table t_lottery_game(
  id int primary key auto_increment,
  name varchar(50) comment '游戏名称',
  cate_id varchar(50) comment '类目id',
  cate_name varchar(50) comment '类目名称',
  enable tinyint(1) comment '是否开启'
) comment '彩种游戏';


create table t_lottery_play(
  id int primary key auto_increment,
  name varchar(50) comment '玩法名称',
  game_id varchar(50) comment '彩种id',
  play_code varchar(50) comment '玩法编码',
  enable tinyint(1) comment '是否开启'
) comment '彩种玩法';


create table t_lottery_play_code(
  id int primary key auto_increment,
  code varchar(50) comment '编码',
  odds varchar(50) comment '赔率'
) comment '彩种玩法编码';
*/