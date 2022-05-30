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
  `token_id` varchar(800) DEFAULT NULL COMMENT 'token_id',
  `use_time` bigint DEFAULT NULL COMMENT '操作用时',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `create_dept_id` varchar(36) DEFAULT NULL COMMENT '创建单位id',
  `create_dept_name` varchar(100) DEFAULT NULL COMMENT '创建单位名称',
  PRIMARY KEY (`id`),
  KEY `i_la_create_time` (`create_time`)
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
  `token_id` varchar(800) DEFAULT NULL COMMENT 'Token id',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `dept_id` varchar(36) DEFAULT NULL COMMENT '单位id',
  `dept_name` varchar(200) DEFAULT NULL COMMENT '单位名称',
  `login_dev` varchar(200) DEFAULT NULL COMMENT '登录设备',
  `login_ip` varchar(400) DEFAULT NULL COMMENT '登录ip',
  `login_source` varchar(20) DEFAULT NULL COMMENT '登录来源',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`),
  KEY `i_ll_login_time` (`login_time`)
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
-- Table structure for table `oauth2_authorization`
--

DROP TABLE IF EXISTS `oauth2_authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_authorization` (
  `id` varchar(100) NOT NULL,
  `registered_client_id` varchar(100) NOT NULL,
  `principal_name` varchar(200) NOT NULL,
  `authorization_grant_type` varchar(100) NOT NULL,
  `attributes` blob,
  `state` varchar(500) DEFAULT NULL,
  `authorization_code_value` blob,
  `authorization_code_issued_at` timestamp NULL DEFAULT NULL,
  `authorization_code_expires_at` timestamp NULL DEFAULT NULL,
  `authorization_code_metadata` blob,
  `access_token_value` blob,
  `access_token_issued_at` timestamp NULL DEFAULT NULL,
  `access_token_expires_at` timestamp NULL DEFAULT NULL,
  `access_token_metadata` blob,
  `access_token_type` varchar(100) DEFAULT NULL,
  `access_token_scopes` varchar(1000) DEFAULT NULL,
  `oidc_id_token_value` blob,
  `oidc_id_token_issued_at` timestamp NULL DEFAULT NULL,
  `oidc_id_token_expires_at` timestamp NULL DEFAULT NULL,
  `oidc_id_token_metadata` blob,
  `refresh_token_value` blob,
  `refresh_token_issued_at` timestamp NULL DEFAULT NULL,
  `refresh_token_expires_at` timestamp NULL DEFAULT NULL,
  `refresh_token_metadata` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_authorization`
--

LOCK TABLES `oauth2_authorization` WRITE;
/*!40000 ALTER TABLE `oauth2_authorization` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2_authorization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_authorization_consent`
--

DROP TABLE IF EXISTS `oauth2_authorization_consent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_authorization_consent` (
  `registered_client_id` varchar(100) NOT NULL,
  `principal_name` varchar(200) NOT NULL,
  `authorities` varchar(1000) NOT NULL,
  PRIMARY KEY (`registered_client_id`,`principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_authorization_consent`
--

LOCK TABLES `oauth2_authorization_consent` WRITE;
/*!40000 ALTER TABLE `oauth2_authorization_consent` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2_authorization_consent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_registered_client`
--

DROP TABLE IF EXISTS `oauth2_registered_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_registered_client` (
  `id` varchar(100) NOT NULL,
  `client_id` varchar(100) NOT NULL,
  `client_id_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `client_secret` varchar(200) DEFAULT NULL,
  `client_secret_expires_at` timestamp NULL DEFAULT NULL,
  `client_name` varchar(200) NOT NULL,
  `client_authentication_methods` varchar(1000) NOT NULL,
  `authorization_grant_types` varchar(1000) NOT NULL,
  `redirect_uris` varchar(1000) DEFAULT NULL,
  `scopes` varchar(1000) NOT NULL,
  `client_settings` varchar(2000) NOT NULL,
  `token_settings` varchar(2000) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(36) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新用户名称',
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_registered_client`
--

LOCK TABLES `oauth2_registered_client` WRITE;
/*!40000 ALTER TABLE `oauth2_registered_client` DISABLE KEYS */;
INSERT INTO `oauth2_registered_client` VALUES ('e7628e9de0ecda237be42b8644c618a8','d15894ffb06b4cbdb1863ad88302158f','2022-05-26 14:00:27','{noop}b89d7f366dd4471eb42eb1e40c046ced',NULL,'clietn_test','client_secret_post','client_credentials',NULL,'oauth2-test','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",7200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]}','2022-05-26 22:00:27','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-30 10:15:34','24a7c90eb8dfce0646f86effd3bba551','管理员',1);
/*!40000 ALTER TABLE `oauth2_registered_client` ENABLE KEYS */;
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
INSERT INTO `user_dept_info` VALUES ('1d3c44415bbd293fd87fe9c1fe0885fc','bd5c00c3725867bf91eec8d8d015b17f','德芙巧克力','德芙','2022-05-20 21:55:58',NULL,NULL,'2022-05-25 21:56:29','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('43f20066146ac9118e139c2539fbfc70','bd5c00c3725867bf91eec8d8d015b17f','大白兔奶糖','小白兔','2022-05-20 21:55:39',NULL,NULL,'2022-05-25 21:56:41','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('bd5c00c3725867bf91eec8d8d015b17f','0','糖果盒','糖果盒','2022-05-20 21:53:41',NULL,NULL,'2022-05-25 21:56:48','24a7c90eb8dfce0646f86effd3bba551','管理员',1);
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
INSERT INTO `user_info` VALUES ('24a7c90eb8dfce0646f86effd3bba551','管理员','admin','0fffff81e971fa3f09107abf77931463fc0710bfb8962efeae3d5654b073bb0c','13811111111','',0,'11111111','bd5c00c3725867bf91eec8d8d015b17f','糖果盒','2022-05-30 10:14:25',NULL,NULL,NULL,75,'2022-05-20 22:01:02',NULL,NULL,'2022-05-28 21:42:16','24a7c90eb8dfce0646f86effd3bba551','管理员',1);
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
  `type` varchar(45) DEFAULT NULL COMMENT '类型:1菜单资源2操作资源3数据资源4接口资源9其他资源',
  `api_url` varchar(400) DEFAULT NULL COMMENT '接口地址',
  `sort` int DEFAULT NULL COMMENT '排序',
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
INSERT INTO `user_res_info` VALUES ('0655c3577c62d59ce7d5def185061533','a21fc9aeeb00af9cc14241c077230b81','用户列表','','','4','/api/user/user/page',20102,'2022-05-28 19:55:03','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:41:11','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('0d50de05c87b1bc27104afa76db8348b','a21fc9aeeb00af9cc14241c077230b81','授予角色查询','','','4','/api/user/role/relation/user/page',20105,'2022-05-28 21:44:46','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('103d6bc1a345f6ac714910de8e92d37f','7fc21e1d812a6c8274c0e09772e73180','登录日志','{ \n    \"url\":\"/page/monitor_loglogin\" \n}','monitor_loglogin','1','get:/pages/monitor/loglogin.json',3001,'2022-05-27 23:44:59','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-27 23:58:36','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('15f156e41259d476e27b9b498901558d','dcf0e5362d4db08fc8f095457e7e6159','认证列表','','','4','/api/core/cbdata/oauth_client/page',20401,'2022-05-29 14:34:45','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('2673083e5168a34e69faed0445183a6e','c67eaa68e571efd68d4f7782c27435e6','菜单资源树','','','4','/api/user/res/tree/all',20301,'2022-05-28 21:06:22','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:38:24','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('2ea7eea8849faede0edd88c967c123e1','78ca34707694b74b3c4b8cb1dc63b2f1','amis文档','{\n    \"link\":\"https://aisuda.bce.baidu.com/amis/zh-CN/docs/index\"\n}','docs_amis','1','',3102,'2022-05-28 00:14:34','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('31d6ae5429587398dc639a863f9aaf68','c67eaa68e571efd68d4f7782c27435e6','授予角色查询','','','4','/api/user/role/relation/res/page',20304,'2022-05-28 21:12:58','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:38:41','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('4200f2808474e21ac36f140ede2e4c59','c67eaa68e571efd68d4f7782c27435e6','资源列表','','','4','/api/core/cbdata/user_res/page',20302,'2022-05-28 21:05:34','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:38:30','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('5546b4de44f83938fb6d64e461bd214d','78ca34707694b74b3c4b8cb1dc63b2f1','swagger文档','{\n    \"url\": \"/page/docs_swagger\",\n    \"schema\":\n    {\n        \"type\": \"page\",\n        \"body\":\n        {\n            \"type\": \"iframe\",\n            \"src\": \"/swagger-ui/index.html\"\n        }\n    }\n }','docs_swagger','1','',3101,'2022-05-28 00:03:14','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 00:13:03','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('6a5fab974254531138f81eb6ec2df189','dcf0e5362d4db08fc8f095457e7e6159','认证增加','','','4','/api/user/oauth',20402,'2022-05-29 14:35:43','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-30 10:11:06','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('714c94393e07f20436b490c701e33f28','0','首页','{\n    \"icon\":\"fa fa-home\",\n    \"url\":\"/page/user_user\",\n    \"default\":\"true\"\n}','','1','get:/pages/user/user.json',1,'2022-05-27 19:35:19','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-27 23:36:53','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('71e0e401404e9711b0c409b0412550ae','7fc21e1d812a6c8274c0e09772e73180','访问日志','{\n    \"url\":\"/page/monitor_logaccess\"\n }','monitor_logaccess','1','get:/pages/monitor/logaccess.json',3002,'2022-05-27 23:46:50','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-27 23:58:42','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('75e9110cf443b440d0cd8eff356eaf7d','dcf0e5362d4db08fc8f095457e7e6159','授予角色保存','','','4','/api/user/role/relation',20404,'2022-05-29 14:52:03','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('777b38feceafee31d1d4ec491615a13b','a21fc9aeeb00af9cc14241c077230b81','用户删除','','','4','/api/core/cbdata/user_user',20104,'2022-05-28 21:43:40','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('78ca34707694b74b3c4b8cb1dc63b2f1','0','API文档','{\n    \"icon\":\"fa fa-book\"\n}','docs','1','',31,'2022-05-27 23:57:43','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-27 23:59:43','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('7fc21e1d812a6c8274c0e09772e73180','0','系统监控','{\n    \"icon\":\"fa fa-binoculars\"\n}','monitor','1',NULL,30,'2022-05-27 23:39:42','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-27 23:59:36','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('96c31399d4986547146dc332c7f04a0b','dcf0e5362d4db08fc8f095457e7e6159','认证编辑','','','4','/api/core/cbdata/oauth_client',20403,'2022-05-30 10:11:55','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-30 10:12:54','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('9f039db1ba134722855cff3c3be609d9','d372914ec887d639a63e9632d53faafb','角色管理','{\n    \"url\":\"/page/user_role\"\n}','user_role','1','get:/pages/user/role.json',202,'2022-05-20 22:07:41',NULL,NULL,'2022-05-27 23:56:25','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('a21fc9aeeb00af9cc14241c077230b81','d372914ec887d639a63e9632d53faafb','用户管理','{\n    \"url\":\"/page/user_user\"\n}','user_user','1','get:/pages/user/user.json',201,'2022-05-20 22:07:19',NULL,NULL,'2022-05-27 23:56:20','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('b391b36b45de01ce1717b566923d157a','c67eaa68e571efd68d4f7782c27435e6','授予角色保存','','','4','/api/user/res/relation',20305,'2022-05-28 21:13:50','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:38:46','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('b7fd56a8406e325014fd52103359fe77','714c94393e07f20436b490c701e33f28','左侧菜单','','','4','/api/user/res/menu/user/all',101,'2022-05-28 19:43:38','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:07:23','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('ba47faa42219d686bb853cb1e175611a','a21fc9aeeb00af9cc14241c077230b81','组织机构树','','','4','/api/user/dept/tree',20101,'2022-05-28 19:54:19','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 19:55:11','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('c5dac60a4b496e8483cdb8af309392d0','103d6bc1a345f6ac714910de8e92d37f','登录日志列表','','','4','/api/core/cbdata/log_login/page',300101,'2022-05-28 21:47:24','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:48:11','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('c67eaa68e571efd68d4f7782c27435e6','d372914ec887d639a63e9632d53faafb','资源管理','{    \n     \"url\":\"/page/user_res\"\n }','user_res','1','get:/pages/user/res.json',203,'2022-05-20 22:08:20',NULL,NULL,'2022-05-27 23:56:32','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('c8cf7ccb615f9d14da23b1cb4842b403','dcf0e5362d4db08fc8f095457e7e6159','授予角色查询','','','4','/api/user/role/relation/user/page',20405,'2022-05-29 14:51:10','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-30 10:13:04','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('ce8e23d33ee37c6fc2de25b1a7fcd12b','9f039db1ba134722855cff3c3be609d9','角色列表','','','4','/api/core/cbdata/user_role/page',20201,'2022-05-28 21:39:02','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:40:05','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('d372914ec887d639a63e9632d53faafb','0','用户中心','{\n    \"icon\":\"fa fa-user\"\n}','user','1',NULL,2,'2022-05-20 22:05:55',NULL,NULL,'2022-05-27 23:37:11','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('dcf0e5362d4db08fc8f095457e7e6159','d372914ec887d639a63e9632d53faafb','oauth2认证','{\n     \"url\":\"/page/user_oauth2\" \n}','user-oauth2','1','get:/pages/user/oauth.json',204,'2022-05-29 14:32:23','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-29 14:33:04','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('dfe50cbdfb580d8f47a17e47f7f90900','c67eaa68e571efd68d4f7782c27435e6','资源增删改','','','4','/api/core/cbdata/user_res',20303,'2022-05-28 21:06:54','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:38:35','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('e4ad7baf7e90d26aa06f952c07f49d85','a21fc9aeeb00af9cc14241c077230b81','授予角色保存','','','4','/api/user/role/relation',20106,'2022-05-28 21:45:43','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('ef7a2e6fd42d77ece618d9ff44f198cc','9f039db1ba134722855cff3c3be609d9','角色增删改','','','4','/api/core/cbdata/user_role',20202,'2022-05-28 21:40:00','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('f803024fa6043fb52571b7226ffcd3cc','a21fc9aeeb00af9cc14241c077230b81','用户增改','','','4','/api/user/user',20103,'2022-05-28 21:41:55','24a7c90eb8dfce0646f86effd3bba551','管理员','2022-05-28 21:43:23','24a7c90eb8dfce0646f86effd3bba551','管理员',1),('fd93816444f72eb4a338919ce15207b6','71e0e401404e9711b0c409b0412550ae','访问日志列表','','','4','/api/core/cbdata/log_access/page',300201,'2022-05-28 21:48:53','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1);
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
INSERT INTO `user_role_info` VALUES ('8916bf9f832bbf6d16ba810fe113ba24','oath2认证角色测试','oauth2-test','2022-05-29 14:25:29','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('ac7f242215adde9b09d9ee6e061eed70','系统管理员','admin','2022-05-20 22:02:44',NULL,NULL,'2022-05-28 21:40:18','24a7c90eb8dfce0646f86effd3bba551','管理员',1);
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
INSERT INTO `user_role_relation` VALUES ('667dc90b62f7ed7ab3005f855b246683','24a7c90eb8dfce0646f86effd3bba551','ac7f242215adde9b09d9ee6e061eed70','2022-05-20 22:09:26',NULL,NULL,NULL,NULL,NULL,1),('bd0300878da90980a15b55da2dab3b54','978fbe467fe66c53b39b4bff92fb9dd3','ac7f242215adde9b09d9ee6e061eed70','2022-05-28 21:45:55','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('c75446a88b6a7233f4c73e9ba90639a2','e7628e9de0ecda237be42b8644c618a8','8916bf9f832bbf6d16ba810fe113ba24','2022-05-29 14:48:23','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1);
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
  `status` int DEFAULT NULL COMMENT '是否有效：1有效0无效',
  PRIMARY KEY (`id`),
  KEY `i_urrr_role_id` (`role_id`),
  KEY `i_urrr_res_id` (`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色资源对照表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_res_relation`
--

LOCK TABLES `user_role_res_relation` WRITE;
/*!40000 ALTER TABLE `user_role_res_relation` DISABLE KEYS */;
INSERT INTO `user_role_res_relation` VALUES ('092450a241c6f53d4ec6d7edadda275a','ac7f242215adde9b09d9ee6e061eed70','c8cf7ccb615f9d14da23b1cb4842b403','2022-05-29 14:52:09','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('0bdadd6e3e41efee8f2359a8824c878c','8916bf9f832bbf6d16ba810fe113ba24','b7fd56a8406e325014fd52103359fe77','2022-05-29 14:25:54','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('0ffae1ae470436d37c65f89320630e42','ac7f242215adde9b09d9ee6e061eed70','78ca34707694b74b3c4b8cb1dc63b2f1','2022-05-27 23:59:06','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('1abb9e04f6739014e1e376c1e50f4060','ac7f242215adde9b09d9ee6e061eed70','ce8e23d33ee37c6fc2de25b1a7fcd12b','2022-05-28 21:39:08','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('220f11f16c226a52bde6c2e893d275ab','ac7f242215adde9b09d9ee6e061eed70','ef7a2e6fd42d77ece618d9ff44f198cc','2022-05-28 21:40:13','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('24e5d047dd2d4f4f8476c6814085f490','ac7f242215adde9b09d9ee6e061eed70','71e0e401404e9711b0c409b0412550ae','2022-05-27 23:48:38','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('2d6b052d9731530381770ccf41724b21','ac7f242215adde9b09d9ee6e061eed70','0655c3577c62d59ce7d5def185061533','2022-05-28 21:12:14','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('34e3d1e22be03f6825699259f4356497','ac7f242215adde9b09d9ee6e061eed70','4200f2808474e21ac36f140ede2e4c59','2022-05-28 21:14:26','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('3ed7a7fa0efab69a305a5645e5ab0b1e','ac7f242215adde9b09d9ee6e061eed70','5546b4de44f83938fb6d64e461bd214d','2022-05-28 00:03:20','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('459df524deeabf370e9151d28432cb26','ac7f242215adde9b09d9ee6e061eed70','777b38feceafee31d1d4ec491615a13b','2022-05-28 21:43:46','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('471c85ac06917e78c9602c26307c937b','ac7f242215adde9b09d9ee6e061eed70','31d6ae5429587398dc639a863f9aaf68','2022-05-28 21:13:12','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('4c1f0acd36ad058a4d99dd4d2ec0d04c','ac7f242215adde9b09d9ee6e061eed70','b391b36b45de01ce1717b566923d157a','2022-05-28 21:14:35','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('651c9331c8c22308738a468c854bc782','ac7f242215adde9b09d9ee6e061eed70','b7fd56a8406e325014fd52103359fe77','2022-05-28 19:46:18','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('68eda886aaa28ed22992fe0a30ce0b12','ac7f242215adde9b09d9ee6e061eed70','dfe50cbdfb580d8f47a17e47f7f90900','2022-05-28 21:14:29','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('6d8ef8944fe04db2cbdcca8963c1f1b8','ac7f242215adde9b09d9ee6e061eed70','d372914ec887d639a63e9632d53faafb','2022-05-20 22:09:44',NULL,NULL,NULL,NULL,NULL,1),('778dd01fd1a7ee2d54aa02e5157e361c','ac7f242215adde9b09d9ee6e061eed70','e4ad7baf7e90d26aa06f952c07f49d85','2022-05-28 21:45:49','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('783e750190c3e8f1b03a4e67ae36f9ae','ac7f242215adde9b09d9ee6e061eed70','dcf0e5362d4db08fc8f095457e7e6159','2022-05-29 14:33:26','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('7e76e747ebe29fb7c70df0b9c59949d4','ac7f242215adde9b09d9ee6e061eed70','ba47faa42219d686bb853cb1e175611a','2022-05-28 21:12:18','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('8c3d4d3815bd40dc192ea15a1dc4588d','ac7f242215adde9b09d9ee6e061eed70','c67eaa68e571efd68d4f7782c27435e6','2022-05-20 22:09:55',NULL,NULL,NULL,NULL,NULL,1),('9333aa7d68f2574a67e604ada2ad8b3e','ac7f242215adde9b09d9ee6e061eed70','96c31399d4986547146dc332c7f04a0b','2022-05-30 10:14:17','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('958a5fcf396dc06b55f76745c08344d0','ac7f242215adde9b09d9ee6e061eed70','a21fc9aeeb00af9cc14241c077230b81','2022-05-20 22:09:48',NULL,NULL,NULL,NULL,NULL,1),('a493c0c7392cdb27a6cc10dcbdd25401','ac7f242215adde9b09d9ee6e061eed70','15f156e41259d476e27b9b498901558d','2022-05-29 14:37:11','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('a9161a98c5526ff947f65bede67ee375','ac7f242215adde9b09d9ee6e061eed70','2673083e5168a34e69faed0445183a6e','2022-05-28 21:14:22','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('a92dee0df8e25560a8456d18d465a8ed','ac7f242215adde9b09d9ee6e061eed70','fd93816444f72eb4a338919ce15207b6','2022-05-28 21:48:58','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('b4686eea1bff1f57ca9f7679ae62c6c0','ac7f242215adde9b09d9ee6e061eed70','714c94393e07f20436b490c701e33f28','2022-05-27 21:53:32','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('b76b2ad8bbf0589b2d32059d2b37773a','ac7f242215adde9b09d9ee6e061eed70','0d50de05c87b1bc27104afa76db8348b','2022-05-28 21:44:49','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('ba39054a24d7dba3e7c46e9e288ec07a','ac7f242215adde9b09d9ee6e061eed70','f803024fa6043fb52571b7226ffcd3cc','2022-05-28 21:42:10','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('ca34674662c4199f8f0f789b47ed9ffe','ac7f242215adde9b09d9ee6e061eed70','6a5fab974254531138f81eb6ec2df189','2022-05-29 14:37:16','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('cc6d593d9a280438613394932bf9ef72','ac7f242215adde9b09d9ee6e061eed70','103d6bc1a345f6ac714910de8e92d37f','2022-05-27 23:48:34','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('e59227f743308edf4b2ded37ba11e3db','ac7f242215adde9b09d9ee6e061eed70','9f039db1ba134722855cff3c3be609d9','2022-05-20 22:09:51',NULL,NULL,NULL,NULL,NULL,1),('eaaee9c6e0281015bcd41ce6a88555a9','ac7f242215adde9b09d9ee6e061eed70','7fc21e1d812a6c8274c0e09772e73180','2022-05-27 23:48:21','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('f1a332dc89a9e8a3ea23d2e914d9e1b6','ac7f242215adde9b09d9ee6e061eed70','c5dac60a4b496e8483cdb8af309392d0','2022-05-28 21:48:14','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('f2140c21bafd7be4d005140afc932204','ac7f242215adde9b09d9ee6e061eed70','75e9110cf443b440d0cd8eff356eaf7d','2022-05-29 14:52:13','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1),('f495468747a321871d25fd88e2144f15','ac7f242215adde9b09d9ee6e061eed70','2ea7eea8849faede0edd88c967c123e1','2022-05-28 00:15:01','24a7c90eb8dfce0646f86effd3bba551','管理员',NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `user_role_res_relation` ENABLE KEYS */;
UNLOCK TABLES;

