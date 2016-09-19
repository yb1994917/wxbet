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
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `bet`;
CREATE TABLE `bet` (
  `id`          	bigint(20)    NOT NULL    AUTO_INCREMENT,
  `create_time` 	datetime      NOT NULL    COMMENT '数据记录创建时间',
  `update_time` 	datetime      NOT NULL    COMMENT '数据记录更新时间',
  `content`         varchar(64)   NOT NULL    COMMENT '打赌内容,最多20汉字',
  `finish_time` 	datetime      NOT NULL    COMMENT '完成时间',
  `amount`          decimal(10,2) DEFAULT 0   COMMENT '打赌支付金额,单位元',
  `state`  			int(2)        DEFAULT 0   COMMENT '状态: -1=创建失败,0=创建成功,1=目标完成,2=目标失败,3=终止',
  `participate`  	int(2)        DEFAULT 0   COMMENT '状态: 0=容许参与,1=停止参与',
  `visible`         int(2)        DEFAULT 0   COMMENT '状态: 0=公开,1=私密',
  `capital`         decimal(10,2) DEFAULT 0   COMMENT '打赌活动总资金池,单位元',
  
  `user_id`         bigint(20)    NOT NULL    COMMENT 'userID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20000 DEFAULT CHARSET=utf8 COMMENT='打赌表';

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
  `user_id`         bigint(20)    NOT NULL    COMMENT 'userID',
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