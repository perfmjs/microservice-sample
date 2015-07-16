CREATE DATABASE IF NOT EXISTS youyue DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE youyue;

DROP TABLE IF EXISTS cms_article;
CREATE TABLE cms_article
(
	id varchar(64) NOT NULL COMMENT '编号',
	category_id varchar(64) NOT NULL COMMENT '栏目编号',
	title varchar(255) NOT NULL COMMENT '标题',
	link varchar(255) COMMENT '文章链接',
	color varchar(50) COMMENT '标题颜色',
	image varchar(255) COMMENT '文章图片',
	keywords varchar(255) COMMENT '关键字',
	description varchar(255) COMMENT '描述、摘要',
	weight int DEFAULT 0 COMMENT '权重，越大越靠前',
	weight_date datetime COMMENT '权重期限',
	hits int DEFAULT 0 COMMENT '点击数',
	posid varchar(10) COMMENT '推荐位，多选',
	custom_content_view varchar(255) COMMENT '自定义内容视图',
	view_config text COMMENT '视图配置',
	create_by varchar(64) COMMENT '创建者',
	create_date datetime COMMENT '创建时间',
	update_by varchar(64) COMMENT '更新者',
	update_date datetime COMMENT '更新时间',
	remarks varchar(255) COMMENT '备注信息',
	del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '文章表';