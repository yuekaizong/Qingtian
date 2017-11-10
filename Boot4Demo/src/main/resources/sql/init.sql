

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `t_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `t_age` int(10) DEFAULT NULL COMMENT '年龄',
  `t_address` varchar(100) DEFAULT NULL COMMENT '家庭住址',
  `t_password` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT '用户登录密码',
  PRIMARY KEY (`t_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `r_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `r_flag` int(10) DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`r_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `ur_user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `ur_role_id` int(11) DEFAULT NULL COMMENT '成员编号',
  PRIMARY KEY (`ur_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

INSERT into t_user VALUES(1,'admin',23, '福建省松峰村', '123456');
INSERT into t_role values(1,'超级管理员', 'ROLE_ADMIN'),(2,'普通用户', 'ROLE_USER');
INSERT into t_user_role VALUES(1,1,1),(2,1,2);