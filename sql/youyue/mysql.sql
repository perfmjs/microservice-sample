CREATE DATABASE IF NOT EXISTS youyue DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE youyue;

DROP TABLE IF EXISTS cms_channel;
CREATE TABLE cms_channel (
  ID INT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(10) NOT NULL COMMENT '频道名称',
  CREATE_TIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  BIT_FLAG BIGINT NOT NULL DEFAULT 0 COMMENT '位扩展字段',
  EXTEND_FEATURES VARCHAR(255) NOT NULL DEFAULT '{}' COMMENT '描述字段，用于描述记录的详细信息',
  PRIMARY KEY (ID)
) COMMENT = '新闻频道表';

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  ID INT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(45) NOT NULL COMMENT '用户名',
  PASSWD VARCHAR(45) NOT NULL COMMENT '密码（密文存放)',
  MOBILE VARCHAR(45) NOT NULL DEFAULT '' COMMENT '手机号',
  EMAIL VARCHAR(100) NOT NULL DEFAULT '',
  PHOTO VARCHAR(100) NOT NULL DEFAULT '' COMMENT '头像',
  CREATE_TIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  BIT_FLAG BIGINT NOT NULL DEFAULT 0 COMMENT '位扩展字段',
  EXTEND_FEATURES VARCHAR(255) NOT NULL DEFAULT '{}' COMMENT '描述字段，用于描述记录的详细信息',
  PRIMARY KEY (ID),
  UNIQUE INDEX name_UNIQUE (NAME ASC)
) COMMENT = '用户表';

DROP TABLE IF EXISTS cms;
CREATE TABLE cms (
  ID INT NOT NULL AUTO_INCREMENT,
  CHANNEL_NAME VARCHAR(10) NOT NULL COMMENT '频道名称,关联cms_channel表',
  TITLE VARCHAR(100) NOT NULL DEFAULT '' COMMENT '标题',
  IMG1 VARCHAR(100) NOT NULL DEFAULT '' COMMENT '描述图片1',
  IMG2 VARCHAR(100) NOT NULL DEFAULT '' COMMENT '描述图片2',
  IMG3 VARCHAR(100) NOT NULL DEFAULT '' COMMENT '描述图片3',
  LABEL VARCHAR(30) NOT NULL DEFAULT '' COMMENT '标签，多个以分号分割',
  SOURCE VARCHAR(20) NOT NULL DEFAULT '' COMMENT '新闻来源',
  SOURCE_URL VARCHAR(100) NOT NULL DEFAULT '' COMMENT '新闻来源地址',
  CONTENT VARCHAR(10000) NOT NULL DEFAULT '' COMMENT '新闻内容',
  LIKE_COUNT INT NOT NULL DEFAULT 0 COMMENT '点赞数',
  UNLIKE_COUNT INT NULL DEFAULT 0 COMMENT '反赞数',
  CREATE_TIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  BIT_FLAG BIGINT NOT NULL DEFAULT 0 COMMENT '位扩展字段',
  EXTEND_FEATURES VARCHAR(255) NOT NULL DEFAULT '{}' COMMENT '描述字段，用于描述记录的详细信息',
  PRIMARY KEY (ID)
) COMMENT = '新闻详细信息表';

DROP TABLE IF EXISTS cms_comment;
CREATE TABLE cms_comment (
  ID INT NOT NULL AUTO_INCREMENT,
	CMS_ID INT NOT NULL COMMENT '新闻ID,关联cms表',
	USER_NAME VARCHAR(45) NOT NULL COMMENT '用户名,关联user表',
	CONTENT VARCHAR(255) NOT NULL DEFAULT '' COMMENT '评论内容',
	LIKE_COUNT INT NOT NULL DEFAULT 0 COMMENT '点赞数',
  CREATE_TIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  BIT_FLAG BIGINT NOT NULL DEFAULT 0 COMMENT '位扩展字段',
  EXTEND_FEATURES VARCHAR(255) NOT NULL DEFAULT '{}' COMMENT '描述字段，用于描述记录的详细信息',
  PRIMARY KEY (ID)
) COMMENT = '新闻评论表';