

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



drop table if exists t_hash_result;
create table t_hash_result (
  id int primary key auto_increment,
  game_id int comment '彩种id',
  num varchar(50) comment '期号数',
  txID varchar(70) comment '期号数',
  block_hash varchar(70) comment '哈希码',
  block_height varchar(50) comment '块高度',
  open_time timestamp comment '开奖时间',
  open_timestamp bigint comment '开奖时间戳',
  network varchar(10) comment '块高度',
  is_settle tinyint comment '状态(0:未结算,1:已结算)'
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
  play_code_id int comment '玩法编码',
  enable tinyint(1) comment '是否开启'
) comment '彩种玩法';
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code_id`,`enable`) values ('400010','一球',1,'200013','哈希28',1000,1);
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code_id`,`enable`) values ('400011','二球',1,'200013','哈希28',1001,1);
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code_id`,`enable`) values ('400012','二球',1,'200013','哈希28',1002,1);
insert into cb.t_lottery_play(`name_code`,`name_default`,`game_id`,`game_name_code`,`game_name_default`,`play_code_id`,`enable`) values ('400012','和值',1,'200014','哈希28',1003,1);



drop table if exists t_lottery_play_code;
create table t_lottery_play_code(
  id int primary key auto_increment,
  play_code_id int comment '玩法编码(对应t_lottery_play.play_code_id)',
  name_code varchar(50) comment '玩法名称编码',
  name_default varchar(50) comment '玩法默认名称',
  odds decimal(10,3) comment '赔率'
) comment '彩种玩法编码';

-- # 一球
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500010','大',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500011','小',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500012','单',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500013','双',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500014','1',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500015','2',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500016','3',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500017','4',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500018','5',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500019','6',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500020','7',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500021','8',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500022','9',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1000,'500023','0',1.000);

-- # 二球
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500010','大',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500011','小',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500012','单',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500013','双',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500014','1',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500015','2',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500016','3',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500017','4',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500018','5',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500019','6',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500020','7',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500021','8',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500022','9',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1001,'500023','0',1.000);

-- # 三球
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500010','大',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500011','小',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500012','单',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500013','双',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500014','1',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500015','2',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500016','3',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500017','4',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500018','5',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500019','6',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500020','7',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500021','8',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500022','9',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1002,'500023','0',1.000);

-- # 和值
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500110','总和大',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500111','总和小',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500112','总和单',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500113','总和双',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500114','大单',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500115','大双',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500116','0',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500117','1',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500118','2',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500119','3',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500120','4',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500121','5',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500122','6',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500123','7',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500124','8',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500125','9',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500126','10',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500127','11',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500128','12',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500129','13',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500130','14',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500131','15',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500132','16',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500133','17',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500134','18',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500135','20',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500136','21',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500137','22',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500138','23',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500139','24',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500140','25',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500141','26',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1003,'500142','27',1.000);


-- # 龙虎
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1004,'500210','龙',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1004,'500211','虎',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1004,'500212','和',1.000);


-- # 特殊
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1005,'500310','豹子',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1005,'500311','顺子',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1005,'500312','半顺',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1005,'500313','对子',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1005,'500314','杂六',1.000);


-- # 牛牛
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500410','牛1',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500411','牛2',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500412','牛3',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500413','牛4',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500414','牛5',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500415','牛6',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500416','牛7',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500417','牛8',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500418','牛9',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500419','牛牛',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500420','牛大',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500421','牛小',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500422','牛单',1.000);
insert into cb.t_lottery_play_code(`play_code_id`,`name_code`,`name_default`,`odds`) values (1006,'500423','牛双',1.000);





drop table if exists t_lottery_bet;
create table t_lottery_bet (
  id int primary key auto_increment,
  uid int comment '会员id',
  username varchar(50) comment '会员名',
  cate_id int comment '类目id',
  cate_name_code int comment '类目编码',
  game_id int comment '彩种id',
  game_name_code int comment '彩种编码',
  play_id int comment '玩法id',
  play_name_code varchar(50) comment '玩法编码',
  play_code_id varchar(50) comment '玩法编码id',
  bet_odds decimal(10,4) comment '赔率',
  bet_code varchar(50) comment '投注编码',
  bet_money decimal(10,4) comment '投注金额',
  create_time timestamp comment '创建时间',
  update_time timestamp comment '更新时间',
  status int comment '状态(0:未结算,1:已结算,2:作废)'
) comment '投注';
