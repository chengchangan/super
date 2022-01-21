/*
 Navicat Premium Data Transfer

 Source Server         : 101.34.35.72
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : 101.34.35.72:3306
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 21/01/2022 09:36:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'common.yaml', 'DEFAULT_GROUP', '# 开启feign支持\nfeign:\n  sentinel:\n    enabled: true\n\n# spring相关配置\nspring:\n# redis相关配置\n  redis:\n    host: 101.34.35.72\n    password: a1234\n  cloud:\n    nacos:\n      config:\n        extension-configs:\n            refresh: true\n      # 注册与发现配置\n      discovery:\n        server-addr: 101.34.35.72:8848\n        namespace: 71f02626-4b72-4793-aab0-291c4cfed63b\n\n\n# 限流相关\n    sentinel:\n      transport:\n        dashboard: localhost:8080\n        port: 8719  # 该端口是 sentinel 与 sentinel-dashboard交互的端口，默认8719，假如被占用了会自动从8719开始依次+1扫描。\n      datasource: \n        flow: \n          nacos: \n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            dataId: ${spring.application.name}-flow-rules\n            groupId: SENTINEL_GROUP\n            # 规则类型，取值见：\n            # org.springframework.cloud.alibaba.sentinel.datasource.RuleType\n            rule-type: flow\n            data-type: json\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: degrade\n            data-type: json\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: system\n            data-type: json\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: authority\n            data-type: json\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: param-flow\n            data-type: json\n\n\n\n# 暴露监控插件端口\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'', '6f6a4183a99ddc82ef56a71993422329', '2021-12-23 06:49:52', '2021-12-23 06:51:15', NULL, '113.87.49.13', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (4, 'common.yaml', 'DEFAULT_GROUP', '# 开启feign支持\nfeign:\n  sentinel:\n    enabled: true\n\n# spring相关配置\nspring:\n# redis相关配置\n  redis:\n    host: 101.34.35.72\n    password: a1234\n  cloud:\n    nacos:\n      config:\n        extension-configs:\n            refresh: true\n      # 注册与发现配置\n      discovery:\n        server-addr: 101.34.35.72:8848\n        namespace: 71f02626-4b72-4793-aab0-291c4cfed63b\n\n\n# 限流相关\n    sentinel:\n      transport:\n        dashboard: localhost:8080\n        port: 8719  # 该端口是 sentinel 与 sentinel-dashboard交互的端口，默认8719，假如被占用了会自动从8719开始依次+1扫描。\n      datasource: \n        flow: \n          nacos: \n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            namespace: ${spring.cloud.nacos.config.namespace}\n            dataId: ${spring.application.name}-flow-rules\n            groupId: SENTINEL_GROUP\n            # 规则类型，取值见：\n            # org.springframework.cloud.alibaba.sentinel.datasource.RuleType\n            rule-type: flow\n            data-type: json\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: degrade\n            data-type: json\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: system\n            data-type: json\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: authority\n            data-type: json\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.config.server-addr}\n            dataId: ${spring.cloud.nacos.config.namespace}\n            groupId: SENTINEL_GROUP\n            rule-type: param-flow\n            data-type: json\n\n\n\n# 暴露监控插件端口\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n\n# 日志收集相关配置\nlog:\n  manager:\n    transferChannel: mysql\n    transferDataSource:\n      username: root\n      password: P!UgKAa5\n      url: jdbc:mysql://101.34.35.72:3306/log_center?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false\n      driver-class-name: com.mysql.cj.jdbc.Driver\n    transferStrategy:\n      always: false\n      onceOfSecond: 5\n      batchMaxSize: 999', '77bdaedaf184f021fd9652432536c98a', '2021-12-23 06:55:52', '2022-01-20 11:54:18', NULL, '113.87.50.22', '', '71f02626-4b72-4793-aab0-291c4cfed63b', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (5, 'oms-example-test.yaml', 'DEFAULT_GROUP', 'server:\n  port: 1998\n\n# 定义日志级别，打印执行的sql\nlogging:\n  level:\n    root: info\n    io.boncray: info', 'c50f7ce5adab1d3e4efc2595f0cd45ac', '2021-12-23 06:56:32', '2022-01-06 08:26:41', NULL, '113.87.48.119', '', '71f02626-4b72-4793-aab0-291c4cfed63b', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (7, 'oms-product.yaml', 'PRODUCT_GROUP', 'server:\n  port: 7777\n\nspring:\n  datasource:\n    url: jdbc:mysql://101.34.35.72:3306/oms_product?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: P!UgKAa5\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\nmybatis:\n  mapper-locations: classpath:mapper/**/*.xml\n\nlogging:\n  level:\n    com.cca: debug\n    io.boncray: info\n', '33b24a65aaa2c0ac7c5b191869c88696', '2022-01-20 08:53:13', '2022-01-20 11:53:06', NULL, '113.87.50.22', '', '71f02626-4b72-4793-aab0-291c4cfed63b', '', '', '', 'yaml', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 1, 'common.yaml', 'DEFAULT_GROUP', '', '# 开启feign支持\r\nfeign:\r\n  sentinel:\r\n    enabled: true\r\n\r\n# spring相关配置\r\nspring:\r\n# redis相关配置\r\n  redis:\r\n    host: 101.34.35.72\r\n    password: a1234\r\n  cloud:\r\n    nacos:\r\n      config:\r\n        extension-configs:\r\n            refresh: true\r\n      # 注册与发现配置\r\n      discovery:\r\n        server-addr: 101.34.35.72:8848\r\n        namespace: 907b6e96-4668-4d6f-8f26-eeda563d7616\r\n\r\n\r\n# 限流相关\r\n    sentinel:\r\n      transport:\r\n        dashboard: localhost:8080\r\n        port: 8719  # 该端口是 sentinel 与 sentinel-dashboard交互的端口，默认8719，假如被占用了会自动从8719开始依次+1扫描。\r\n      datasource: \r\n        flow: \r\n          nacos: \r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            dataId: ${spring.application.name}-flow-rules\r\n            groupId: SENTINEL_GROUP\r\n            # 规则类型，取值见：\r\n            # org.springframework.cloud.alibaba.sentinel.datasource.RuleType\r\n            rule-type: flow\r\n            data-type: json\r\n        degrade:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: degrade\r\n            data-type: json\r\n        system:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: system\r\n            data-type: json\r\n        authority:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: authority\r\n            data-type: json\r\n        param-flow:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: param-flow\r\n            data-type: json\r\n\r\n\r\n\r\n# 暴露监控插件端口\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'', '1ec12fc6c0e236a9b1ac8ffa7af790fd', '2021-12-23 06:49:51', '2021-12-23 06:49:52', NULL, '113.87.49.13', 'I', '');
INSERT INTO `his_config_info` VALUES (1, 2, 'common.yaml', 'DEFAULT_GROUP', '', '# 开启feign支持\r\nfeign:\r\n  sentinel:\r\n    enabled: true\r\n\r\n# spring相关配置\r\nspring:\r\n# redis相关配置\r\n  redis:\r\n    host: 101.34.35.72\r\n    password: a1234\r\n  cloud:\r\n    nacos:\r\n      config:\r\n        extension-configs:\r\n            refresh: true\r\n      # 注册与发现配置\r\n      discovery:\r\n        server-addr: 101.34.35.72:8848\r\n        namespace: 907b6e96-4668-4d6f-8f26-eeda563d7616\r\n\r\n\r\n# 限流相关\r\n    sentinel:\r\n      transport:\r\n        dashboard: localhost:8080\r\n        port: 8719  # 该端口是 sentinel 与 sentinel-dashboard交互的端口，默认8719，假如被占用了会自动从8719开始依次+1扫描。\r\n      datasource: \r\n        flow: \r\n          nacos: \r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            dataId: ${spring.application.name}-flow-rules\r\n            groupId: SENTINEL_GROUP\r\n            # 规则类型，取值见：\r\n            # org.springframework.cloud.alibaba.sentinel.datasource.RuleType\r\n            rule-type: flow\r\n            data-type: json\r\n        degrade:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: degrade\r\n            data-type: json\r\n        system:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: system\r\n            data-type: json\r\n        authority:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: authority\r\n            data-type: json\r\n        param-flow:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: param-flow\r\n            data-type: json\r\n\r\n\r\n\r\n# 暴露监控插件端口\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'', '1ec12fc6c0e236a9b1ac8ffa7af790fd', '2021-12-23 06:51:15', '2021-12-23 06:51:15', NULL, '113.87.49.13', 'U', '');
INSERT INTO `his_config_info` VALUES (0, 3, 'oms-example-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 1998\r\n\r\n# 定义日志级别，打印执行的sql\r\nlogging:\r\n  level:\r\n    root: info\r\n    io.boncray: debug', '67f8c2cb22bf8efd78d7779904f76c01', '2021-12-23 06:51:53', '2021-12-23 06:51:54', NULL, '113.87.49.13', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 4, 'common.yaml', 'DEFAULT_GROUP', '', '# 开启feign支持\r\nfeign:\r\n  sentinel:\r\n    enabled: true\r\n\r\n# spring相关配置\r\nspring:\r\n# redis相关配置\r\n  redis:\r\n    host: 101.34.35.72\r\n    password: a1234\r\n  cloud:\r\n    nacos:\r\n      config:\r\n        extension-configs:\r\n            refresh: true\r\n      # 注册与发现配置\r\n      discovery:\r\n        server-addr: 101.34.35.72:8848\r\n        namespace: 71f02626-4b72-4793-aab0-291c4cfed63b\r\n\r\n\r\n# 限流相关\r\n    sentinel:\r\n      transport:\r\n        dashboard: localhost:8080\r\n        port: 8719  # 该端口是 sentinel 与 sentinel-dashboard交互的端口，默认8719，假如被占用了会自动从8719开始依次+1扫描。\r\n      datasource: \r\n        flow: \r\n          nacos: \r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            dataId: ${spring.application.name}-flow-rules\r\n            groupId: SENTINEL_GROUP\r\n            # 规则类型，取值见：\r\n            # org.springframework.cloud.alibaba.sentinel.datasource.RuleType\r\n            rule-type: flow\r\n            data-type: json\r\n        degrade:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: degrade\r\n            data-type: json\r\n        system:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: system\r\n            data-type: json\r\n        authority:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: authority\r\n            data-type: json\r\n        param-flow:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: param-flow\r\n            data-type: json\r\n\r\n\r\n\r\n# 暴露监控插件端口\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'', '7a50c551e26de6f1bc891dc3ab03b8f9', '2021-12-23 06:55:52', '2021-12-23 06:55:52', NULL, '113.87.49.13', 'I', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (0, 5, 'oms-example-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 1998\r\n\r\n# 定义日志级别，打印执行的sql\r\nlogging:\r\n  level:\r\n    root: info\r\n    io.boncray: debug', '67f8c2cb22bf8efd78d7779904f76c01', '2021-12-23 06:56:31', '2021-12-23 06:56:32', NULL, '113.87.49.13', 'I', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (3, 6, 'oms-example-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 1998\r\n\r\n# 定义日志级别，打印执行的sql\r\nlogging:\r\n  level:\r\n    root: info\r\n    io.boncray: debug', '67f8c2cb22bf8efd78d7779904f76c01', '2021-12-23 06:56:41', '2021-12-23 06:56:42', NULL, '113.87.49.13', 'D', '');
INSERT INTO `his_config_info` VALUES (5, 7, 'oms-example-test.yaml', 'DEFAULT_GROUP', '', 'server:\r\n  port: 1998\r\n\r\n# 定义日志级别，打印执行的sql\r\nlogging:\r\n  level:\r\n    root: info\r\n    io.boncray: debug', '67f8c2cb22bf8efd78d7779904f76c01', '2022-01-06 08:26:45', '2022-01-06 08:26:41', NULL, '113.87.48.119', 'U', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (0, 8, 'oms-product.yaml', 'PRODUCT_GROUP', '', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-01-20 08:53:13', '2022-01-20 08:53:13', NULL, '113.87.48.161', 'I', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (7, 9, 'oms-product.yaml', 'PRODUCT_GROUP', '', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2022-01-20 10:05:44', '2022-01-20 10:05:43', NULL, '113.87.50.22', 'U', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (7, 10, 'oms-product.yaml', 'PRODUCT_GROUP', '', 'server:\n  port: 7777\n\nspring:\n  datasource:\n    url: jdbc:mysql://101.34.35.72:3306/oms_product?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: P!UgKAa5\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\nmybatis:\n  mapper-locations: classpath:mapper/**/*.xml\n\nlogging:\n  level:\n    root: info\n', '13977849a43d3cbc846960fc5a3860a1', '2022-01-20 11:21:59', '2022-01-20 11:22:00', NULL, '113.87.50.22', 'U', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (7, 11, 'oms-product.yaml', 'PRODUCT_GROUP', '', 'server:\n  port: 7777\n\nspring:\n  datasource:\n    url: jdbc:mysql://101.34.35.72:3306/oms_product?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: P!UgKAa5\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\nmybatis:\n  mapper-locations: classpath:mapper/**/*.xml\n\nlogging:\n  level:\n    root: info\n', '13977849a43d3cbc846960fc5a3860a1', '2022-01-20 11:22:21', '2022-01-20 11:22:21', NULL, '113.87.50.22', 'U', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (7, 12, 'oms-product.yaml', 'PRODUCT_GROUP', '', 'server:\n  port: 7777\n\nspring:\n  datasource:\n    url: jdbc:mysql://101.34.35.72:3306/oms_product?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: P!UgKAa5\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\nmybatis:\n  mapper-locations: classpath:mapper/**/*.xml\n\nlogging:\n  level:\n    root: debug\n', 'bb08ac12ff19e6436e218c27563408fa', '2022-01-20 11:23:39', '2022-01-20 11:23:39', NULL, '113.87.50.22', 'U', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (7, 13, 'oms-product.yaml', 'PRODUCT_GROUP', '', 'server:\n  port: 7777\n\nspring:\n  datasource:\n    url: jdbc:mysql://101.34.35.72:3306/oms_product?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false\n    username: root\n    password: P!UgKAa5\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\nmybatis:\n  mapper-locations: classpath:mapper/**/*.xml\n\nlogging:\n  level:\n    com.cca: debug\n    io.boncray: debug\n', 'b033a55995b59f6835de47c34f8f21da', '2022-01-20 11:53:05', '2022-01-20 11:53:06', NULL, '113.87.50.22', 'U', '71f02626-4b72-4793-aab0-291c4cfed63b');
INSERT INTO `his_config_info` VALUES (4, 14, 'common.yaml', 'DEFAULT_GROUP', '', '# 开启feign支持\r\nfeign:\r\n  sentinel:\r\n    enabled: true\r\n\r\n# spring相关配置\r\nspring:\r\n# redis相关配置\r\n  redis:\r\n    host: 101.34.35.72\r\n    password: a1234\r\n  cloud:\r\n    nacos:\r\n      config:\r\n        extension-configs:\r\n            refresh: true\r\n      # 注册与发现配置\r\n      discovery:\r\n        server-addr: 101.34.35.72:8848\r\n        namespace: 71f02626-4b72-4793-aab0-291c4cfed63b\r\n\r\n\r\n# 限流相关\r\n    sentinel:\r\n      transport:\r\n        dashboard: localhost:8080\r\n        port: 8719  # 该端口是 sentinel 与 sentinel-dashboard交互的端口，默认8719，假如被占用了会自动从8719开始依次+1扫描。\r\n      datasource: \r\n        flow: \r\n          nacos: \r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            namespace: ${spring.cloud.nacos.config.namespace}\r\n            dataId: ${spring.application.name}-flow-rules\r\n            groupId: SENTINEL_GROUP\r\n            # 规则类型，取值见：\r\n            # org.springframework.cloud.alibaba.sentinel.datasource.RuleType\r\n            rule-type: flow\r\n            data-type: json\r\n        degrade:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: degrade\r\n            data-type: json\r\n        system:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: system\r\n            data-type: json\r\n        authority:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: authority\r\n            data-type: json\r\n        param-flow:\r\n          nacos:\r\n            server-addr: ${spring.cloud.nacos.config.server-addr}\r\n            dataId: ${spring.cloud.nacos.config.namespace}\r\n            groupId: SENTINEL_GROUP\r\n            rule-type: param-flow\r\n            data-type: json\r\n\r\n\r\n\r\n# 暴露监控插件端口\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'', '7a50c551e26de6f1bc891dc3ab03b8f9', '2022-01-20 11:54:18', '2022-01-20 11:54:18', NULL, '113.87.50.22', 'U', '71f02626-4b72-4793-aab0-291c4cfed63b');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', '71f02626-4b72-4793-aab0-291c4cfed63b', 'dev', '开发环境', 'nacos', 1640242060525, 1640242060525);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
