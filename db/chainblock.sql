

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

insert into cb.t_wallet (`address`,`uid`,`username`,`type`) values ('TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe',1,'jack', 1);




drop table if exists t_expect;
create table t_expect (
  id int primary key auto_increment,
  game_id int comment '彩种id',
  num varchar(50) comment '期号数',
  start_time varchar(20) comment '开始时间',
  end_time varchar(20) comment '结束时间'
) comment '期号';


drop table if exists t_open_result;
create table t_open_result (
  id int primary key auto_increment,
  game_id int comment '彩种id',
  num varchar(50) comment '期号数',
  hash_code varchar(50) comment '哈希码',
  status int comment '状态(0:未开奖,1:已开奖,2:已结算)'
) comment '开奖结果';





drop table if exists t_lottery_category;
create table t_lottery_category(
  id int primary key auto_increment,
  name_code varchar(50) comment '类目编码',
  name_default varchar(50) comment '类目默认名称'
) comment '彩种类目';
insert into cb.t_lottery_category (`id`,`name_code`,`name_default`) values (1,'100010','哈希抽奖');
insert into cb.t_lottery_category (`id`,`name_code`,`name_default`) values (2,'100020','哈希彩票');


drop table if exists t_lottery_game;
create table t_lottery_game(
  id int primary key auto_increment,
  name_code varchar(50) comment '游戏名称编码',
  name_default varchar(50) comment '游戏默认名称',
  cate_id varchar(50) comment '类目id',
  cate_name_code varchar(50) comment '类目编码',
  cate_name_default varchar(50) comment '类目默认名称',
  enable tinyint(1) comment '是否开启'
) comment '彩种游戏';
insert into cb.t_lottery_game(`id`,`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values (1,'200010','抽奖-牛牛',1,'100010','哈希抽奖',1);
insert into cb.t_lottery_game(`id`,`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values (2,'200011','抽奖-大小',1,'100010','哈希抽奖',1);
insert into cb.t_lottery_game(`id`,`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values (3,'200012','抽奖-单双',1,'100010','哈希抽奖',1);
insert into cb.t_lottery_game(`id`,`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values (4,'200013','哈希28',1,'100020','哈希彩票',1);



drop table if exists t_lottery_play;
create table t_lottery_play(
  id int primary key auto_increment,
  name_code varchar(50) comment '玩法名称编码',
  name_default varchar(50) comment '玩法默认名称',
  game_id varchar(50) comment '彩种id',
  game_name_code varchar(50) comment '游戏名称编码',
  game_name_default varchar(50) comment '游戏默认名称',
  play_code int comment '玩法编码',
  enable tinyint(1) comment '是否开启'
) comment '彩种玩法';
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code`,`enable`) values ('400010','一球',1,'200013','哈希28',1000,1);
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code`,`enable`) values ('400011','二球',1,'200013','哈希28',1001,1);
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code`,`enable`) values ('400012','二球',1,'200013','哈希28',1002,1);
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code`,`enable`) values ('400012','和值',1,'200014','哈希28',1003,1);



drop table if exists t_lottery_play_code;
create table t_lottery_play_code(
  id int primary key auto_increment,
  play_code int comment '玩法编码(对应t_lottery_play.play_code字段)',
  name_code varchar(50) comment '玩法名称编码',
  name_default varchar(50) comment '玩法默认名称',
  odds decimal(10,3) comment '赔率'
) comment '彩种玩法编码';
-- # 一球
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'500010','大',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'500011','小',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'500012','单',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'500013','双',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','1',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','2',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','3',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','4',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','5',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','6',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','7',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','8',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','9',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1000,'','0',1.000);

-- # 二球
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'500010','大',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'500011','小',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'500012','单',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'500013','双',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','1',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','2',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','3',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','4',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','5',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','6',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','7',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','8',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','9',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1001,'','0',1.000);

-- # 三球
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'500010','大',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'500011','小',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'500012','单',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'500013','双',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','1',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','2',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','3',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','4',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','5',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','6',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','7',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','8',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','9',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1002,'','0',1.000);

-- # 和值
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'500110','总和大',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'500111','总和小',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'500112','总和单',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'500113','总和双',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'500114','大单',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'500115','大双',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','0',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','1',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','2',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','3',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','4',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','5',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','6',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','7',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','8',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','9',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','10',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','11',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','12',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','13',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','14',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','15',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','16',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','17',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','18',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','20',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','21',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','22',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','23',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','24',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','25',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','26',1.000);
insert into cb.t_lottery_play_code(`play_code`,`name_code`,`name_default`,`odds`) values (1003,'','27',1.000);






drop table if exists t_lottery_bet;
create table t_lottery_bet (
  id int primary key auto_increment,
  uid int comment '会员id',
  username varchar(50) comment '会员名',
  cate_id varchar(50) comment '玩法名称',
  game_id int comment '彩种id',
  play_id int comment '玩法id',
  play_code varchar(50) comment '玩法编码',
  play_odds varchar(50) comment '赔率',
  content_code varchar(50) comment '内容编码',
  bet_num varchar(50) comment '投注号码',
  bet_money decimal(10,4) comment '投注金额',
  bet_time timestamp comment '投注时间',
  status int comment '状态(0:未结算,1:已结算,2:作废)'
) comment '投注';
