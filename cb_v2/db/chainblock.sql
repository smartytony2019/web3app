

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
   sort int(5) comment '序号',
   address varchar(100) comment '投注地址',
   algorithm varchar(10) comment '算法',
   odds decimal(10,2) comment '赔率'
) comment '彩种游戏';

insert into cb_v2.t_game(`cate_id`,`cate_name`,`cate_name_zh`,`name`,`name_zh`,`enable`,`pic`,`sort`,`address`, `algorithm`, `odds`) values
('1', '100010', '哈希', '200010', '哈希两面',1, 'http://xx/x.png', 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu', '1000', 1.95),
('1', '100010', '哈希', '200110', '哈希百家乐',1, 'http://xx/x.png', 1, '', '2000', 0),
('1', '100010', '哈希', '200210', '哈希PK拾',1, 'http://xx/x.png', 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nuA', '3000', 1.98),
('1', '100010', '哈希', '200310', '幸运哈希',1, 'http://xx/x.png', 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nuB', '4000', 1.97),
('1', '100010', '哈希', '200410', '哈希牛牛',1, 'http://xx/x.png', 1, 'TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nuC', '5000', 0),
('2', '100110', '彩票', '200510', 'XB彩票',1, 'http://xx/x.png', 1, '', '', 0),
('3', '100210', '体育', '200610', '皇冠体育',1, 'http://xx/x.png', 1, '', '', 0);






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
   type tinyint(1) comment '类型(1:体验房, 2:初级房, 3:中级房, 4:高级房)'
) comment '房间';

insert into cb_v2.t_hash_play(`cate_id`,`cate_name`,`cate_name_zh`, `game_id`, `game_name`, `game_name_zh`, `name`, `name_zh`, `min`, `max`, `max_odds`,`type`) values
('1', '100010', '哈希', 1, '200010', '哈希两面', '300010', '体验房', '0', '200', 1.95, 1),
('1', '100010', '哈希', 1, '200010', '哈希两面', '300011', '初级房', '50', '5000', 1.95, 2),
('1', '100010', '哈希', 1, '200010', '哈希两面', '300012', '中级房', '100', '10000', 1.95, 3),
('1', '100010', '哈希', 1, '200010', '哈希两面', '300013', '高级房', '1000', '20000', 1.95, 4),

('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300010', '体验房', '0', '200', 1.95, 1),
('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300011', '初级房', '50', '5000', 1.95, 2),
('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300012', '中级房', '100', '10000', 1.95, 3),
('1', '100010', '哈希', 2, '200110', '哈希百家乐', '300013', '高级房', '1000', '20000', 1.95, 4),

('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300010', '体验房', '0', '200', 1.98, 1),
('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300011', '初级房', '50', '5000', 1.98, 2),
('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300012', '中级房', '100', '10000', 1.98, 3),
('1', '100010', '哈希', 3, '200210', '哈希PK拾', '300013', '高级房', '1000', '20000', 1.98, 4),

('1', '100010', '哈希', 4, '200310', '幸运哈希', '300010', '体验房', '0', '200', 1.98, 2),
('1', '100010', '哈希', 4, '200310', '幸运哈希', '300011', '初级房', '0', '200', 1.98, 2),
('1', '100010', '哈希', 4, '200310', '幸运哈希', '300012', '中级房', '0', '200', 1.98, 3),
('1', '100010', '哈希', 4, '200310', '幸运哈希', '300013', '高级房', '0', '200', 1.98, 4),

('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300010', '体验房', '0', '200', 1.98, 1),
('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300011', '初级房', '50', '5000', 1.98, 2),
('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300012', '中级房', '100', '10000', 1.98, 3),
('1', '100010', '哈希', 5, '200410', '哈希牛牛', '300013', '高级房', '1000', '20000', 1.98, 4)
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
    transaction_id varchar(100) comment '交易id',
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
    algorithm varchar(10) comment '算法',
    UNIQUE KEY unique_sn (sn)
) comment '哈希注单';



drop table if exists t_hash_offline_bet;
create table t_hash_offline_bet (
    id int primary key auto_increment,
    sn varchar(100) comment '编号',
    cate_id int comment '类目id',
    cate_name varchar(50) comment '类目编码',
    cate_name_zh varchar(50) comment '类目名称(中文)',
    game_id int comment '彩种id',
    game_name varchar(50) comment '类目编码',
    game_name_zh varchar(50) comment '彩种名称(中文)',
    transaction_id varchar(100) comment '交易id',
    block_height varchar(50) comment '块高',
    block_hash varchar(80) comment '开奖结果',
    network varchar(50) comment '网络',
    odds decimal(10,4) comment '赔率',
    money decimal(10,4) comment '投注单价',
    profit_money decimal(10,4) comment '赢利金额',
    payout_money decimal(10,4) comment '派彩金额',
    create_time timestamp null default null comment '创建时间',
    create_timestamp bigint(20) comment '创建时间戳',
    update_time timestamp null default null comment '更新时间',
    update_timestamp bigint(20) comment '更新时间戳',
    flag int(5) default 0 comment '标记(1:赢, 2:输, 3: 和)',
    status int default 0 comment '状态(0:未结算,1:已结算,2:作废)',
    algorithm varchar(10) comment '算法',
    UNIQUE KEY unique_transaction_id (transaction_id)
) comment '哈希离线注单';


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


# *************************************************************
# 会员相关表
# *************************************************************

drop table if exists t_member;
create table t_member(
  id int primary key auto_increment,
  username varchar(50) comment '用户名',
  pwd varchar(100) comment '密码',
  money decimal(10,4) comment '金额',
  salt varchar(100) comment '盐',
  withdraw_wallet varchar(100) comment '提现钱包地址',
  withdraw_pwd varchar(100) comment '提现钱包密码',
  version int comment '版本',
  type int default 1 comment '类型(1:正常会员, 2:测试会员)',
  is_enable tinyint(1) default 1 comment '是否冻结(1:正常, 0:冻结)',
  UNIQUE KEY unique_username (username)
) comment '会员表';


insert into cb_v2.t_member (`username`,`pwd`,`money`,`salt`, `withdraw_wallet`,`withdraw_pwd`,`version`,`type`,`is_enable`) values
('jack','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackB1','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackB2','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackC1','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackC2','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackC3','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackC4','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD1','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD2','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD3','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD4','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD5','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD6','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD7','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackD8','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackE1','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackE2','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('jackE3','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('demo5566','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1),
('demo7788','2311519d5e6c7785c41cc712f273d77f',10000,'Br2m9o6J', 'TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe', '123456',1, 1, 1)
;

drop table if exists t_member_group;
create table t_member_group(
  id int primary key auto_increment,
  name varchar(50) comment '编码',
  name_zh varchar(50) comment '中文'
) comment '会员组表';
insert into cb_v2.t_member_group (name, name_zh) VALUES
('600010', '正式会员组'),
('600011', '测试会员组'),
('600012', '虚拟会员组')
;

drop table if exists t_member_expand;
create table t_member_expand(
  id int primary key auto_increment,
  uid int(20) comment 'uid',
  username varchar(50) comment '用户名',
  first_recharge_time timestamp null default null comment '首充时间',
  first_recharge_money decimal(10,2) comment '首充金额',
  bet_amount decimal(10,2) comment '投注量',
  withdraw_bet_amount decimal(10,2) comment '提现投注量'
) comment '会员扩展表';
insert into cb_v2.t_member_expand (uid, username, first_recharge_time, first_recharge_money, bet_amount, withdraw_bet_amount) VALUES
(19, 'demo5566', '2022-01-01 10:10:10', 100.00, 0, 0),
(19, 'demo7788', '2022-01-01 10:10:10', 100.00, 0, 0)
;



drop table if exists t_member_record;
create table t_member_record
(
  id int primary key auto_increment,
  uid int(20) comment '用户名',
  username varchar(50) comment '用户名',
  domain varchar(50) comment '域名',
  device timestamp null default null comment '设备',
  reg_ip varchar(50) comment '注册ip',
  reg_time timestamp null default null comment '注册时间',
  login_ip varchar(50) comment '登录ip',
  login_time timestamp null default null comment '登录时间'
) comment '会员记录表';


drop table if exists t_member_flow;
create table t_member_flow(
  id int primary key auto_increment,
  sn varchar(100) comment '订单号',
  uid int(20) comment '用户i',
  username varchar(50) comment '用户名',
  before_money decimal(10,2) comment '帐变前金额',
  after_money decimal(10,2) comment '帐变后金额',
  flow_money decimal(10,2) comment '流水金额',
  item varchar(100) comment '帐变项',
  item_code int comment '帐变编码',
  item_zh varchar(100) comment '帐变中文',
  create_time timestamp null default null comment '创建时间',
  ext varchar(200) comment '扩展字段',
  UNIQUE KEY unique_sn (sn)
) comment '会员流水表';





# ****************************************************************************************************************
# 活动相关表
# ****************************************************************************************************************


drop table if exists t_activity_cate;
create table t_activity_cate
(
    id int primary key auto_increment,
    name varchar(50) comment '名称编码',
    name_zh varchar(50) comment '名称中文'
) comment '活动类目表';
insert into cb_v2.t_activity_cate(name, name_zh) VALUES
('600010', '限时活动'),
('600011', '新手活动'),
('600012', '日常活动');


drop table if exists t_activity;
create table t_activity
(
    id int primary key auto_increment,
    cate_id int comment '类目id',
    cate_name varchar(50) comment '类目编码',
    cate_name_zh varchar(50) comment '类目中文',
    sn varchar(100) comment '编号',
    name varchar(100) comment '活动名称',
    content text comment '内容',
    sorted int comment '序号',
    type int comment '限制项(1:首充, 2:注册送, 3:签到, 10:其它)',
    language varchar(20) comment '语言',
    begin_time timestamp null default null comment '开始时间',
    finish_time timestamp null default null comment '结束时间',
    create_time timestamp null default null comment '创建时间',
    is_enable tinyint comment '是否启用'
) comment '活动表';
insert into cb_v2.t_activity(cate_id, cate_name, cate_name_zh, sn, name, content, sorted, type, language, begin_time, finish_time, create_time, is_enable) values
(1, '600010', '限时活动', '', '充值赠送', '', 1, 10, 'zh', '2022-08-30 00:00:00', '2022-08-30 00:00:00', '2022-08-30 00:00:00', 1),
(1, '600011', '新手活动', '', '首充赠送', '', 1, 1, 'zh', '2022-08-30 00:00:00', '2022-08-30 00:00:00', '2022-08-30 00:00:00', 1),
(1, '600011', '新手活动', '', '新注册赠送', '', 1, 2, 'zh', '2022-08-30 00:00:00', '2022-08-30 00:00:00', '2022-08-30 00:00:00', 1),
(1, '600012', '限时活动', '', '打码返水', '', 1, 10, 'zh', '2022-08-30 00:00:00', '2022-08-30 00:00:00', '2022-08-30 00:00:00', 1),
(1, '600012', '限时活动', '', '打码次数', '', 1, 10, 'zh', '2022-08-30 00:00:00', '2022-08-30 00:00:00', '2022-08-30 00:00:00', 1);



drop table if exists t_activity_rule;
create table t_activity_rule(
    id int primary key auto_increment,
    sn varchar(100) comment '编号',
    cycle int comment '周期(1:一次 2:不限次数 3:一天一次 4:一周一次 5:一月一次 6:自定义天数)',
    days int comment '天数',
    limit_item int comment '限制项(1:充值, 2:首充, 3:打码, 4:打码次数, 5:注册)',
    limit_lev int default 1 comment '限制等级(1: 包含项, 2: 必须项)',
    withdraw_bet_mul int comment '提现打码倍数',
    calc_mode int comment '计算方式(1:固定金额 2:百分比)',
    receive_mode int comment '领取方式(1:直接发放, 2:后端审核, 3:自动发放)',
    money decimal(10,2) comment '金额',
    symbol varchar(50) comment '赠送币种'
) comment '活动规则表';
insert into cb_v2.t_activity_rule(sn, cycle,days,limit_item,limit_lev,withdraw_bet_mul,calc_mode,receive_mode,money, symbol) values
('1000', 2, 0, 1, 1, 1, 1, 1, 0,'USDT'),
('2000', 1, 0, 2, 1, 1, 2, 1, 0,'TRX'),
('3000', 1, 0, 5, 1, 1, 1, 1, 10,'USDT'),
('4000', 2, 0, 3, 1, 1, 2, 1, 0,'USDT'),
('5000', 6, 3, 4, 1, 1, 1, 1, 0,'TRX');



drop table if exists t_activity_rule_item;
create table t_activity_rule_item (
  id int primary key auto_increment,
  sn varchar(100) comment '编号',
  type int comment '类型(1:等于 2:大于 3:大于等于 4:小于 5:小于等于 6:范围)',
  min int default 0 comment '最小值',
  max int default 0 comment '最大值',
  ratio decimal(10, 2) comment '赠送比例'
) comment '活动规则项表';
insert into t_activity_rule_item (sn, type, min, max, ratio) VALUES
('1000', 6, 0, 1000, 10),
('1000', 6, 1001, 5000, 20),
('1000', 6, 5001, 10000, 50),
('1000', 6, 10001, 20000, 100),
('1000', 6, 20001, 50000, 200),

('2000', 3, 100, 0, 100),

('3000', 1, 0, 0, 10),

('4000', 6, 0, 1000, 0.01),
('4000', 6, 1001, 5000, 0.02),
('4000', 6, 5001, 10000, 0.03),
('4000', 6, 10001, 20000, 0.04),
('4000', 6, 20001, 50000, 0.05),

('5000', 6, 1, 100, 10),
('5000', 6, 101, 500, 20),
('5000', 6, 501, 1000, 50),
('5000', 6, 1001, 2000, 100),
('5000', 6, 2001, 5000, 200)
;


drop table if exists t_activity_record;
create table t_activity_record (
    id int primary key auto_increment,
    activity_id int comment '活动id',
    activity_name varchar(50) comment '活动名',
    uid int default 0 comment 'uid',
    username varchar(50) comment '用户名',
    money decimal(10, 2) comment '赠送金额',
    symbol varchar(50) comment '赠送币种',
    status int default 0 comment '状态(0:未处理 1:成功 2:驳回)',
    create_time timestamp null default null comment '创建时间',
    remark varchar(100) comment '备注'
) comment '活动记录表';





drop table if exists t_finance;
create table t_finance(
  id int primary key auto_increment,
  uid int(20) comment 'uid',
  username varchar(50) comment '用户名',
  transaction_id varchar(100) comment '订单号',
  from_address varchar(100) comment '转帐地址',
  to_address varchar(100) comment '收款地址',
  money decimal(10,2) comment '充值金额',
  block_time timestamp null default null comment '充值时间',
  block_timestamp bigint comment '充值时间戳',
  symbol varchar(100) comment '币种',
  `type` int(5) comment '类型(1:充值, 2:提现)',
  is_account tinyint(1) default 0 comment '是否统计',
  UNIQUE KEY unique_transaction_id (transaction_id)
) comment '资金表';




create table t_operation_log(
  id int primary key auto_increment
) comment '会员流水表';




drop table if exists t_wallet;
create table t_wallet(
    id int primary key auto_increment,
    uid int comment '会员id',
    username varchar(50) comment '会员名',
    type int(10) comment '钱包类型',
    private_key varchar(100),
    public_key varchar(200),
    address_base58 varchar(100),
    address_hex varchar(100),
    is_main tinyint(1) default 0 comment '是否主钱包'
) comment '钱包';

insert into cb_v2.t_wallet(uid, username, type, private_key, public_key, address_base58, address_hex, is_main) values
(0,'',1,'EDF5BE9D97DAF6024D3A8BE7ECEC38EB95AD1B47C4620BC8790C22F9FD297C93', '04CF51A868293145E64B064D659A39282D9F136FE6B1273B423B641C987419425E214BD41FBE858C4C4AA7E557EB2E4DD61AB031E9DE1B33B9253DC6AB3E189144', 'TLRPmBB3wL21kg51ZTqsQSwwnejtpxNaYq', '4172A50E2D18F6F11D74AE6AF8CF8C94D7085B4826', 1),
(3, 'jackB2', 1, 'CCD3959D4551058E65F8984CBD5F5A8B406973F10754BCCDE4B640A1061E5A0E', '040AEF573326EBDC792082319F06164620244DFF38970F1454FD93D38C40E551A875C1D7CE7B88EA74AFCC004DAC943A7E3BFDF599AEA3CFB184EFA8813E6ADA21', 'TGJhRu9zaFxyyaSWq2iyXLovvq3baugy5U', '41458063833CE040B738F3BDE63BA6738DB2D29F68', 0),
(19, 'demo5566',1,'F9E0FF36CD981085BA854FD062756E5D8CC6232752A4A6F3AB58BEEF33E0BFBC', '04CA3097E79A93B179043D583D8FB5FFCFB711EF0B24D7EC14AD11097C5AFEA9A15D496173AE56F1AB02877BC868CF17AD79F8AC192D5F1341C07E9D75ACDD86D3', 'TQ5NbDWu1fQgzhq1LE4ej37RyLmDphAKm2','419ABC42DC5374064B3896D3DD382AD2080B8FF84E', 0),
(20, 'demo7777',1,'EE6B3AEA3402574D9C8DE3B723AB821E6DFCAA5F84B0DFD6E359215C4DA45DC2', '048549964D49E72389AF88827F64AAFC35CD8A185D709FBD17E77D29D382A1761F7A44F3F3384353B41BC14779606D6C54ADC09E365C916F0DDD8D2C4875FA0C43', 'TWLWZiN9etaSuSXsFQ9xNNRxbuqUxmfzXq', '41DF69E967E600BE5638239319ECEF64B465F038FF',0)
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
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (6,12,'jackD5',3,'18,19');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (6,13,'jackD6',3,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (7,14,'jackD7',3,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (7,15,'jackD8',3,'');

insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (9,16,'jackE1',4,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (9,17,'jackE2',4,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (12,18,'jackE3',4,'');
insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (12,19,'demo5566',4,'');

insert into cb_v2.t_agent(`p_uid`,`uid`,`username`,`level`,`child`) values (19,20,'demo5566',5,'');


drop table if exists t_statistics;
create table t_statistics(
  id int primary key auto_increment,
  `date` varchar(10) comment '日期',
  uid int comment '用户id',
  username varchar(100) comment '用户名',
  bet_count decimal(20,2) default 0 comment '当日投注次数',
  bet_amount decimal(20,2) default 0 comment '当日投注总额',
  profit_amount decimal(20,2) default 0 comment '当日盈利总额',
  recharge_trc20_count decimal(20,2) default 0 comment '充值trc20次数',
  recharge_trc20_amount decimal(20,2) default 0 comment '充值trc20总额',
  withdraw_trc20_amount decimal(20,2) default 0 comment '提现trc20总额',
  recharge_trx_count decimal(20,2) default 0 comment '充值trx次数',
  recharge_trx_amount decimal(20,2) default 0 comment '充值trx总额',
  withdraw_trx_amount decimal(20,2) default 0 comment '提现trx总额',
  activity_amount decimal(20,2) default 0 comment '活动总额',
  update_time timestamp null default null comment '更新时间',
  UNIQUE KEY unique_date_uid (`date`,uid) comment '联合索引(日期和用户id)'
) comment '统计';


INSERT INTO cb_v2.t_statistics (`date`, `uid`, `username`, `bet_count`, `bet_amount`,
                                `profit_amount`, `recharge_trc20_count`, `recharge_trc20_amount`, `withdraw_trc20_amount`, `recharge_trx_count`,
                                `recharge_trx_amount`, `withdraw_trx_amount`, `activity_amount`, `update_time`
                                ) VALUES
('20220820',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43'),
('20220821',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43'),
('20220822',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43'),
('20220823',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43'),
('20220824',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43'),
('20220825',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43'),
('20220826',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43'),
('20220827',19,'demo5566',1000.00,1000.00, 100000.00, 10, 1000, 1000, 10, 1000, 1000, 0, '2022-07-04 18:37:43');




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



drop table if exists t_system_config;
CREATE TABLE t_system_config(
    id        int primary key auto_increment,
    `key`     varchar(50) COMMENT '键',
    `value`   varchar(50) COMMENT '值',
    `type`    varchar(50) comment '数据类型(1: String 2:Integer, 3:Float, 4: Boolean, 5:Date)',
    `cate`    int comment '配置分类(1: 系统配置 2:会员配置, 3:游戏配置)',
    `comment` varchar(100) comment '备注'
);
insert into cb_v2.t_system_config(`key`, `value`, `type`, `cate`, `comment`) values
('maintain', 'true', 'Boolean', 1, '开启网站'),
('close_register', 'false', 'Boolean', 2, '关闭注册'),
('close_register', 'false', 'Boolean', 2, '注册必填推荐码'),
('withdraw_bet_mul', 'true', 'Boolean', 2, '提现打码倍数');



drop table if exists t_system_flow;
create table t_system_flow(
  id int primary key auto_increment,
  sn varchar(100) comment '订单号',
  uid int(20) comment '用户i',
  username varchar(50) comment '用户名',
  before_money decimal(10,2) comment '帐变前金额',
  after_money decimal(10,2) comment '帐变后金额',
  flow_money decimal(10,2) comment '流水金额',
  item varchar(100) comment '帐变项',
  item_code int comment '帐变编码',
  item_zh varchar(100) comment '帐变中文',
  create_time timestamp null default null comment '创建时间',
  create_timestamp bigint(20) comment '创建时间戳',
  ext varchar(200) comment '扩展字段',
  UNIQUE KEY unique_sn (sn)
) comment '会员流水表';




