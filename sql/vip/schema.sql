/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2012/4/11 11:30:12                           */
/*==============================================================*/
CREATE DATABASE IF NOT EXISTS vip DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE vip;

drop table if exists activity;

drop table if exists degree;

drop table if exists jms_receive_log;

drop table if exists mission;

drop table if exists mission_exam_topic;

drop table if exists prerogative;

drop table if exists vip_user;

drop table if exists vip_member_userdegree;

drop table if exists vip_degree_updown_log;

drop table if exists vip_mission;

drop table if exists vip_prerogative;

drop table if exists vip_wealth_log;

drop table if exists cms_content;

drop table if exists betfollow_temp;

drop table if exists temp_vip_key;

/*==============================================================*/
/* Table: activity 活动基本信息表                                                         					*/
/*==============================================================*/
create table activity
(
   id                   	int not null auto_increment,
   update_time          	timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          	datetime not null,
   activity_name        	varchar(50) not null comment '活动名称',
   activity_icon        	varchar(100) not null comment '活动图标地址',
   activity_objects     	varchar(20) not null default '所有会员' comment '活动对象，参与活动的会员等级要求。如：所有会员',
   activity_platform_type 	tinyint not null default '1' comment '活动平台类型：1=web;2=wap',
   start_time           	datetime not null comment '活动开始时间',
   end_time             	datetime not null comment '活动结束时间',
   detail_url           	varchar(100) not null comment '活动落地url',
   recommend_home       	tinyint not null default 0 comment '是否推荐到首页最新活动。0=否；1=是',
   activity_award_rule  	varchar(1000) not null default '{}' comment '活动奖励规则',
   features             	varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             	bigint not null default 0 comment '扩展字段',
   status               	tinyint not null default 0 comment '-1=表示记录已删除',
   activity_intro       	varchar(255) comment '活动简介',
   primary key (id),
   index idx_update_time (update_time)
) engine=InnoDB default charset=utf8 comment '记录活动基本信息';


/*==============================================================*/
/* Table: degree 等级基本信息表                                 	*/
/*==============================================================*/
create table degree
(
   id                   int not null auto_increment,
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          datetime not null,
   degree_name          char(8) not null default '普通' comment '普通，白银，黄金，白金，钻石，金钻，金冠',
   degree_code          int not null default 1 comment '1,2,3,4,5,6,7',
   base_wealth          int not null default 0 comment '等级基本财富值',
   status               tinyint not null default 0 comment '-1=表示记录已删除',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   description          varchar(255) comment '描述字段，用于描述记录的详细信息',
   primary key (id),
   unique key uk_degree_degree_name (degree_name),
   unique key uk_degree_degree_code (degree_code)
) engine=InnoDB default charset=utf8 comment '会员等级基本信息表';

/*=========================================================================================*/
/* Table: jms_receive_log 接收ActiveMQ消息流水表(保留最近3个月数据和status=0|1的记录)      				   */
/*=========================================================================================*/
create table jms_receive_log
(
   id                   bigint not null auto_increment,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   msg_body             varchar(255) not null default '{}' comment 'json结构.需要包含：投注客户端(-投注客户端, 0-本站web, 1本站wap, 2新浪web等)和渠道属性',
   status               tinyint not null default 0 comment '0=待处理;1=处理中;2=已完成;3=处理失败',
   status_update_time	datetime not null comment '状态字段更新时间',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   primary key (id),
   index idx_msg_body (msg_body)
) engine=InnoDB default charset=utf8 comment '保存从ActiveMQ Broker中接收的消息.';

/*==============================================================*/
/* Table: mission 任务基本信息表                                							*/
/*==============================================================*/
create table mission
(
   id                   	int not null auto_increment,
   parent_id            	int not null default 0 comment '父级任务id',
   update_time          	timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          	datetime not null,
   mission_name         	varchar(20) not null comment '任务名称',
   mission_code         	int not null default 0 comment '任务代码，设计需要，无直接业务关联',
   mission_goal         	varchar(255) not null comment '任务目标，对任务的简要描述',
   mission_objects      	varchar(20) not null default 'd>=1' comment '任务对象,记录参与任务的会员等级要求,支持表达式如：d>1&&d<5',
   mission_start_time   	datetime not null comment '任务开始时间',
   mission_end_time     	datetime not null comment '任务结束时间',
   mission_display_num  	tinyint not null default 1 comment '任务显示顺序',
   mission_rule         	varchar(1000) not null default '{}' comment '任务验证规则定义',
   mission_type         	tinyint not null default 1 comment '任务类型包含：成长任务和活动任务，成长任务=1；活动任务=2;新手任务=3',
   mission_platform_type 	tinyint not null default 1 comment '任务平台类型，分web平台和wap平台；1=web;2=wap',
   mission_award_rule       varchar(1000) not null default '{}' comment '任务奖励规则定义，包含：财富值，彩金卡，充值优惠卡，json格式[{type:''value'',award:''value''},......]，awardType: 财富值=1; 彩金卡=2; 充值优惠卡=3.award: 财富值=数值; 彩金卡=数值; 充值优惠卡=充|送.如：[{awardType:1,award: 50},{type:2,award: 30},{awardType:3,award: ''50|20''}]，表示的含义：完成任务奖励财富值50点+一张奖励彩金卡30元+一张充50送20优惠卡.',
   ask_answer           	tinyint not null default 0 comment '是否答题类任务，如：智勇闯关。 0=否；1=是',
   mission_behavior_type 	tinyint not null default 0 comment '任务行为类型有：可重复行为类任务=0；单次行为类任务=1。单次行为类任务在会员接任务时，就需要检查该任务行为会员是否已完成',
   lpzx_activity_id         bigint not null default 0 comment '礼品中心关联的活动id，数据类型跟礼品中心的活动id一致,如果任务的奖励中包含礼品卡，则需要设置此字段。0表示不关联',
   status               	tinyint not null default 0 comment '-1=表示记录已删除',
   features             	varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             	bigint not null default 0 comment '扩展字段',
   mission_content      	varchar(1000) comment '任务内容，对任务的详细描述.包含任务奖励的描述信息',
   mission_progress     	varchar(255) comment '任务提示说明(开启任务后,提示做任务的地址)',
   mission_img              varchar(40) comment '任务包的图片地址',
   mission_url              varchar(200) comment '任务提示地址',
   primary key (id)
) engine=InnoDB default charset=utf8 comment '记录任务的基本信息，包含：任务名称、任务关联的url地址、任务奖励等。任务图标通过任务代码地址进行映射。';

/*==============================================================*/
/* Table: mission_exam_topic 答题类任务的题目信息表            					*/
/*==============================================================*/
create table mission_exam_topic
(
   id                   int not null auto_increment,
   mission_id           int not null default 0 comment '关联的任务id',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          datetime not null,
   topic_name           varchar(255) not null comment '题目名称',
   topic_display_num    tinyint not null default 1 comment '题目显示顺序',
   topic_type           tinyint not null default 1 comment '题目类型,1=单选题；2=多选题',
   topic_option         varchar(2000) not null default '{}' comment '题目选项,采用json格式。记录选项内容和是否正确答案',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   topic_tip_url        varchar(255) comment '题目答案提示url',
   primary key (id)
) engine=InnoDB default charset=utf8 comment '记录任务题目信息';

/*==============================================================*/
/* Table: prerogative 特权基本信息表                            							*/
/*==============================================================*/
create table prerogative
(
   id                   	int not null auto_increment,
   update_time          	timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          	datetime not null,
   prerogative_name     	varchar(20) not null comment '特权名称',
   prerogative_code     	int not null comment '特权代码',
   prerogative_title    	varchar(150) not null comment '特权副标题',
   require_degree_code  	int not null default 1 comment '特权的最低会员等级要求',
   prerogative_detail_url 	varchar(150) not null default 'http://www.aicai.com' comment '特权详情落地url',
   award_rule           	varchar(1000) not null comment '特权奖励规则为json结构。当开发新特权时，约定特权的数据格式。',
   award_validity       	int not null default 0 comment '特权奖励的有效期，从会员当前等级开始生效日期那天算起。如果为0或空，则不计算有效期。有效期按天计算。该字段待定（需求不明确）',
   hot_prerogative      	tinyint not null default 0 comment '是否为热门特权; 0=非热门；1=热门特权',
   prerogative_display_num 	int not null default 1 comment '特权显示顺序',
   features             	varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             	bigint not null default 0 comment '扩展字段',
   prerogative_intro    	varchar(255) comment '特权介绍',
   primary key (id),
   unique key uk_prerogative_prerogative_name (prerogative_name),
   unique key uk_prerogative_prerogative_code (prerogative_code),
   index idx_prerogative_display_num (prerogative_display_num)
) engine=InnoDB default charset=utf8 comment '记录特权的基本信息，特权图标通过特权代码进行映射。';

/*==============================================================*/
/* Table: vip_member_userdegree 会员与vip用户对应关系表.           */
/*==============================================================*/
create table vip_member_userdegree 
(
    member_id           bigint not null default 0 comment '会员id',
    account             varchar(40) binary not null,
    vip_user_id         bigint not null default 0 comment '用户id',
    update_time         timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_time         datetime not null,
    signup_time         datetime not null comment '注册时间',
    member_total_wealth int not null default 0 comment '会员帐号的总财富值.',
    init_integral		int not null default 0 comment '初始化时，帐号原来的总积分.',
    flag_sync   		tinyint not null default 1 comment '帐号等级同步到coreservice的标记, 1=已同步;0=未同步.',
    features            varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
    flag_bit            bigint not null default 0 comment '扩展字段',
    cert_no             varchar(40) binary comment '用户身份证号' ,
    name                varchar(40) binary comment '用户姓名',
    primary key (member_id),
    index idx_vip_user_id (vip_user_id),
    index idx_cert_no_name (cert_no,name),
    index idx_create_time (create_time),
    index idx_flag_sync (flag_sync),
    index idx_account (account)
) engine=InnoDB default charset=utf8 comment '记录用户与会员的对应关系,非实名认证用户vip_user_id,cert_no,name留空.';

/*==============================================================*/
/* Table: vip_user vip用户表                                 	*/
/*==============================================================*/
create table vip_user
(
   id                   bigint not null auto_increment,
   cert_no              varchar(40) binary not null comment '用户身份证号',
   name                 varchar(40) binary not null comment '用户姓名',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          datetime not null,
   degree_id            int not null default 0,
   degree_code          int not null default 1 comment '1,2,3,4,5,6,7',
   init_degree_code     int not null default 1 comment '会员初始等级代码',
   curr_wealth          int not null default 0 comment '当前等级的累积财富值',
   total_wealth         int not null default 0 comment '用户在爱彩网获得的所有财富值累计值,会员的各个帐号总财富值的合计值.',
   mission_total_wealth int not null default 0 comment '任务获得的总财富值',
   effect_start_time    datetime not null comment '当前级别生效时间',
   effect_end_time      datetime not null comment '当前级别失效时间',
   status               tinyint not null default 0 comment '-1=表示记录已删除',
   version              tinyint not null default 0 comment '记录版本,用于乐观锁',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   description          varchar(255) comment '描述字段，用于描述记录的详细信息。',
   primary key (id),
   index idx_cert_no_name (cert_no,name),
   index idx_degree_id_status (degree_id,status),
   index idx_update_time (update_time),
   index idx_effect_end_time (effect_end_time)
) engine=InnoDB default charset=utf8 comment '记录VIP用户基本信息.';

/*==============================================================*/
/* Table: vip_degree_updown_log                                 */
/*==============================================================*/
create table vip_degree_updown_log
(
   id                   bigint not null auto_increment,
   vip_user_id          bigint not null default 0 comment '用户id',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   member_id            bigint not null default 0,
   account             	varchar(40) binary not null,
   degree_code          int not null default 1 comment '1,2,3,4,5,6,7',
   effect_start_time    datetime not null comment '开始生效时间',
   effect_end_time      datetime not null comment '截止生效时间',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   primary key (id),
   index idx_vip_user_id (vip_user_id),
   index idx_effect_start_time (effect_start_time)
) engine=InnoDB default charset=utf8 comment '记录会员等级升降级，只插入和查询数据，没有更新操作';

/*==============================================================*/
/* Table: vip_mission                                           */
/*==============================================================*/
create table vip_mission
(
   id                   bigint not null auto_increment,
   vip_user_id          bigint not null default 0 comment '用户id',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          datetime not null,
   mission_id           int not null default 0 comment '关联的任务id',
   member_id            bigint not null default 0,
   account             	varchar(40) binary not null,
   mission_start_time   datetime not null comment '会员开启任务时间',
   mission_finish_time  datetime not null default '1970-01-01 00:00:00' comment '会员完成任务时间',
   mission_act_status   tinyint not null default 1 comment '1=进行中;2=已完成.',
   version              tinyint not null default 0 comment '记录版本,用于乐观锁',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   cert_no              varchar(40) binary comment '用户身份证号',
   name                 varchar(40) binary comment '用户姓名',
   primary key (id),
   index idx_vip_user_id (vip_user_id),
   index idx_member_id (member_id),
   index idx_update_time (update_time),
   index idx_create_time (create_time)
) engine=InnoDB default charset=utf8 comment '记录会员参与的任务信息';

/*==============================================================*/
/* Table: vip_prerogative                                       */
/*==============================================================*/
create table vip_prerogative
(
   id                   bigint not null auto_increment,
   vip_user_id          bigint not null default 0 comment '用户id',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   create_time          datetime not null,
   prerogative_id       int not null default 0 comment '关联的特权id',
   prerogative_name     varchar(10) not null comment '特权名称',
   prerogative_code     int not null comment '特权代码',
   use_info             varchar(500) default '{}' comment '特权使用情况记录,json结构,由具体特权自行定义.',  
   used_time            datetime not null default '1970-01-01 00:00:00' comment '使用特权时间',
   version              tinyint not null default 0 comment '记录版本,用于乐观锁',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   award_start_time     datetime comment '特权奖励有效开始时间，根据特权奖励有效期计算',
   award_end_time       datetime comment '特权奖励有效截止时间，根据特权奖励有效期计算',
   cert_no              varchar(40) binary comment '用户身份证号',
   name                 varchar(40) binary comment '用户姓名',
   primary key (id),
   index idx_vip_user_id (vip_user_id),
   index idx_prerogative_code (prerogative_code)
) engine=InnoDB default charset=utf8 comment '记录会员使用的特权信息';

/*==============================================================*/
/* Table: vip_wealth_log 会员财富值流水表(保留最近3个月数据)    				*/
/*==============================================================*/
create table vip_wealth_log
(
   id                   bigint not null auto_increment,
   vip_user_id          bigint not null default 0 comment '用户id',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   member_id            bigint not null default 0,
   account             	varchar(40) binary not null,
   wealth_item          varchar(20) not null default '投注增加' comment '财富值类目,基础财富值类型的类目为固定为：投注增加；任务财富值类目 ： 任务名称 + 任务序号.需讨论字段类型',
   wealth_type          tinyint not null default 1 comment '类型：基础财富值=1，任务财富值=2',
   wealth               int not null default 0 comment '会员单次行为获得的财富值，比如完成一个任务、进行一次投注',
   earn_time            datetime not null comment '获赠时间',
   features             varchar(1024) not null default '{}' comment '扩展字段,要求:json结构',
   flag_bit             bigint not null default 0 comment '扩展字段',
   earn_origin          varchar(255) comment '获赠来源，json结构，记录从其他业务系统中获得该财富值的源头，如：方案号，投注方式等信息。',
   cert_no              varchar(40) binary comment '用户身份证号',
   name                 varchar(40) binary comment '用户姓名',
   primary key (id),
   index idx_vip_user_id (vip_user_id),
   index idx_cert_no_name (cert_no,name),
   index idx_member_id (member_id),
   index idx_wealth_item (wealth_item),
   index idx_earn_time (earn_time)
) engine=InnoDB default charset=utf8 comment '记录会员的财富值流水信息';


/*==============================================================*/
/* Table: cms_content 会员系统首页内容管理表				        */
/*==============================================================*/
create table cms_content
(
	id					int not null auto_increment,
	name	            varchar(14)	not null,
	name_url			varchar(255) not null,
	content_type		tinyint not null default 1 comment '1=最新动态;2=明星会员推荐',
	degree_code         int not null default 1 comment '推荐会员的等级,由后台直接维护.',
	display_num			tinyint not null default 1 comment '显示顺序',
	detail_url			varchar(255),
	description			varchar(255),
	primary key(id)
) engine=InnoDB default charset=utf8 comment '用于维护会员系统首页的最新动态和明星会员推荐板块';

/*==============================================================*/
/* Table: betfollow_temp 合买跟单临时表                       */
/*==============================================================*/
create table betfollow_temp 
(
  id 					bigint not null auto_increment comment '主键',
  member_id 			bigint not null default 0 comment '会员id',
  order_id 				bigint not null default 0 comment '订单id',
  trans_type 			int not null default 0 comment '交易类型',
  update_time 			timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  create_time 			datetime not null comment '创建时间',
  failure_number 		int not null default 0 comment '失败次数',
  status 				tinyint not null default 1 comment '状态，1=未处理; 2=处理中; 3=处理成功; 4=处理失败',
  plan_no 				varchar(30) not null default '0' comment '方案号',
  msg_body 				varchar(255) not null default '{}' comment '对应的jms消息',
  primary key  (id),
  unique key UK_BT_OI (order_id),
  index IDX_BT_UT (update_time),
  index IDX_BT_FN (failure_number),
  index IDX_BT_S (status)
) engine=InnoDB default charset=utf8 comment '合买跟单临时表';

/*==============================================================*/
/* Table: temp_vip_key 唯一键临时表，用于处理并发问题，不做查询操作 	*/
/*==============================================================*/
create table temp_vip_key
(
  id 					bigint not null auto_increment comment '主键',
  vip_key				varchar(100) not null comment '唯一索引.',
  create_time          	timestamp not null default CURRENT_TIMESTAMP,
  primary key (id),
  unique key UK_VIP_KEY (vip_key)
) engine=InnoDB default charset=utf8 comment '唯一键临时表,用于处理并发问题，不做查询操作';


/*== 2012-09-18新增. ==*/
drop table if exists vip_activity_award;
drop table if exists vip_activity_props;

/*==============================================================*/
/* Table: vip_activity_award 会员参与活动获得奖品记录表.				*/
/*==============================================================*/
create table vip_activity_award
(
  	id 					bigint not null auto_increment comment '主键',
  	vip_user_id         bigint not null default 0 comment '用户id',
  	activity_id			int not null default 0 comment '活动id.',
  	activity_item_id	int not null default 0 comment '活动的明细id,由活动的业务规则自行定义.一个活动可能包含多个子项，每个子项都可以产生奖品.',
  	award_id			int not null default 0 comment '奖品id.业务相关字段,活动自行指定.同一个活动中的奖品id是唯一的.',
 	member_id 			bigint not null default 0 comment '帐号id,即奖品是某个帐号拿的.',
 	account         	varchar(40) binary not null,
  	award_type			int not null default 0 comment '奖品类型. 财富值=1; 彩金卡=2; 充值优惠卡=3',
  	award_num			int not null default 0 comment '可以计算的数值类奖品值,如财富值和彩金卡.',
  	award_str			varchar(50) not null default '0|0' comment '字符串的奖品值,如充值优惠卡，格式: 20|2表示充20送2元.',
   	create_time         datetime not null comment '记录创建时间，由应用主动指定.',
    version             tinyint not null default 0 comment '记录版本,用于乐观锁',
    features            varchar(255) not null default '{}' comment '扩展字段,要求:json结构',
    flag_bit            bigint not null default 0 comment '扩展字段',
  	primary key (id),
  	index IDX_VAA_VUI(vip_user_id),
  	index IDX_VAA_AI(activity_id),
  	index IDX_VAA_AII(activity_item_id),
  	index IDX_VAA_AT_AN(award_type,award_num),
  	index IDX_VAA_AT_AS(award_type,award_str)
) engine=InnoDB default charset=utf8 comment '会员参与活动获得奖品记录表.';

/*=============================================================================================*/
/* Table: vip_activity_props 会员参与活动获得的道具记录表，活动中的虚拟物品,如：中秋活动投注送月饼.		   */
/*=============================================================================================*/
create table vip_activity_props 
(
  	id 					bigint not null auto_increment comment '主键',
  	vip_user_id         bigint not null default 0 comment '用户id',
  	activity_id			int not null default 0 comment '活动id.',
 	member_id 			bigint not null default 0 comment '帐号id,即道具是某个帐号获得的.',
 	account         	varchar(40) binary not null,
  	props_origin_type	int not null default 0 comment '道具来源,由活动的业务规则自行定义.一个活动获得的道具途经有多个.',
  	props_num			int not null default 0 comment '道具获得的个数.',
  	update_time 		datetime not null comment '记录更新时间，由应用主动指定.',
   	create_time         datetime not null comment '记录创建时间，由应用主动指定.',
    version             tinyint not null default 0 comment '记录版本,用于乐观锁',
    features            varchar(255) not null default '{}' comment '扩展字段,要求:json结构',
    flag_bit            bigint not null default 0 comment '扩展字段',
  	primary key (id),
  	index IDX_VAP_VUI(vip_user_id),
  	index IDX_VAP_AI(activity_id),
  	index IDX_VAP_POT_PN(props_origin_type,props_num),
  	index IDX_VAP_CT(create_time)
) engine=InnoDB default charset=utf8 comment '会员参与活动获得的道具记录表，活动中的虚拟物品,如：中秋活动投注送月饼.';


/*=============================================================================================*/
/* Table: vip_activity_props  增加一个字段status，表示记录的状态.		   */
/*=============================================================================================*/
ALTER TABLE `vip_activity_props`     ADD COLUMN `status` TINYINT DEFAULT '0' NOT NULL COMMENT '状态值,-1表示为删除' AFTER `props_num`;

/*=============================================================================================*/
/* Table: vip_activity_join_log 会员参与活动流水表.		   */
/*=============================================================================================*/
CREATE TABLE `vip_activity_join_log` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `vip_user_id` bigint(20) NOT NULL COMMENT 'Vip用户ID',
  `activity_id` int(11) NOT NULL COMMENT '活动ID',
  `item_id` int(11) DEFAULT NULL COMMENT '子活动ID',
  `vip_award_id` int(11) default '-1' COMMENT '关联vip_activity_award的ID',
  `activity_data` int(11) default NULL COMMENT '活动数据，一般用于统计使用',
  `description` varchar(255) default NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '参与时间',
  `features` varchar(255) DEFAULT NULL,
  `flag_bit` bigint(20) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*== 2012-10-23新增，会员任务二期 ==*/
drop table if exists vip_mission_daily;
drop table if exists vip_mission_cumulation;
drop table if exists vip_mission_award;

/*==============================================================*/
/* Table: vip_mission_daily 会员参与每日任务记录表.					*/
/*==============================================================*/
create table vip_mission_daily
(
  	id 					bigint not null auto_increment comment '主键',
 	member_id 			bigint not null default 0 comment '帐号id.',
  	vip_user_id         bigint not null default 0 comment '会员id',
  	mission_id			int not null default 0 comment '任务id.',
  	parent_mission_id	int not null default 0 comment '父任务id.',
  	finish_time			datetime not null comment '任务完成时间.',
  	reward_time			datetime not null default '1970-01-01 00:00:00' comment '领取奖励时间.',
  	checksum			varchar(50) not null comment '唯一键，检查会员是否完成当天任务.由vipUserId_missionId_finishDay构成',
 	account         	varchar(40) binary not null,
 	bet_plan_id			bigint not null default 0 comment '方案id.',
 	status				tinyint not null default 0 comment '状态： 1=已完成；2=已领取.',
   	create_time         datetime not null comment '记录创建时间，由应用主动指定.',
   	features            varchar(255) not null default '{}' comment '扩展字段,要求:json结构',
  	flag_bit			bigint not null default 0 comment '扩展字段',
 	primary key (id),
 	unique key UK_VMD_CHECKSUM(checksum),
 	index IDX_VMD_VUI_MI_S(vip_user_id,mission_id,status),
 	index IDX_VMD_VUI_PMI_FT(vip_user_id,parent_mission_id,finish_time),
 	index IDX_VMD_CT(create_time)
) engine=InnoDB default charset=utf8 comment '会员参与每日任务记录表';

/*===============================================================================*/
/* Table: vip_mission_cumulation 会员累计投注任务记录表，用于计算用户是否满足累积/连续任务要求.*/
/*===============================================================================*/
create table vip_mission_cumulation
(
  	id 					bigint not null auto_increment comment '主键',
 	member_id 			bigint not null default 0 comment '帐号id.',
  	vip_user_id         bigint not null default 0 comment '会员id',
  	mission_id			int not null default 0 comment '任务id.',
 	account         	varchar(40) binary not null,
 	bet_plan_id			bigint not null default 0 comment '方案id.',
 	issue_no			varchar(100) not null default '0' comment '彩期.',
 	issue_order_num		int not null default 0 comment '已连续彩期数.',
  	checksum			varchar(50) not null comment '唯一键，一般用于处理当前彩期多次投注只计算一次的情况.由vipUserId_missionId_issueNo构成',
   	create_time         datetime not null comment '记录创建时间.',
   	features            varchar(255) not null default '{}' comment '扩展字段,要求:json结构',
  	flag_bit			bigint not null default 0 comment '扩展字段',
 	primary key (id),
 	unique key UK_VMC_CHECKSUM(checksum),
 	index IDX_VMC_VUI_MI(vip_user_id,mission_id),
 	index IDX_VMC_CT(create_time)
) engine=InnoDB default charset=utf8 comment ' 会员累计投注任务记录表，用于计算用户是否满足累积/连续任务要求.';

/*==============================================================*/
/* Table: vip_mission_award 会员参与任务获得的奖励记录表.			*/
/*==============================================================*/
create table vip_mission_award
(
  	id 					bigint not null auto_increment comment '主键',
 	member_id 			bigint not null default 0 comment '帐号id.',
  	vip_user_id         bigint not null default 0 comment '会员id',
  	mission_id			int not null default 0 comment '任务id.',
  	parent_mission_id	int not null default 0 comment '父任务id.',
 	account         	varchar(40) binary not null,
  	award_type			int not null default 0 comment '奖品类型. 财富值=1; 彩金卡=2; 充值优惠卡=3',
  	award_num			int not null default 0 comment '可以计算的数值类奖品值,如财富值和彩金卡.',
  	award_str			varchar(50) not null default '0|0' comment '字符串的奖品值,如充值优惠卡，格式: 20|2表示充20送2元.',
   	create_time         datetime not null comment '记录创建时间.',
    features            varchar(255) not null default '{}' comment '扩展字段,要求:json结构',
    flag_bit            bigint not null default 0 comment '扩展字段',
 	primary key (id),
 	index IDX_VMA_VUI_MI(vip_user_id,mission_id)
) engine=InnoDB default charset=utf8 comment '会员参与任务获得的奖励记录表';

/*== 2012-12-02新增，会员12月份活动回馈 ==*/
/*=============================================================================================*/
/* Table: vip_activity_join_log  增加字段member_id, account, status.		   					   */
/*=============================================================================================*/
alter table `vip_activity_join_log`  add column `member_id` bigint not null default 0 comment '帐号id.' after `id`;
alter table `vip_activity_join_log`  add column `account` varchar(40) binary not null default '0' comment '帐号id.' after `item_id`;
alter table `vip_activity_join_log`  add column `status` tinyint default '0' not null comment '状态值,-1表示为删除.' after `activity_data`;
alter table `vip_activity_join_log`  add column `platform_id` tinyint default '0' not null comment '平台id. 0=不区分; 1=WEB, 2=WAP ' after `activity_data`;

/*==============================================================================*/
/* Table: vip_activity_party 会员活动-多人游戏时的场次记录表，一般初始化数据.		*/
/*==============================================================================*/
drop table if exists vip_activity_party;
create table vip_activity_party 
(
    id                  bigint not null auto_increment comment '主键',
    activity_id         int not null default 0 comment '活动id. 与activity_item_id构成唯一记录.',
    activity_item_id    int not null default 0 comment '活动的明细id,由活动的业务规则自行定义.一个活动可能包含多个子项.',
    join_num            int not null default 0 comment '当前加入游戏的人数',
    status              tinyint not null default 0 comment '状态.',
    next_game_time      datetime not null default '1970-01-01 00:00:00' comment '下一局开始时间.减去系统当前时间为下一局等待时间.',
  	update_time 		datetime not null comment '记录更新时间，由应用主动指定.',
   	create_time         datetime not null comment '记录创建时间，由应用主动指定.',
    version             tinyint not null default 0 comment '记录版本,用于乐观锁',
    features            varchar(255) not null default '{}' comment '扩展字段,要求:json结构',
    flag_bit            bigint not null default 0 comment '扩展字段',
  	primary key (id)
) engine=InnoDB default charset=utf8 comment '会员活动-多人游戏时的场次记录表，一般初始化数据';


/*== 2012-12-03新增，并发时重复消息的处理. ==*/
/*==============================================================================*/
/* Table: jms_temp_key 并发时重复消息的处理，用于jms消息的排重.                       */
/*==============================================================================*/
drop table if exists jms_temp_key;
create table jms_temp_key 
(
  id                    bigint not null auto_increment comment '主键',
  msg_body              varchar(255) not null default '{}' comment '对应的jms消息',
  create_time           timestamp not null default CURRENT_TIMESTAMP,
  primary key (id),
  unique key UK_MSG_BODY (msg_body)
) engine=InnoDB default charset=utf8 comment 'jms消息排重处理表,用于处理并发问题，不做查询操作';

/*=============================================================================================*/
/* Table: activity  增加字段lpzx_activity_id		   					   */
/*=============================================================================================*/
alter table `activity`  add column `lpzx_activity_id` bigint not null default 0 comment '礼品中心关联的活动id，数据类型跟礼品中心的活动id一致,0表示不关联' after `id`;


/*=============================================================================================*/
/* Table: vip_prerogative_award  新建一个特权奖品		   					   */
/*=============================================================================================*/
CREATE TABLE `vip_prerogative_award` (
  `id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `vip_user_id` bigint(20) NOT NULL default '0' COMMENT '用户id',
  `prerogative_code` int(11) NOT NULL default '0' COMMENT '特权代码',
  `prerogative_name` varchar(40) NOT NULL default '0' COMMENT '特权名称',
  `award_id` int(11) NOT NULL default '0' COMMENT '奖品id.业务相关字段,自行指定.奖品id是唯一的.',
  `account` varchar(40) character set utf8 collate utf8_bin NOT NULL,
  `award_type` int(11) NOT NULL default '0' COMMENT '奖品类型. 财富值=1; 彩金卡=2; 充值优惠卡=3',
  `award_num` int(11) NOT NULL default '0' COMMENT '可以计算的数值类奖品值,如财富值和彩金卡.',
  `award_str` varchar(50) NOT NULL default '0|0' COMMENT '字符串的奖品值,如充值优惠卡，格式: 20|2表示充20送2元.',
  `award_data` varchar(500) default NULL COMMENT 'JSON结构，记录用户奖励获得的数据',
  `create_time` datetime NOT NULL COMMENT '记录创建时间，由应用主动指定.',
  `features` varchar(255) NOT NULL default '{}' COMMENT '扩展字段,要求:json结构',
  `flag_bit` bigint(20) NOT NULL default '0' COMMENT '扩展字段',
   PRIMARY KEY  (`id`),
   index IDX_VPA_VUI(vip_user_id),
   index IDX_VPA_PC_CT(prerogative_code,create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员特权奖品记录表.';

/*=============================================================================================*/
/* Table: vip_activity_rank  新建一个会员活动排名表		   					   */
/*=============================================================================================*/
CREATE TABLE `vip_activity_rank` (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `vip_user_id` bigint(20) NOT NULL COMMENT '会员ID',
  `account` varchar(100) NOT NULL COMMENT '账号',
  `member_id` bigint(20) NOT NULL COMMENT '用户ID',
  `type_id` int(2) NOT NULL COMMENT '类型ID',
  `ranking` int(8) NOT NULL COMMENT '排名',
  `rank_flag` int(8) NOT NULL COMMENT '排名标注',
  `rank_data` int(8) NOT NULL COMMENT '排名数据',
  `rank_json` varchar(200) NOT NULL COMMENT '排名JSON',
  `status` int(2) NOT NULL COMMENT '状态',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `features` varchar(500) NOT NULL COMMENT '扩展字段',
  `flag_bit` bigint(20) NOT NULL COMMENT '扩展字段',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员活动排名表'