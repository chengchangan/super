/*
 Navicat Premium Data Transfer

 Source Server         : 101.34.35.72
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : 101.34.35.72:3306
 Source Schema         : log_center

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 20/01/2022 20:06:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

create database `log_center` default character set utf8mb4 collate utf8mb4_general_ci;

-- ----------------------------
-- Table structure for local_log
-- ----------------------------
DROP TABLE IF EXISTS `local_log`;
CREATE TABLE `local_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志类型',
  `parent_track_id` bigint(10) NOT NULL COMMENT '父级　TrackId',
  `current_track_id` bigint(10) NOT NULL COMMENT '当前 TrackId',
  `service_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志所属服务',
  `logger_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志名称（所属类）',
  `level` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志级别',
  `message` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志信息',
  `log_time` datetime(6) NOT NULL COMMENT '日志产生时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rpc_log
-- ----------------------------
DROP TABLE IF EXISTS `rpc_log`;
CREATE TABLE `rpc_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_track_id` bigint(10) NOT NULL COMMENT '父级　TrackId',
  `current_track_id` bigint(10) NOT NULL COMMENT '当前 TrackId',
  `service_name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志所属服务',
  `level` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志级别',
  `method` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `request_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求的路径',
  `request_param` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求参数（header和body）',
  `response_data` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应结果',
  `elapsed_time` int(6) NULL DEFAULT NULL COMMENT '耗时（毫秒）',
  `log_time` datetime(0) NOT NULL COMMENT '日志产生时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
