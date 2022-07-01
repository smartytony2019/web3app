

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
insert into cb.t_lottery_game(`id`,`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values (4,'200110','哈希28',1,'100020','哈希彩票',1);



drop table if exists t_lottery_play;
create table t_lottery_play(
  id int primary key auto_increment,
  name_code varchar(50) comment '玩法名称编码',
  name_default varchar(50) comment '玩法默认名称'
) comment '彩种玩法';

insert into cb.t_lottery_play(`id`,`name_code`,`name_default`) values (1,'400010','一球');
insert into cb.t_lottery_play(`id`,`name_code`,`name_default`) values (2,'400011','二球');
insert into cb.t_lottery_play(`id`,`name_code`,`name_default`) values (3,'400012','二球');
insert into cb.t_lottery_play(`id`,`name_code`,`name_default`) values (4,'400012','和值');
insert into cb.t_lottery_play(`id`,`name_code`,`name_default`) values (5,'400012','龙虎');
insert into cb.t_lottery_play(`id`,`name_code`,`name_default`) values (6,'400012','特殊');
insert into cb.t_lottery_play(`id`,`name_code`,`name_default`) values (7,'400012','牛牛');



drop table if exists t_lottery_play_code;
create table t_lottery_play_code(
  id int primary key auto_increment,
  play_id int comment '玩法id',
  name_code varchar(50) comment '玩法名称编码',
  name_default varchar(50) comment '玩法默认名称',
  odds decimal(10,3) comment '赔率'
) comment '彩种玩法编码';

-- # 一球
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1000,1,'500010','大',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1001,1,'500011','小',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1002,1,'500012','单',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1003,1,'500013','双',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1004,1,'500014','1',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1005,1,'500015','2',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1006,1,'500016','3',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1007,1,'500017','4',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1008,1,'500018','5',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1009,1,'500019','6',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1010,1,'500020','7',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1011,1,'500021','8',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1012,1,'500022','9',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (1013,1,'500023','0',1.000);

-- # 二球
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2001,2,'500010','大',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2002,2,'500011','小',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2003,2,'500012','单',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2004,2,'500013','双',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2005,2,'500014','1',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2006,2,'500015','2',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2007,2,'500016','3',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2008,2,'500017','4',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2009,2,'500018','5',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2010,2,'500019','6',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2011,2,'500020','7',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2012,2,'500021','8',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2013,2,'500022','9',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (2014,2,'500023','0',1.000);

-- # 三球
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3001,3,'500010','大',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3002,3,'500011','小',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3003,3,'500012','单',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3004,3,'500013','双',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3005,3,'500014','1',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3006,3,'500015','2',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3007,3,'500016','3',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3008,3,'500017','4',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3009,3,'500018','5',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3010,3,'500019','6',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3011,3,'500020','7',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3012,3,'500021','8',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3013,3,'500022','9',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (3014,3,'500023','0',1.000);

-- # 和值
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4001,4,'500110','总和大',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4002,4,'500111','总和小',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4003,4,'500112','总和单',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4004,4,'500113','总和双',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4005,4,'500114','大单',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4006,4,'500115','大双',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4007,4,'500116','0',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4008,4,'500117','1',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4009,4,'500118','2',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4010,4,'500119','3',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4011,4,'500120','4',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4012,4,'500121','5',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4013,4,'500122','6',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4014,4,'500123','7',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4015,4,'500124','8',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4016,4,'500125','9',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4017,4,'500126','10',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4018,4,'500127','11',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4019,4,'500128','12',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4020,4,'500129','13',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4021,4,'500130','14',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4022,4,'500131','15',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4023,4,'500132','16',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4024,4,'500133','17',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4025,4,'500134','18',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4026,4,'500135','20',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4027,4,'500136','21',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4028,4,'500137','22',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4029,4,'500138','23',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4030,4,'500139','24',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4031,4,'500140','25',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4032,4,'500141','26',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (4033,4,'500142','27',1.000);


-- # 龙虎
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (5000,5,'500210','龙',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (5001,5,'500211','虎',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (5002,5,'500212','和',1.000);


-- # 特殊
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (6000,6,'500310','豹子',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (6001,6,'500311','顺子',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (6002,6,'500312','半顺',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (6003,6,'500313','对子',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (6004,6,'500314','杂六',1.000);


-- # 牛牛
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7000,7,'500410','牛1',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7001,7,'500411','牛2',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7002,7,'500412','牛3',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7003,7,'500413','牛4',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7004,7,'500414','牛5',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7005,7,'500415','牛6',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7006,7,'500416','牛7',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7007,7,'500417','牛8',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7008,7,'500418','牛9',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7009,7,'500419','牛牛',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7010,7,'500420','牛大',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7011,7,'500421','牛小',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7012,7,'500422','牛单',1.000);
insert into cb.t_lottery_play_code(`id`,`play_id`,`name_code`,`name_default`,`odds`) values (7013,7,'500423','牛双',1.000);



drop table if exists t_lottery_bet;
create table t_lottery_bet (
  id int primary key auto_increment,
  uid int comment '会员id',
  username varchar(50) comment '会员名',
  cate_id int comment '类目id',
  cate_name_code varchar(50) comment '类目编码',
  cate_name_default varchar(50) comment '类目默认名称',
  game_id int comment '彩种id',
  game_name_code varchar(50) comment '类目编码',
  game_name_default varchar(50) comment '彩种编码',
  play_id int comment '玩法id',
  play_name_code varchar(50) comment '类目编码',
  play_name_default varchar(50) comment '玩法编码',
  play_code_id int comment '玩法编码id',
  play_code_name_code varchar(50) comment '玩法编码id',
  play_code_name_default varchar(50) comment '玩法编码id',
  hash_result varchar(50) comment '开奖结果',
  num varchar(10) comment '期号',
  odds decimal(10,4) comment '赔率',
  money decimal(10,4) comment '投注金额',
  create_time timestamp null default null comment '创建时间',
  update_time timestamp null default null comment '更新时间',
  status int default 0 comment '状态(0:未结算,1:已结算,2:作废)',
  remark varchar(100) comment '备注'
) comment '彩票注单';


drop table if exists t_draw_bet;
create table t_draw_bet (
  id int primary key auto_increment,
  uid int comment '会员id',
  username varchar(50) comment '会员名',
  form_address varchar(50) comment '用户地址',
  to_address varchar(50) comment '收款地址',
  amount varchar(100) comment '金额',
  symbol varchar(50) comment '货币',
  hash_result varchar(50) comment '开奖结果',
  create_time timestamp null default null comment '创建时间',
  update_time timestamp null default null comment '更新时间',
  status int default 0 comment '状态(0:未结算,1:已结算,2:作废)',
  remark varchar(100) comment '备注'
) comment '抽奖注单';

