

drop database if exists cb_v1;

create database cb_v1;

use cb_v1;

create table t_member(
  id int primary key auto_increment,
  username varchar(50) comment '用户名',
  pwd varchar(100) comment '密码',
  money decimal(10,4) comment '金额',
  freeze_money decimal(10,4) comment '冻结金额',
  salt varchar(100) comment '盐',
  version int comment '版本',
  create_time timestamp null default null comment '创建时间',
  update_time timestamp null default null comment '创建时间',
  UNIQUE KEY unique_username (username)
);


insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jack','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackB1','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackB2','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC1','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC2','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC3','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC4','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD1','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD2','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD3','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD4','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD5','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD6','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD7','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD8','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackE1','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackE2','123456',10000,'123456',1,'2022-06-25 12:00:00');
insert into cb_v1.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackE3','123456',10000,'123456',1,'2022-06-25 12:00:00');



create table t_member_flow(
  id int primary key auto_increment,
  username varchar(50) comment '用户名',
  before_money decimal(10,4) comment '帐变前金额',
  after_money decimal(10,4) comment '帐变后金额',
  flow_money decimal(10,4) comment '流水金额',
  item_code int comment '帐变编码',
  item_code_default varchar(100) comment '帐变默认编码',
  create_time timestamp null default null comment '创建时间',
  remark varchar(100) comment '备注'
) comment '会员流水表';


create table t_operation_log(
  id int primary key auto_increment
) comment '会员流水表';




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

insert into cb_v1.t_wallet (`address`,`uid`,`username`,`type`) values ('TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe',1,'jack', 1);




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
INSERT INTO cb_v1.t_hash_result (game_id, num, txID, block_hash, block_height, open_time, open_timestamp, network, is_settle) VALUES (5, '202207010275', '00b62b030c3acf59129247e4a5272be8bbf736723c05a9aed154c990c9513df3', '0000000001a6a300de858fa824019a3e1dbd779e074fc2cb314fc61aca7dfff6', '27697920', '2022-07-01 22:55:07', 1656687307000, 'nile', 0);
INSERT INTO cb_v1.t_hash_result (game_id, num, txID, block_hash, block_height, open_time, open_timestamp, network, is_settle) VALUES (5, '202207010274', '78458f985d78e05a21ff4171e376bb3ba08154f4c6675ff0860f99c4ff8c98a3', '0000000001a6a29e0703e89c59bab0e5c36f5bab2c91fc9797da037d2cd9b9e8', '27697822', '2022-07-01 22:50:06', 1656687006000, 'nile', 0);
INSERT INTO cb_v1.t_hash_result (game_id, num, txID, block_hash, block_height, open_time, open_timestamp, network, is_settle) VALUES (5, '202207010273', 'd9dc22d7f4a915a339bc49a6b3cfe5df8f3a394f6d79a64fe68cbf1d5553a589', '0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb', '27697765', '2022-07-01 22:47:16', 1656686836000, 'nile', 0);




drop table if exists t_lottery_category;
create table t_lottery_category(
  id int primary key auto_increment,
  name_code varchar(50) comment '类目编码',
  name_default varchar(50) comment '类目默认名称'
) comment '彩种类目';
insert into cb_v1.t_lottery_category (`name_code`,`name_default`) values ('100010','哈希抽奖');
insert into cb_v1.t_lottery_category (`name_code`,`name_default`) values ('100020','哈希彩票');


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
insert into cb_v1.t_lottery_game(`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values ('200010','抽奖-牛牛',1,'100010','哈希抽奖',1);
insert into cb_v1.t_lottery_game(`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values ('200011','抽奖-大小',1,'100010','哈希抽奖',1);
insert into cb_v1.t_lottery_game(`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values ('200012','抽奖-单双',1,'100010','哈希抽奖',1);
insert into cb_v1.t_lottery_game(`name_code`,`name_default`,`cate_id`,`cate_name_code`,`cate_name_default`,`enable`) values ('200110','哈希28',1,'100020','哈希彩票',1);



drop table if exists t_lottery_play;
create table t_lottery_play(
  id int primary key auto_increment,
  name_code varchar(50) comment '玩法名称编码',
  name_default varchar(50) comment '玩法默认名称'
) comment '彩种玩法';

insert into cb_v1.t_lottery_play(`name_code`,`name_default`) values ('400010','一球');
insert into cb_v1.t_lottery_play(`name_code`,`name_default`) values ('400011','二球');
insert into cb_v1.t_lottery_play(`name_code`,`name_default`) values ('400012','二球');
insert into cb_v1.t_lottery_play(`name_code`,`name_default`) values ('400012','和值');
insert into cb_v1.t_lottery_play(`name_code`,`name_default`) values ('400012','龙虎');
insert into cb_v1.t_lottery_play(`name_code`,`name_default`) values ('400012','特殊');
insert into cb_v1.t_lottery_play(`name_code`,`name_default`) values ('400012','牛牛');



drop table if exists t_lottery_play_code;
create table t_lottery_play_code(
  id int primary key auto_increment,
  play_id int comment '玩法编号',
  name_code varchar(50) comment '玩法名称编码',
  name_default varchar(50) comment '玩法默认名称',
  odds decimal(10,3) comment '赔率'
) comment '彩种玩法编码';

-- # 一球
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500010','大',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500011','小',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500012','单',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500013','双',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500014','1',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500015','2',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500016','3',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500017','4',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500018','5',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500019','6',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500020','7',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500021','8',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500022','9',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (1,'500023','0',9.800);

-- # 二球
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500010','大',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500011','小',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500012','单',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500013','双',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500014','1',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500015','2',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500016','3',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500017','4',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500018','5',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500019','6',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500020','7',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500021','8',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500022','9',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (2,'500023','0',9.800);

-- # 三球
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500010','大',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500011','小',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500012','单',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500013','双',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500014','1',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500015','2',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500016','3',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500017','4',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500018','5',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500019','6',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500020','7',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500021','8',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500022','9',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (3,'500023','0',9.800);

-- # 和值
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500110','总和大',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500111','总和小',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500112','总和单',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500113','总和双',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500114','0',803.600);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500115','1',267.860);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500116','2',133.930);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500117','3',80.360);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500118','4',53.570);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500119','5',38.260);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500120','6',28.690);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500121','7',22.310);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500122','8',17.860);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500123','9',14.600);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500124','10',12.750);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500125','11',11.640);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500126','12',11.000);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500127','13',10.710);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500128','14',10.710);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500129','15',11.000);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500130','16',11.640);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500131','17',12.750);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500132','18',14.640);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500133','19',17.860);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500134','20',22.310);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500135','21',28.690);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500136','22',38.260);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500137','23',53.570);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500138','24',80.3600);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500139','25',133.930);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500140','26',267.840);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (4,'500141','27',803.600);


-- # 龙虎
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (5,'500210','龙',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (5,'500211','虎',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (5,'500212','和',8.030);


-- # 特殊
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (6,'500310','豹子',80.360);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (6,'500311','顺子',13.390);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (6,'500312','半顺',2.170);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (6,'500313','对子',2.970);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (6,'500314','杂六',2.680);


-- # 牛牛
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500410','牛1',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500411','牛2',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500412','牛3',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500413','牛4',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500414','牛5',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500415','牛6',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500416','牛7',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500417','牛8',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500418','牛9',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500419','牛牛',9.800);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500420','牛大',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500421','牛小',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500422','牛单',1.960);
insert into cb_v1.t_lottery_play_code(`play_id`,`name_code`,`name_default`,`odds`) values (7,'500423','牛双',1.960);




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
  play_id int comment '玩法编号',
  play_name_code varchar(50) comment '类目编码',
  play_name_default varchar(50) comment '玩法编码',
  play_code_id int comment '玩法编码id',
  play_code_name_code varchar(50) comment '玩法编码id',
  play_code_name_default varchar(50) comment '玩法编码id',
  hash_result varchar(80) comment '开奖结果',
  num varchar(20) comment '期号',
  odds decimal(10,4) comment '赔率',
  money decimal(10,4) comment '投注金额',
  profit_money decimal(10,4) comment '赢利金额',
  payout_money decimal(10,4) comment '派彩金额',
  create_time timestamp null default null comment '创建时间',
  update_time timestamp null default null comment '更新时间',
  status int default 0 comment '状态(0:未结算,1:已结算,2:作废)',
  remark varchar(100) comment '备注'
) comment '彩票注单';

INSERT INTO `t_lottery_bet` (`uid`, `username`, `cate_id`, `cate_name_code`, `cate_name_default`, `game_id`, `game_name_code`, `game_name_default`, `play_id`, `play_name_code`, `play_name_default`, `play_code_id`, `play_code_name_code`, `play_code_name_default`, `hash_result`, `num`, `odds`, `money`, `profit_money`, `payout_money`, `create_time`, `update_time`, `status`, `remark`) VALUES
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:27','2022-07-04 18:34:27',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:30','2022-07-04 18:34:30',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:30','2022-07-04 18:34:30',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:30','2022-07-04 18:34:30',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:31','2022-07-04 18:34:31',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:31','2022-07-04 18:34:31',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:31','2022-07-04 18:34:31',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:31','2022-07-04 18:34:31',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:32','2022-07-04 18:34:32',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:32','2022-07-04 18:34:32',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:32','2022-07-04 18:34:32',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:32','2022-07-04 18:34:32',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:33','2022-07-04 18:34:33',1,NULL),
(1,'jack',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:34:33','2022-07-04 18:34:33',1,NULL),
(2,'jackB',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:36:06','2022-07-04 18:36:06',1,NULL),
(2,'jackB',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:36:07','2022-07-04 18:36:07',1,NULL),
(2,'jackB',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:36:08','2022-07-04 18:36:08',1,NULL),
(2,'jackB',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:36:08','2022-07-04 18:36:08',1,NULL),
(2,'jackB',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:36:09','2022-07-04 18:36:09',1,NULL),
(2,'jackB',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:36:09','2022-07-04 18:36:09',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:12','2022-07-04 18:37:12',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:13','2022-07-04 18:37:13',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:14','2022-07-04 18:37:14',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:14','2022-07-04 18:37:14',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:15','2022-07-04 18:37:15',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:15','2022-07-04 18:37:15',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:16','2022-07-04 18:37:16',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:16','2022-07-04 18:37:16',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:17','2022-07-04 18:37:17',1,NULL),
(3,'jackC',1,'100020','哈希彩票',4,'200110','哈希28',1,'400010','一球',1,'500010','大','0000000001a6a26530f361b32e7d79f97e3894175a63a4e6835789eb9511fbdb','202207010273',1.9600,100.0000,96.0000,196.0000,'2022-07-04 18:37:17','2022-07-04 18:37:17',1,NULL);




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






drop table if exists t_agent;
create table t_agent(
  id int primary key auto_increment,
  p_uid int comment '上级用户id',
  uid int comment '用户id',
  username varchar(100) comment '用户名',
  level int comment '层级',
  child text comment '代理下级'
) comment '代理';



insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (0,1,'jack',0,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (1,2,'jackB1',1,'4,5,8,9,10,11,16,17');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (1,3,'jackB2',1,'6,7,12,13,14,15,18');

insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (2,4,'jackC1',2,'8,9,16,17');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (2,5,'jackC2',2,'10,11');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (3,6,'jackC3',2,'12,13,18');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (3,7,'jackC4',2,'14,15');

insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (4,8,'jackD1',3,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (4,9,'jackD2',3,'16,17');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (5,10,'jackD3',3,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (5,11,'jackD4',3,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (6,12,'jackD5',3,'18');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (6,13,'jackD6',3,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (7,14,'jackD7',3,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (7,15,'jackD8',3,'');

insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (9,16,'jackE1',4,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (9,17,'jackE2',4,'');
insert into cb_v1.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (12,18,'jackE3',4,'');



drop table if exists t_statistics;
create table t_statistics(
  id int primary key auto_increment,
  `date` varchar(10) comment '日期',
  uid int comment '用户id',
  username varchar(100) comment '用户名',
  bet_money decimal(20,2) comment '投注金额',
  bet_profit_money decimal(20,2) comment '投注赢利金额',
  bet_payout_money decimal(20,2) comment '投注派彩金额',
  recharge_money decimal(20,2) comment '充值金额',
  withdraw_money decimal(20,2) comment '提现金额',
  update_time timestamp null default null comment '更新时间',
  UNIQUE KEY unique_date_uid (`date`,uid) comment '联合索引(日期和用户id)'
) comment '统计';


INSERT INTO cb_v1.t_statistics (`date`, `uid`, `username`, `bet_money`, `bet_profit_money`, `bet_payout_money`, `recharge_money`, `withdraw_money`, `update_time`) VALUES
('20220704',2,'jackB1',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',3,'jackB2',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',4,'jackC1',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',5,'jackC2',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',6,'jackC3',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',7,'jackC4',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',8,'jackD1',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',9,'jackD2',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',10,'jackD3',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',11,'jackD4',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',12,'jackD5',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',13,'jackD6',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',14,'jackD7',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',15,'jackD8',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',16,'jackE1',100000.00,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',17,'jackE2',100000.0000,100000.00,100000.00,0,0,'2022-07-04 18:37:43'),
('20220704',18,'jackE3',100000.0000,100000.00,100000.00,0,0,'2022-07-04 18:37:43');





drop table if exists t_agent_rebate;
create table t_agent_rebate(
                               id int primary key auto_increment,
                               min int comment '最低业绩',
                               max int comment '最高业绩',
                               rebate int comment '额度'
) comment '代理返佣比表';

insert into cb_v1.t_agent_rebate(`min`,`max`,`rebate`) values
(0,2000,50),
(2001,5000,55),
(5001,10000,60),
(10001,20000,80),
(20001,50000,90),
(50001,100000,110),
(100001,150000,130),
(150001,200000,135),
(200001,250000,140),
(250001,300000,145),
(300001,400000,150),
(400001,500000,155),
(500001,600000,160),
(600001,800000,170),
(800001,1000000,180),
(1000001,1500000,190),
(1500001,2000000,195),
(2000001,2500000,200),
(2500001,3000000,205),
(3000001,3500000,210),
(3500001,4000000,215),
(4000001,5000000,220),
(5000001,10000000,230);





drop table if exists t_agent_commission;
create table t_agent_commission(
    id int primary key auto_increment,
    `date` varchar(10) comment '日期',
    uid int comment '用户id',
    username varchar(100) comment '用户名',
    commission decimal(20,2) comment '佣金',
    total_performance decimal(20,2) comment '总业绩',
    self_performance decimal(20,2) comment '自营业绩',
    direct_performance decimal(20,2) comment '直属业绩',
    team_performance decimal(20,2) comment '团队业绩',
    rebate int comment '返佣比',
    create_time timestamp null default null comment '创建时间',
    update_time timestamp null default null comment '创建时间',
    UNIQUE KEY unique_date_uid (`date`,uid) comment '联合索引(日期和用户id)'
) comment '代理佣金表';









drop table if exists t_user;
CREATE TABLE t_user (
    id int(20) primary key auto_increment,
    username varchar(100) COMMENT '姓名',
    pwd varchar(128) COMMENT '密码',
    is_delete tinyint(1) default 0 COMMENT '是否删除 1：已删除；0：未删除',
    unique key uniq_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='后台用户表';
insert into cb_v1.t_user(username, pwd, is_delete) values ('admin', '123456', 0);
insert into cb_v1.t_user(username, pwd, is_delete) values ('jack', '123456', 0);
insert into cb_v1.t_user(username, pwd, is_delete) values ('tony', '123456', 0);


drop table if exists t_user_role;
CREATE TABLE t_user_role (
    id int(20) primary key auto_increment,
    user_id int COMMENT '用户ID',
    role_id int COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色表';
insert into cb_v1.t_user_role(user_id, role_id) values (1,1);
insert into cb_v1.t_user_role(user_id, role_id) values (1,2);
insert into cb_v1.t_user_role(user_id, role_id) values (2,2);
insert into cb_v1.t_user_role(user_id, role_id) values (2,3);
insert into cb_v1.t_user_role(user_id, role_id) values (3,1);
insert into cb_v1.t_user_role(user_id, role_id) values (3,3);


drop table if exists t_role;
CREATE TABLE t_role (
    id int primary key auto_increment,
    code varchar(100) COMMENT '编码',
    name varchar(100) COMMENT '名称',
    is_delete tinyint(1)  DEFAULT 0 COMMENT '是否删除 1：已删除；0：未删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';
insert into cb_v1.t_role(code, name, is_delete) values ('1000', '开发部', 0);
insert into cb_v1.t_role(code, name, is_delete) values ('1001', '测试部', 0);
insert into cb_v1.t_role(code, name, is_delete) values ('1003', '财务部', 0);




drop table if exists t_role_permission;
CREATE TABLE t_role_permission (
    id int(20) primary key auto_increment,
    role_id int(20) COMMENT '角色ID',
    permission_id int(20) COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关系表';
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,3);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,4);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,5);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,6);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,8);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,9);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,10);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,11);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,14);
insert into cb_v1.t_role_permission(role_id, permission_id) values (1,19);



drop table if exists t_permission;
CREATE TABLE t_permission (
    id int primary key auto_increment,
    name varchar(100) COMMENT '名称',
    name_code varchar(100) comment '权限编码',
    code varchar(100) COMMENT '权限编码',
    parent_id int COMMENT '父节点',
    node_type tinyint(4) DEFAULT 1 COMMENT '节点类型，1文件夹，2页面，3按钮',
    icon_url varchar(255) COMMENT '图标地址',
    sort int(11) DEFAULT 1 COMMENT '排序号',
    link_url varchar(500) COMMENT '页面对应的地址',
    level int(11) DEFAULT 0 COMMENT '层次',
    path varchar(100) DEFAULT '' COMMENT '树id的路径 整个层次上的路径id，逗号分隔',
    is_delete tinyint(1)  DEFAULT 0 COMMENT '是否删除 1：已删除；0：未删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

insert into cb_v1.t_permission(name, name_code, code, parent_id, node_type, icon_url, sort, link_url, level, path, is_delete) values
('会员管理','1000','1000',0, 1, '', 10, '/member/index', 1, '', 0),
('会员中心','1000','1000',1, 2, '', 10, '/member/index', 2, '1', 0),
('添加会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),
('删除会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),
('修改会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),
('查询会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),

('会员流水','1000','1000',1, 2, '', 10, '/member/index', 2, '1', 0),
('添加流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),
('删除流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),
('修改流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),
('查询流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),

('代理管理','1000','1000',0, 1, '', 10, '/member/index', 1, '', 0),
('代理中心','1000','1000',12, 2, '', 10, '/member/index', 2, '', 0),
('添加代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),
('删除代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),
('修改代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),
('查询代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),

('代理佣金','1000','1000',12, 2, '', 10, '/member/index', 2, '', 0),
('添加佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0),
('删除佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0),
('修改佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0),
('查询佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0)
;







