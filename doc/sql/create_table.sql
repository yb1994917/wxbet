--wxbet 数据库表结构
--state状态 0=有效(默认),1=无效
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          	bigint(20)    NOT NULL    AUTO_INCREMENT,
  `openid`          varchar(128)  NOT NULL    COMMENT '微信openid',
  `create_time` 	datetime      NOT NULL    COMMENT '数据记录创建时间',
  `update_time` 	datetime      NOT NULL    COMMENT '数据记录更新时间',
  `nickname`        varchar(64)   NOT NULL    COMMENT '用户名称',
  `avatar`          varchar(64)   NOT NULL    COMMENT '用户图像',
  `state`  			int(2)        DEFAULT 1   COMMENT '状态: 0=未审核,1=正常,2=停止',
  `income`  	    int(11)       DEFAULT 0   COMMENT '已赚取,累计收入',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `user` (`id`,`openid`, `create_time`, `update_time`, `nickname`,`avatar`,`state`,`income`)
VALUES(10001,'oGRmQsxIGGdoxeXrGcG_lgDZWV_E', '2016-06-26 18:11:46', '2016-06-26 18:11:46','hello','',1,0);
INSERT INTO `user` (`id`,`openid`, `create_time`, `update_time`, `nickname`,`avatar`,`state`,`income`)
VALUES(10002,'oGRmQs1dsisW6G1TVtm9dfged4io', '2016-09-11 18:11:46', '2016-09-11 18:11:46','test','',1,0);

DROP TABLE IF EXISTS `bet`;
CREATE TABLE `bet` (
  `id`          	bigint(20)    NOT NULL    AUTO_INCREMENT,
  `create_time` 	datetime      NOT NULL    COMMENT '数据记录创建时间',
  `update_time` 	datetime                  COMMENT '数据记录更新时间',
  `content`         varchar(64)   NOT NULL    COMMENT '打赌内容,最多20汉字',
  `finish_time` 	datetime                  COMMENT '完成时间',
  `real_time` 		datetime                  COMMENT '实际完成时间',
  `amount`          decimal(10,2) DEFAULT 0   COMMENT '打赌支付金额,单位元',
  `state`  			int(2)        DEFAULT 0   COMMENT '状态: -1=创建失败,0=创建成功,1=目标完成,2=目标失败,3=终止',
  `participate`  	int(2)        DEFAULT 0   COMMENT '状态: 0=容许参与,1=停止参与',
  `visible`         int(2)        DEFAULT 0   COMMENT '状态: 0=公开,1=私密',
  `capital`         decimal(10,2) DEFAULT 0   COMMENT '打赌活动总资金池,单位元',
  `pv`         		int(11)       DEFAULT 0   COMMENT '浏览数',
  
  `user_id`         bigint(20)    NOT NULL    COMMENT 'userID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20000 DEFAULT CHARSET=utf8 COMMENT='打赌表';

alter table `bet` add column `pv` int(11) DEFAULT 0 COMMENT '浏览数' after `capital`;

DROP TABLE IF EXISTS `progress`;
CREATE TABLE `progress` (
  `id`          	bigint(20)    NOT NULL    AUTO_INCREMENT,
  `create_time` 	datetime      NOT NULL    COMMENT '数据记录创建时间',
  `content`         varchar(64)   NOT NULL    COMMENT '打赌过程记录内容',
  `pic`             varchar(64)   NOT NULL    COMMENT '打赌过程记录图片',
  
  `bet_id`          bigint(20)    NOT NULL    COMMENT 'betID',
  `user_id`         bigint(20)    NOT NULL    COMMENT 'userID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30000 DEFAULT CHARSET=utf8 COMMENT='打赌过程记录表';

DROP TABLE IF EXISTS `participate`;
CREATE TABLE `participate` (
  `id`          	bigint(20)    NOT NULL    AUTO_INCREMENT,
  `create_time` 	datetime      NOT NULL    COMMENT '数据记录创建时间',
  `name`         	varchar(64)   NOT NULL    COMMENT '用户名称,匿名显示匿名',
  `comment`         varchar(64)   NOT NULL    COMMENT '参与留言',
  `amount`          decimal(10,2) DEFAULT 0   COMMENT '打赌参与金额,单位元',
  
  `bet_id`          bigint(20)    NOT NULL    COMMENT 'betID',
  `user_id`         bigint(20)    NOT NULL    COMMENT '组织者userID',
  `partner_id`      bigint(20)    NOT NULL    COMMENT '参与者userID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30000 DEFAULT CHARSET=utf8 COMMENT='参与打赌记录表';

DROP TABLE IF EXISTS `distribution`;
CREATE TABLE `distribution` (
  `id`          	bigint(20)    NOT NULL    AUTO_INCREMENT,
  `create_time` 	datetime      NOT NULL    COMMENT '数据记录创建时间',
  `organizer`  		int(2)        DEFAULT 0   COMMENT '状态: 0=参与者,1=组织者',
  `state`  			int(2)        DEFAULT 0   COMMENT '状态: -1=创建失败,0=创建成功,1=目标完成,2=目标失败,3=终止',
  `name`         	varchar(64)   NOT NULL    COMMENT '用户名称,匿名显示匿名',
  `capital`         decimal(10,2) DEFAULT 0   COMMENT '打赌活动总资金池,单位元',
  `income`          decimal(10,2) DEFAULT 0   COMMENT '打赌收益金额,单位元',
  
  `bet_id`          bigint(20)    NOT NULL    COMMENT 'betID',
  `user_id`         bigint(20)    NOT NULL    COMMENT 'userID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30000 DEFAULT CHARSET=utf8 COMMENT='打赌结果资金分配表';