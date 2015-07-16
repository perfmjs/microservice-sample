CREATE DATABASE IF NOT EXISTS lottery DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE lottery;

create table bet_game_issue
(
  ID                   int not null auto_increment,
  ISSUE_NO			varchar(100) not null default '0' comment '彩期.',
  GAME_ID               int not null default 0,
  IS_CURRENT           tinyint not null default 0 comment '是否当前期.',
  START_TIME           	datetime not null default CURRENT_TIMESTAMP comment '开始时间',
  END_TIME             	datetime not null default CURRENT_TIMESTAMP comment '结束时间',
  OPEN_TIME             	datetime not null default CURRENT_TIMESTAMP  comment '开奖时间',
  STATUS               	tinyint not null default 0 comment '-1=表示记录已删除',
  PRE_ISSUE             int null,
  RESULT                varchar(100) null,
  PRIZE_POOL            int null,
  BIT_FLAG             bigint not null default 0 comment '扩展字段',
  EXTEND_FEATURES      varchar(255) null comment '描述字段，用于描述记录的详细信息',
  primary key (id)
) engine=InnoDB default charset=utf8 comment '奖期表';