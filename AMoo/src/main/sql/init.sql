

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(30) DEFAULT NULL COMMENT '名称',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `address` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `password` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT '用户登录密码',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `flag` varchar(30) DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `role_id` int(11) DEFAULT NULL COMMENT '成员编号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hi2she2l`;
CREATE TABLE `hi2she2l` (
  `id` int(11) NOT NULL COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名字',
  `fade` varchar(100) DEFAULT NULL COMMENT '期限',
  `enable` int(11) DEFAULT NULL COMMENT '可用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

INSERT into user VALUES(1,'admin',23, '福建省松峰村', '25564ylb');
INSERT into role values(1,'超级管理员', 'ROLE_ADMIN'),(2,'普通用户', 'ROLE_USER');
INSERT into user_role VALUES(1,1,1),(2,1,2);