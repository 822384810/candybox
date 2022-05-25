--
-- Table structure for table `log_access`
--

DROP TABLE IF EXISTS `log_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_access` (
  `id` varchar(36) NOT NULL COMMENT 'Id',
  `url` varchar(400) DEFAULT NULL COMMENT 'url',
  `class_name` varchar(400) DEFAULT NULL COMMENT '类名',
  `method_name` varchar(200) DEFAULT NULL COMMENT '方法名',
  `opt_content` longtext COMMENT '操作内容',
  `opt_ip` varchar(400) DEFAULT NULL COMMENT '操作ip',
  `token_id` varchar(36) DEFAULT NULL COMMENT 'token_id',
  `use_time` bigint DEFAULT NULL COMMENT '操作用时',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `create_dept_id` varchar(36) DEFAULT NULL COMMENT '创建单位id',
  `create_dept_name` varchar(100) DEFAULT NULL COMMENT '创建单位名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='访问日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_access`
--

LOCK TABLES `log_access` WRITE;
/*!40000 ALTER TABLE `log_access` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_login`
--

DROP TABLE IF EXISTS `log_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_login` (
  `id` varchar(36) NOT NULL,
  `token_id` varchar(36) DEFAULT NULL COMMENT 'Token id',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `dept_id` varchar(36) DEFAULT NULL COMMENT '单位id',
  `dept_name` varchar(200) DEFAULT NULL COMMENT '单位名称',
  `login_dev` varchar(200) DEFAULT NULL COMMENT '登录设备',
  `login_ip` varchar(400) DEFAULT NULL COMMENT '登录ip',
  `login_source` varchar(20) DEFAULT NULL COMMENT '登录来源',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登录日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_login`
--

LOCK TABLES `log_login` WRITE;
/*!40000 ALTER TABLE `log_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_dept_info`
--

DROP TABLE IF EXISTS `user_dept_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_dept_info` (
  `id` varchar(36) NOT NULL COMMENT 'Id',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父id，第一级为0',
  `name` varchar(200) DEFAULT NULL COMMENT '单位名称',
  `short_name` varchar(100) DEFAULT NULL COMMENT '单位简称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新用户名称',
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效',
  PRIMARY KEY (`id`),
  KEY `i_udi_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_dept_info`
--

LOCK TABLES `user_dept_info` WRITE;
/*!40000 ALTER TABLE `user_dept_info` DISABLE KEYS */;
INSERT INTO `user_dept_info` VALUES ('1d3c44415bbd293fd87fe9c1fe0885fc','bd5c00c3725867bf91eec8d8d015b17f','德芙糖果盒','德芙','2022-05-20 21:55:58',NULL,NULL,'2022-05-20 22:00:18',NULL,NULL,1),('43f20066146ac9118e139c2539fbfc70','bd5c00c3725867bf91eec8d8d015b17f','小白兔糖果盒','小白兔','2022-05-20 21:55:39',NULL,NULL,NULL,NULL,NULL,1),('bd5c00c3725867bf91eec8d8d015b17f','0','糖果盒工厂','糖果盒','2022-05-20 21:53:41',NULL,NULL,'2022-05-23 23:27:44','24a7c90eb8dfce0646f86effd3bba551','管理员',1);
/*!40000 ALTER TABLE `user_dept_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` varchar(36) NOT NULL COMMENT 'Id',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `password` varchar(800) DEFAULT NULL COMMENT '密码',
  `mobile_phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `mail` varchar(128) DEFAULT NULL COMMENT '电子邮件',
  `id_type` int DEFAULT NULL COMMENT '增加类型：1身份证',
  `id_number` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `dept_id` varchar(36) DEFAULT NULL COMMENT '单位id',
  `dept_name` varchar(200) DEFAULT NULL COMMENT '单位名称',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(400) DEFAULT NULL COMMENT '最后登录ip',
  `last_login_dev` varchar(200) DEFAULT NULL COMMENT '最后登录设备',
  `last_login_source` varchar(20) DEFAULT NULL COMMENT '最后登录来源',
  `login_count` bigint DEFAULT NULL COMMENT '登录次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新用户名称',
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效',
  PRIMARY KEY (`id`),
  KEY `i_ui_nicke_name` (`nick_name`),
  KEY `i_ui_mobile_phone` (`mobile_phone`),
  KEY `i_ui_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('24a7c90eb8dfce0646f86effd3bba551','管理员','admin','0fffff81e971fa3f09107abf77931463fc0710bfb8962efeae3d5654b073bb0c','13811111111','',0,'11111111','bd5c00c3725867bf91eec8d8d015b17f','糖果盒工厂','2022-05-25 19:04:59',NULL,NULL,NULL,45,'2022-05-20 22:01:02',NULL,NULL,'2022-05-24 14:58:50','24a7c90eb8dfce0646f86effd3bba551','管理员',1);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_res_info`
--

DROP TABLE IF EXISTS `user_res_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_res_info` (
  `id` varchar(32) NOT NULL COMMENT 'Id',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父id：0为一级',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `descript` varchar(4000) DEFAULT NULL COMMENT '描述',
  `tag` varchar(200) DEFAULT NULL COMMENT '标签',
  `type` varchar(45) DEFAULT NULL COMMENT '类型:1菜单资源2操作资源3数据资源9其他资源',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新用户名称',
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效',
  PRIMARY KEY (`id`),
  KEY `i_uri_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_res_info`
--

LOCK TABLES `user_res_info` WRITE;
/*!40000 ALTER TABLE `user_res_info` DISABLE KEYS */;
INSERT INTO `user_res_info` VALUES ('9f039db1ba134722855cff3c3be609d9','d372914ec887d639a63e9632d53faafb','角色管理','{\n    \"img\":\"\",\n    \"url\":\"/user/role\",\n    \"text\":\"角色管理\"\n}','/user/role','1','2022-05-20 22:07:41',NULL,NULL,'2022-05-21 00:59:58',NULL,NULL,1),('a21fc9aeeb00af9cc14241c077230b81','d372914ec887d639a63e9632d53faafb','用户管理','{\n    \"img\":\"\",\n    \"url\":\"/user/user\",\n    \"text\":\"用户管理\"\n}','/user/user','1','2022-05-20 22:07:19',NULL,NULL,'2022-05-21 00:59:48',NULL,NULL,1),('c67eaa68e571efd68d4f7782c27435e6','d372914ec887d639a63e9632d53faafb','资源管理','{\n    \"img\":\"\",\n    \"url\":\"/user/res\",\n    \"text\":\"资源管理\"\n}','/user/res','1','2022-05-20 22:08:20',NULL,NULL,'2022-05-21 01:00:06',NULL,NULL,1),('d372914ec887d639a63e9632d53faafb','0','用户中心','{\n    \"img\":\"User\",\n    \"url\":\"\",\n    \"text\":\"用户中心\"\n}','user','1','2022-05-20 22:05:55',NULL,NULL,'2022-05-20 22:08:56',NULL,NULL,1);
/*!40000 ALTER TABLE `user_res_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_info`
--

DROP TABLE IF EXISTS `user_role_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_info` (
  `id` varchar(36) NOT NULL COMMENT 'Id',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `tag` varchar(100) DEFAULT NULL COMMENT '标签',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新用户名称',
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_info`
--

LOCK TABLES `user_role_info` WRITE;
/*!40000 ALTER TABLE `user_role_info` DISABLE KEYS */;
INSERT INTO `user_role_info` VALUES ('ac7f242215adde9b09d9ee6e061eed70','系统管理员','admin','2022-05-20 22:02:44',NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `user_role_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_relation`
--

DROP TABLE IF EXISTS `user_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_relation` (
  `id` varchar(36) NOT NULL COMMENT 'Id',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新用户名称',
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效',
  PRIMARY KEY (`id`),
  KEY `i_urr_user_id` (`user_id`),
  KEY `i_urr_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_relation`
--

LOCK TABLES `user_role_relation` WRITE;
/*!40000 ALTER TABLE `user_role_relation` DISABLE KEYS */;
INSERT INTO `user_role_relation` VALUES ('667dc90b62f7ed7ab3005f855b246683','24a7c90eb8dfce0646f86effd3bba551','ac7f242215adde9b09d9ee6e061eed70','2022-05-20 22:09:26',NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_res_relation`
--

DROP TABLE IF EXISTS `user_role_res_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_res_relation` (
  `id` varchar(36) NOT NULL COMMENT 'Id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  `res_id` varchar(36) DEFAULT NULL COMMENT '资源id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新用户名称',
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色资源对照表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_res_relation`
--

LOCK TABLES `user_role_res_relation` WRITE;
/*!40000 ALTER TABLE `user_role_res_relation` DISABLE KEYS */;
INSERT INTO `user_role_res_relation` VALUES ('6d8ef8944fe04db2cbdcca8963c1f1b8','ac7f242215adde9b09d9ee6e061eed70','d372914ec887d639a63e9632d53faafb','2022-05-20 22:09:44',NULL,NULL,NULL,NULL,NULL,1),('958a5fcf396dc06b55f76745c08344d0','ac7f242215adde9b09d9ee6e061eed70','a21fc9aeeb00af9cc14241c077230b81','2022-05-20 22:09:48',NULL,NULL,NULL,NULL,NULL,1),('e59227f743308edf4b2ded37ba11e3db','ac7f242215adde9b09d9ee6e061eed70','9f039db1ba134722855cff3c3be609d9','2022-05-20 22:09:51',NULL,NULL,NULL,NULL,NULL,1),('8c3d4d3815bd40dc192ea15a1dc4588d','ac7f242215adde9b09d9ee6e061eed70','c67eaa68e571efd68d4f7782c27435e6','2022-05-20 22:09:55',NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `user_role_res_relation` ENABLE KEYS */;
UNLOCK TABLES;

