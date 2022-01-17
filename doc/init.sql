
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dc_cms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;

USE `dc_cms`;

/*Table structure for table `sys_logininfor` */

DROP TABLE IF EXISTS `sys_logininfor`;

CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) COLLATE utf8mb4_bin DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '提示信息',
  `access_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统访问记录';

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '备注',
  `create_username` varchar(50) DEFAULT NULL,
  `modify_username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1032 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`is_frame`,`is_cache`,`menu_type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`,`create_username`,`modify_username`) values (1,'系统管理',0,4,'system',NULL,1,0,'M','0','0','','system','admin','2021-04-08 09:49:27','admin','2021-07-14 13:49:36','系统管理目录',NULL,NULL),(100,'用户管理',1,1,'user','system/user/index',1,0,'C','0','0','system:user:list','user','admin','2021-04-08 09:49:27','',NULL,'用户管理菜单',NULL,NULL),(101,'角色管理',1,2,'role','system/role/index',1,0,'C','0','0','system:role:list','peoples','admin','2021-04-08 09:49:27','',NULL,'角色管理菜单',NULL,NULL),(102,'菜单管理',1,3,'menu','system/menu/index',1,0,'C','0','0','system:menu:list','tree-table','admin','2021-04-08 09:49:27','admin','2021-04-12 14:05:37','菜单管理菜单',NULL,NULL),(1001,'用户查询',100,1,'','',1,0,'F','0','0','system:user:query','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1002,'用户新增',100,2,'','',1,0,'F','0','0','system:user:add','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1003,'用户修改',100,3,'','',1,0,'F','0','0','system:user:edit','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1004,'用户删除',100,4,'','',1,0,'F','0','0','system:user:remove','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1005,'用户导出',100,5,'','',1,0,'F','0','0','system:user:export','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1006,'用户导入',100,6,'','',1,0,'F','0','0','system:user:import','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1007,'重置密码',100,7,'','',1,0,'F','0','0','system:user:resetPwd','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1008,'角色查询',101,1,'','',1,0,'F','0','0','system:role:query','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1009,'角色新增',101,2,'','',1,0,'F','0','0','system:role:add','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1010,'角色修改',101,3,'','',1,0,'F','0','0','system:role:edit','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1011,'角色删除',101,4,'','',1,0,'F','0','0','system:role:remove','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1012,'角色导出',101,5,'','',1,0,'F','0','0','system:role:export','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1013,'菜单查询',102,1,'','',1,0,'F','0','0','system:menu:query','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1014,'菜单新增',102,2,'','',1,0,'F','0','0','system:menu:add','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1015,'菜单修改',102,3,'','',1,0,'F','0','0','system:menu:edit','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1016,'菜单删除',102,4,'','',1,0,'F','0','0','system:menu:remove','#','admin','2021-04-08 09:49:27','',NULL,'',NULL,NULL),(1025,'数据项管理',0,0,'data','data/sql/index',1,0,'C','0','0','manager:sqlInfo:list','job','admin','2021-07-09 15:40:50','admin','2021-11-10 16:21:01','',NULL,NULL),(1026,'数据项查询',1025,1,'data','data/sql/index',1,0,'F','0','0','manager:sqlInfo:query','list','admin','2021-07-09 15:42:49','admin','2021-08-11 16:04:44','',NULL,NULL),(1028,'数据源管理',0,2,'datasource','datasource/index',1,1,'C','0','0','manager:dataSource:list','application','admin','2021-07-14 13:53:18','admin','2021-07-28 11:32:41','',NULL,NULL),(1029,'数据源查询',1028,0,'',NULL,1,0,'F','0','0','manager:dataSource:query','#','admin','2021-07-14 13:54:12','admin','2021-08-11 16:01:36','',NULL,NULL),(1030,'数据源新增',1028,1,'',NULL,1,0,'F','0','0','manager:dataSource:add','#','admin','2021-08-11 15:13:54','',NULL,'',NULL,NULL),(1031,'数据源编辑',1028,2,'',NULL,1,0,'F','0','0','manager:dataSource:edit','#','admin','2021-08-11 15:14:21','admin','2021-08-11 15:14:55','',NULL,NULL);

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '公告标题',
  `notice_type` char(1) COLLATE utf8mb4_bin NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_username` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_username` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='通知公告表';

/*Data for the table `sys_notice` */

/*Table structure for table `sys_oper_log` */

DROP TABLE IF EXISTS `sys_oper_log`;

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) COLLATE utf8mb4_bin DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作地点',
  `oper_param` text COLLATE utf8mb4_bin COMMENT '请求参数',
  `json_result` mediumtext COLLATE utf8mb4_bin COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` text COLLATE utf8mb4_bin COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='操作日志记录';

/*Data for the table `sys_oper_log` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) COLLATE utf8mb4_bin DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` int(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_username` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_username` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色信息表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`role_key`,`role_sort`,`data_scope`,`menu_check_strictly`,`dept_check_strictly`,`status`,`del_flag`,`create_username`,`create_time`,`modify_username`,`update_time`,`remark`) values (1,'超级管理员','admin',1,'1',1,1,0,0,'admin','2021-07-30 15:54:15','',NULL,'超级管理员'),(2,'默认角色','default',2,'2',1,0,0,2,'admin','2021-07-30 15:54:15','admin','2021-08-18 13:36:36','普通角色');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

/*Data for the table `sys_role_menu` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '用户昵称',
  `type` int(2) DEFAULT '0' COMMENT '用户类型：0-平台注册用户,1-AD域用户,2-单点登录用户',
  `email` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) COLLATE utf8mb4_bin DEFAULT '' COMMENT '手机号码',
  `sex` int(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` text COLLATE utf8mb4_bin COMMENT '头像地址',
  `background` text COLLATE utf8mb4_bin COMMENT '背景图',
  `password` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '密码',
  `change_status` bit(1) DEFAULT NULL COMMENT '改变状态',
  `status` int(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `login_ip` varchar(128) COLLATE utf8mb4_bin DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_username` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_username` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户信息表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`user_name`,`nick_name`,`type`,`email`,`phone`,`sex`,`avatar`,`background`,`password`,`change_status`,`status`,`del_flag`,`login_ip`,`login_date`,`create_username`,`create_time`,`modify_username`,`update_time`,`remark`) values (1,'admin','管理员',0,'sinosoft@sinosoft.com','15888888888',1,'http://10.1.41.81:9000/sidecar-manager/94f89746d6094f6b90417745ba323f91.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20211229T082647Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604800&X-Amz-Credential=minioadmin%2F20211229%2Fcn-north-1%2Fs3%2Faws4_request&X-Amz-Signature=a7f69e9ec6b6122724451316abaaf0022cc56f3fbfe1b8ab444ee05e1dde472b','http://10.1.41.81:9000/sidecar-manager/524d5061ae2743e0a6bcee4dd2b9b06e.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20211229T032559Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604800&X-Amz-Credential=minioadmin%2F20211229%2Fcn-north-1%2Fs3%2Faws4_request&X-Amz-Signature=ee026684fd0a77d28e79c79a22aafa3a04676183e92aeba4ac7224fcdd85d7ce','$2a$10$FfhCoqe3vah4o1s7s4qQFeoMybVMbA5knR9Qc2JVtKdHuhvHeMepe','',0,0,'127.0.0.1','2021-04-08 09:49:21','admin','2021-04-08 09:49:21','15900000001','2021-11-05 17:04:55','管理员'),(4,'xiaowei','xiaowei',1,'','',2,NULL,NULL,'','',0,0,'',NULL,'','2022-01-12 18:30:56','','2022-01-13 15:00:15',NULL),(5,'shuifeifei','shuifeifei',1,'','',2,NULL,NULL,'','',0,0,'',NULL,'','2022-01-12 18:32:12','','2022-01-12 18:30:59',NULL),(6,'yurong','yurong',1,'','',2,NULL,NULL,'','',0,0,'',NULL,'','2022-01-12 18:32:50','','2022-01-12 18:30:59',NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户和角色关联表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1),(4,2),(5,2),(6,2);
