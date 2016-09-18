--wxbet 数据库表结构
--state状态 0=有效(默认),1=无效
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          	bigint(20)    NOT NULL    AUTO_INCREMENT,
  `create_time` 	datetime      NOT NULL    COMMENT '数据记录创建时间',
  `update_time` 	datetime      NOT NULL    COMMENT '数据记录更新时间',
  `last_login_time` datetime      			  COMMENT '上次登录时间',
  `name`        	varchar(64)   NOT NULL    COMMENT '用户名称',
  `passwd`      	varchar(128)  NOT NULL    COMMENT '用户密码',
  `realname`    	varchar(128)              COMMENT '身份证真实名字',
  `role_id`        	bigint(20)     			  COMMENT '用户权限角色',
  `phone`       	varchar(64)   			  COMMENT '用户手机号',
  `email`       	varchar(256)  DEFAULT ''  COMMENT '用户邮箱',
  `state`  			int(2)        DEFAULT 1   COMMENT '状态: 0=未审核,1=正常,2=停止',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50000 DEFAULT CHARSET=utf8 COMMENT='用户表';