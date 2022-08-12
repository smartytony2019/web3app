

drop database if exists cb_v2;

create database cb_v2;

use cb_v2;


drop table if exists t_category;
create table t_category(
    id int primary key auto_increment,
    name varchar(50) comment '类目名称编码',
    name_zh varchar(50) comment '类目中文名称',
    sort int(5) comment '序号'
) comment '游戏类目';
insert into cb_v2.t_category(`name`,`name_zh`, `sort`) values
('100010','哈希', 1),
('100110','彩票', 1),
('100210','体育', 1);


drop table if exists t_game;
create table t_game(
    id int primary key auto_increment,
    cate_id varchar(50) comment '类目id',
    cate_name varchar(50) comment '类目名称编码',
    cate_name_zh varchar(50) comment '类目中文名称',
    name varchar(50) comment '游戏名称编码',
    name_zh varchar(50) comment '游戏中文名称',
    enable tinyint(1) comment '是否开启',
    pic varchar(100) comment '图片地址',
    sort int(5) comment '序号'
) comment '彩种游戏';

insert into cb_v2.t_game(`cate_id`,`cate_name`,`cate_name_zh`,`name`,`name_zh`,`enable`,`pic`,`sort`) values
('1', '100010', '哈希', '200010', '哈希两面',1, 'http://xx/x.png', 1),
('1', '100010', '哈希', '200110', '哈希百家乐',1, 'http://xx/x.png', 1),
('1', '100010', '哈希', '200210', '哈希PK拾',1, 'http://xx/x.png', 1),
('1', '100010', '哈希', '200310', '幸运哈希',1, 'http://xx/x.png', 1),
('1', '100010', '哈希', '200410', '哈希牛牛',1, 'http://xx/x.png', 1),
('2', '100110', '彩票', '200510', 'XB彩票',1, 'http://xx/x.png', 1),
('3', '100210', '体育', '200610', '皇冠体育',1, 'http://xx/x.png', 1);






drop table if exists t_hash_play;
create table t_hash_play(
   id int primary key auto_increment,
   cate_id varchar(50) comment '类目id',
   cate_name varchar(50) comment '类目名称编码',
   cate_name_zh varchar(50) comment '类目中文名称',
   game_id int comment '游戏id',
   game_name varchar(50) comment '游戏名称编码',
   game_name_zh varchar(50) comment '游戏中文名称',
   name varchar(50) comment '房间名称编码',
   name_zh varchar(50) comment '房间中文名称',
   min int comment '最低金额',
   max int comment '最高金额',
   max_odds decimal(10,2) comment '最大赔率',
   type tinyint(1) comment '类型(1:体验房, 2:初级房, 3:中级房, 4:高级房)',
   address varchar(100) comment '投注地址',
   algorithm varchar(10) comment '算法'
) comment '房间';

insert into cb_v2.t_hash_play(`cate_id`,`cate_name`,`cate_name_zh`, `game_id`, `game_name`, `game_name_zh`, `name`, `name_zh`, `min`, `max`, `max_odds`,`type`,`address`, `algorithm`) values
('1', '100010', '哈希', 1, '200010', '哈希两面', '300010', '体验房', '0', '200', 1.95, 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '1000'),
('1', '100010', '哈希', 1, '200010', '哈希两面', '300011', '初级房', '50', '5000', 1.95, 2, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '1000'),
('1', '100010', '哈希', 1, '200010', '哈希两面', '300012', '中级房', '100', '10000', 1.95, 3, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '1000'),
('1', '100010', '哈希', 1, '200010', '哈希两面', '300013', '高级房', '1000', '20000', 1.95, 4, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '1000'),

('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300010', '体验房', '0', '200', 1.95, 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '2000'),
('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300011', '初级房', '50', '5000', 1.95, 2, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '2000'),
('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300012', '中级房', '100', '10000', 1.95, 3, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '2000'),
('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300013', '高级房', '1000', '20000', 1.95, 4, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '2000'),

('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300010', '体验房', '0', '200', 1.98, 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '3000'),
('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300011', '初级房', '50', '5000', 1.98, 2, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '3000'),
('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300012', '中级房', '100', '10000', 1.98, 3, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '3000'),
('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300013', '高级房', '1000', '20000', 1.98, 4, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '3000'),

('1', '100010', '哈希', 4, '200310', '幸运哈希', '300010', '体验房', '0', '200', 1.98, 2, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '4000'),
('1', '100010', '哈希', 4, '200310', '幸运哈希', '300011', '初级房', '0', '200', 1.98, 2, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '4000'),
('1', '100010', '哈希', 4, '200310', '幸运哈希', '300012', '中级房', '0', '200', 1.98, 3, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '4000'),
('1', '100010', '哈希', 4, '200310', '幸运哈希', '300013', '高级房', '0', '200', 1.98, 4, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '4000'),

('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300010', '体验房', '0', '200', 1.98, 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '5000'),
('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300011', '初级房', '50', '5000', 1.98, 2, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '5000'),
('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300012', '中级房', '100', '10000', 1.98, 3, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '5000'),
('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300013', '高级房', '1000', '20000', 1.98, 4, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '5000')
;




drop table if exists t_hash_odds;
create table t_hash_odds(
    id int primary key auto_increment,
    game_id int comment '游戏id',
    name varchar(50) comment '名称',
    name_zh varchar(50) comment '名称中文',
    odds decimal(10, 2) comment '赔率',
    code varchar(50) comment '编码'
) comment '彩种玩法';

insert into cb_v2.t_hash_odds(`game_id`, `name`,`name_zh`, `odds`, `code`) values

(1, '400310','大', '1.95', '400310'),
(1, '400311','小', '1.95', '400311'),
(1, '400312','单', '1.95', '400312'),
(1, '400313','双', '1.95', '400313'),


(2, '400410','庄', '1.95', '400410'),
(2, '400411','庄对', '11', '400411'),
(2, '400412','闲对', '11', '400412'),
(2, '400413','闲', '1.95', '400413'),
(2, '400414','和', '8', '400414'),

(3, '400110','0', '9.8', '400110'),
(3, '400111','1', '9.8', '400111'),
(3, '400112','2', '9.8', '400112'),
(3, '400113','3', '9.8', '400113'),
(3, '400114','4', '9.8', '400114'),
(3, '400115','5', '9.8', '400115'),
(3, '400116','6', '9.8', '400116'),
(3, '400117','7', '9.8', '400117'),
(3, '400118','8', '9.8', '400118'),
(3, '400119','9', '9.8', '400119'),


(4, '400010','', '9.8', '400010'),

(5, '400210','庄(平倍)', '0.95', '400210'),
(5, '400211','庄(超级翻倍)', '0.95', '400211'),
(5, '400212','庄(翻倍)', '0.95', '400212'),
(5, '400213','和局', '9.5', '400213'),
(5, '400214','闲(平倍)', '0.95', '400214'),
(5, '400215','闲(超级翻倍)', '0.95', '400215'),
(5, '400216','闲(翻倍)', '0.95', '400216')
;



drop table if exists t_hash_result;
CREATE TABLE t_hash_result(
    id int primary key auto_increment,
    uid int(20) comment '会员id',
    username varchar(50) comment '会员名',
    game_id int(5) comment '游戏id',
    play_id int(5) comment '玩法id',
    sn varchar(100) comment '编号',
    to_address varchar(100) comment '会员数字钱包',
    txID varchar(70) comment '交易id',
    block_hash varchar(70) comment '块哈希',
    block_height varchar(70) comment '块高',
    open_time timestamp comment '开奖时间',
    open_timestamp bigint comment '开奖时间戳',
    network varchar(20) comment '网络',
    flag int(5) default 0 comment '标记(1:赢, 2:输, 3: 和)',
    UNIQUE KEY unique_sn (sn)
) comment '哈希开奖表';




drop table if exists t_hash_bet;
create table t_hash_bet (
    id int primary key auto_increment,
    sn varchar(100) comment '编号',
    uid int comment '会员id',
    username varchar(50) comment '会员名',
    cate_id int comment '类目id',
    cate_name varchar(50) comment '类目编码',
    cate_name_zh varchar(50) comment '类目名称(中文)',
    game_id int comment '彩种id',
    game_name varchar(50) comment '类目编码',
    game_name_zh varchar(50) comment '彩种名称(中文)',
    play_id int comment '玩法编号',
    play_name varchar(50) comment '玩法编码',
    play_name_zh varchar(50) comment '玩法名称(中文)',
    block_height varchar(50) comment '块高',
    block_hash varchar(80) comment '开奖结果',
    network varchar(50) comment '网络',
    content varchar(100) comment '投注内容',
    content_zh varchar(100) comment '投注内容(中文)',
    odds decimal(10,4) comment '赔率',
    bet_amount int(5) comment '投注数量',
    money decimal(10,4) comment '投注单价',
    money_amount decimal(10,4) comment '投注金额',
    profit_money decimal(10,4) comment '赢利金额',
    payout_money decimal(10,4) comment '派彩金额',
    create_time timestamp null default null comment '创建时间',
    update_time timestamp null default null comment '更新时间',
    flag int(5) default 0 comment '标记(1:赢, 2:输, 3: 和)',
    status int default 0 comment '状态(0:未结算,1:已结算,2:作废)',
    algorithm varchar(10) comment '算法'
) comment '哈希注单';


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
) comment '抽奖注单(未登录注单)';



drop table if exists t_lottery_bet;
create table t_lottery_bet (
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
) comment '彩票注单';

drop table if exists t_sport_bet;
create table t_sport_bet (
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
) comment '体育注单';




drop table if exists t_member;
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


insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jack','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackB1','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackB2','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC1','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC2','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC3','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackC4','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD1','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD2','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD3','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD4','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD5','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD6','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD7','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackD8','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackE1','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackE2','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');
insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`,`version`,`create_time`) values ('jackE3','29226cace4e40c30d0ca154ca98e7b88',10000,'8cTRqhYO15chyJGkJGO05GnpBr2m9o6J',1,'2022-06-25 12:00:00');



drop table if exists t_member_flow;
create table t_member_flow(
  id int primary key auto_increment,
  sn varchar(50) comment '订单号',
  username varchar(50) comment '用户名',
  before_money decimal(10,4) comment '帐变前金额',
  after_money decimal(10,4) comment '帐变后金额',
  flow_money decimal(10,4) comment '流水金额',
  item int comment '帐变编码',
  item_zh varchar(100) comment '帐变默认编码',
  create_time timestamp null default null comment '创建时间',
  remark varchar(100) comment '备注'
) comment '会员流水表';

# insert into cb_v2.t_member_flow(sn, username, before_money, after_money, flow_money, item, item_zh, create_time, remark) values
# ('123456','jack', 10000, 10020, 20, 100010, 100010, '2022-06-25 12:00:00', '');

create table t_operation_log(
  id int primary key auto_increment
) comment '会员流水表';




drop table if exists t_recharge;
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



drop table if exists t_wallet;
create table t_wallet(
    id int primary key auto_increment,
    uid int comment '会员id',
    username varchar(50) comment '会员名',
    type int(10) comment '钱包类型',
    private_key varchar(100),
    public_key varchar(200),
    address_base58 varchar(100),
    address_hex varchar(100)
) comment '钱包';

insert into cb_v2.t_wallet(uid, username, type, private_key, public_key, address_base58, address_hex) values
(1, 'jack', 1, 'CCD3959D4551058E65F8984CBD5F5A8B406973F10754BCCDE4B640A1061E5A0E', '040AEF573326EBDC792082319F06164620244DFF38970F1454FD93D38C40E551A875C1D7CE7B88EA74AFCC004DAC943A7E3BFDF599AEA3CFB184EFA8813E6ADA21','TGJhRu9zaFxyyaSWq2iyXLovvq3baugy5U', '41458063833CE040B738F3BDE63BA6738DB2D29F68'),
(2, 'jackB1', 1, 'CCD3959D4551058E65F8984CBD5F5A8B406973F10754BCCDE4B640A1061E5A0E', '040AEF573326EBDC792082319F06164620244DFF38970F1454FD93D38C40E551A875C1D7CE7B88EA74AFCC004DAC943A7E3BFDF599AEA3CFB184EFA8813E6ADA21','TGJhRu9zaFxyyaSWq2iyXLovvq3baugy5U', '41458063833CE040B738F3BDE63BA6738DB2D29F68'),
(3, 'jackB2', 1, 'CCD3959D4551058E65F8984CBD5F5A8B406973F10754BCCDE4B640A1061E5A0E', '040AEF573326EBDC792082319F06164620244DFF38970F1454FD93D38C40E551A875C1D7CE7B88EA74AFCC004DAC943A7E3BFDF599AEA3CFB184EFA8813E6ADA21','TGJhRu9zaFxyyaSWq2iyXLovvq3baugy5U', '41458063833CE040B738F3BDE63BA6738DB2D29F68'),
(4, 'jackC1', 1, 'CCD3959D4551058E65F8984CBD5F5A8B406973F10754BCCDE4B640A1061E5A0E', '040AEF573326EBDC792082319F06164620244DFF38970F1454FD93D38C40E551A875C1D7CE7B88EA74AFCC004DAC943A7E3BFDF599AEA3CFB184EFA8813E6ADA21','TGJhRu9zaFxyyaSWq2iyXLovvq3baugy5U', '41458063833CE040B738F3BDE63BA6738DB2D29F68'),
(5, 'jackC2', 1, 'CCD3959D4551058E65F8984CBD5F5A8B406973F10754BCCDE4B640A1061E5A0E', '040AEF573326EBDC792082319F06164620244DFF38970F1454FD93D38C40E551A875C1D7CE7B88EA74AFCC004DAC943A7E3BFDF599AEA3CFB184EFA8813E6ADA21','TGJhRu9zaFxyyaSWq2iyXLovvq3baugy5U', '41458063833CE040B738F3BDE63BA6738DB2D29F68')
;






drop table if exists t_agent;
create table t_agent(
  id int primary key auto_increment,
  p_uid int comment '上级用户id',
  uid int comment '用户id',
  username varchar(100) comment '用户名',
  level int comment '层级',
  child text comment '代理下级'
) comment '代理';



insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (0,1,'jack',0,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (1,2,'jackB1',1,'4,5,8,9,10,11,16,17');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (1,3,'jackB2',1,'6,7,12,13,14,15,18');

insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (2,4,'jackC1',2,'8,9,16,17');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (2,5,'jackC2',2,'10,11');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (3,6,'jackC3',2,'12,13,18');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (3,7,'jackC4',2,'14,15');

insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (4,8,'jackD1',3,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (4,9,'jackD2',3,'16,17');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (5,10,'jackD3',3,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (5,11,'jackD4',3,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (6,12,'jackD5',3,'18');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (6,13,'jackD6',3,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (7,14,'jackD7',3,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (7,15,'jackD8',3,'');

insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (9,16,'jackE1',4,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (9,17,'jackE2',4,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (12,18,'jackE3',4,'');



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


INSERT INTO cb_v2.t_statistics (`date`, `uid`, `username`, `bet_money`, `bet_profit_money`, `bet_payout_money`, `recharge_money`, `withdraw_money`, `update_time`) VALUES
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

insert into cb_v2.t_agent_rebate(`min`,`max`,`rebate`) values
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
    avatar varchar(100) COMMENT '头像',
    is_delete tinyint(1) default 0 COMMENT '是否删除 1：已删除；0：未删除',
    unique key uniq_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='后台用户表';
insert into cb_v2.t_user(username, pwd, avatar, is_delete) values ('admin', '123456', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', 0);
insert into cb_v2.t_user(username, pwd, avatar, is_delete) values ('jack', '123456', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', 0);
insert into cb_v2.t_user(username, pwd, avatar, is_delete) values ('tony', '123456', 'https://i.gtimg.cn/club/item/face/img/2/16022_100.gif', 0);


drop table if exists t_user_role;
CREATE TABLE t_user_role (
    id int(20) primary key auto_increment,
    user_id int COMMENT '用户ID',
    role_id int COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色表';
insert into cb_v2.t_user_role(user_id, role_id) values (1,1);
insert into cb_v2.t_user_role(user_id, role_id) values (1,2);
insert into cb_v2.t_user_role(user_id, role_id) values (2,2);
insert into cb_v2.t_user_role(user_id, role_id) values (2,3);
insert into cb_v2.t_user_role(user_id, role_id) values (3,1);
insert into cb_v2.t_user_role(user_id, role_id) values (3,3);


drop table if exists t_role;
CREATE TABLE t_role (
    id int primary key auto_increment,
    code varchar(100) COMMENT '编码',
    name varchar(100) COMMENT '名称',
    is_delete tinyint(1)  DEFAULT 0 COMMENT '是否删除 1：已删除；0：未删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';
insert into cb_v2.t_role(code, name, is_delete) values ('1000', '开发部', 0);
insert into cb_v2.t_role(code, name, is_delete) values ('1001', '测试部', 0);
insert into cb_v2.t_role(code, name, is_delete) values ('1003', '财务部', 0);




drop table if exists t_role_permission;
CREATE TABLE t_role_permission (
    id int(20) primary key auto_increment,
    role_id int(20) COMMENT '角色ID',
    permission_id int(20) COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关系表';
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,3);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,4);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,5);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,6);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,8);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,9);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,10);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,11);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,14);
insert into cb_v2.t_role_permission(role_id, permission_id) values (1,19);



drop table if exists t_permission;
CREATE TABLE t_permission (
    id int primary key auto_increment,
    path varchar(100) COMMENT '路径',
    component varchar(100) COMMENT '页面',
    redirect varchar(100) COMMENT '页面对应的地址',
    name varchar(100) COMMENT '名称',
    name_default varchar(100) COMMENT '名称',
    title varchar(100) comment '标题',
    icon varchar(255) COMMENT '图标',
    code int COMMENT '权限编码',
    node_type tinyint(4) DEFAULT 1 COMMENT '节点类型，1文件夹，2页面，3按钮',
    sort int(11) DEFAULT 1 COMMENT '排序号',
    level int(11) DEFAULT 0 COMMENT '层次',
    parent_id int COMMENT '父节点',
    parent_path varchar(100) COMMENT '树id的路径 整个层次上的路径id，逗号分隔',
    is_delete tinyint(1)  DEFAULT 0 COMMENT '是否删除 1：已删除；0：未删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

insert into cb_v2.t_permission(path, component, redirect, name, name_default, title, icon, code, node_type, sort, level, parent_id, parent_path, is_delete) values
('/dashboard','#','/dashboard/analysis', 'Dashboard', '会员管理', 'router.dashboard', 'ant-design:dashboard-filled', 10011, 1, 1, 1, 0, '', 0),
('analysis','views/Dashboard/Analysis','', 'Analysis', '会员中心', 'router.analysis', '', 0, 2, 1, 1, 1, '1', 0),
('','','', '', '添加会员', '', '', 10011, 3, 1, 1, 2, '2,1', 0),
('','','', '', '删除会员', '', '', 10011, 3, 1, 1, 2, '2,1', 0),
('','','', '', '修改会员', '', '', 10011, 3, 1, 1, 2, '2,1', 0),
('','','', '', '查询会员', '', '', 10011, 3, 1, 1, 2, '2,1', 0),


('workplace','views/Dashboard/Workplace','', 'Workplace', '会员流水', 'router.workplace', '', 0, 2, 1, 1, 1, '1', 0),
('','','', '', '添加会员', '', '', 10011, 3, 1, 1, 7, '7,1', 0),
('','','', '', '删除会员', '', '', 10011, 3, 1, 1, 7, '7,1', 0),
('','','', '', '修改会员', '', '', 10011, 3, 1, 1, 7, '7,1', 0),
('','','', '', '查询会员', '', '', 10011, 3, 1, 1, 7, '7,1', 0)
;

#
# insert into cb_v2.t_permission(name, name_code, code, parent_id, node_type, icon_url, sort, link_url, level, path, is_delete) values
# ('会员管理','1000','1000',0, 1, '', 10, '/member/index', 1, '', 0),
# ('会员中心','1000','1000',1, 2, '', 10, '/member/index', 2, '1', 0),
# ('添加会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),
# ('删除会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),
# ('修改会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),
# ('查询会员','1000','1000',2, 3, '', 10, '/admin/user/findPage', 3, '2,1', 0),
#
# ('会员流水','1000','1000',1, 2, '', 10, '/member/index', 2, '1', 0),
# ('添加流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),
# ('删除流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),
# ('修改流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),
# ('查询流水','1000','1000',7, 3, '', 10, '/admin/user/findPage', 3, '7,1', 0),
#
# ('代理管理','1000','1000',0, 1, '', 10, '/member/index', 1, '', 0),
# ('代理中心','1000','1000',12, 2, '', 10, '/member/index', 2, '', 0),
# ('添加代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),
# ('删除代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),
# ('修改代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),
# ('查询代理','1000','1000',13, 3, '', 10, '/admin/user/findPage', 3, '13,12', 0),
#
# ('代理佣金','1000','1000',12, 2, '', 10, '/member/index', 2, '', 0),
# ('添加佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0),
# ('删除佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0),
# ('修改佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0),
# ('查询佣金','1000','1000',18, 3, '', 10, '/admin/user/findPage', 3, '18,12', 0)
# ;







