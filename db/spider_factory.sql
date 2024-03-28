/*
 Navicat Premium Data Transfer

 Source Server         : yys62_s1
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : s1.stonedt.com:6201
 Source Schema         : bupt_test

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 06/03/2024 18:49:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('quartzScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `site` varchar(1255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点',
  `status` int(5) NULL DEFAULT 0 COMMENT '状态: 采集中',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `is_del` int(5) NULL DEFAULT 0 COMMENT '是否删除，默认0，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------

-- ----------------------------
-- Table structure for agent_pool
-- ----------------------------
DROP TABLE IF EXISTS `agent_pool`;
CREATE TABLE `agent_pool`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务商',
  `term` datetime(0) NULL DEFAULT NULL COMMENT '有效期',
  `url` varchar(1255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
  `status` int(255) NULL DEFAULT 0 COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `is_del` int(5) NULL DEFAULT 0 COMMENT '是否删除，默认0，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent_pool
-- ----------------------------
INSERT INTO `agent_pool` VALUES (1, '思通数科', '2023-08-17 17:36:14', 'http://www.ap.org/', 0, '2023-08-17 17:36:14', 1, '2023-09-05 10:39:25', 1, 0);

-- ----------------------------
-- Table structure for browser_template
-- ----------------------------
DROP TABLE IF EXISTS `browser_template`;
CREATE TABLE `browser_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `temp_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `temp_xml` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板xml内容',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of browser_template
-- ----------------------------

-- ----------------------------
-- Table structure for data_source
-- ----------------------------
DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `website_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站名称',
  `website_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站url',
  `sitedomain` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站域名',
  `website_remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '网站备注',
  `new_website_type` int(2) NULL DEFAULT NULL COMMENT '新网站类形\\\\\\\\n1微信，2微博，3政务，4论坛，5新闻，6报刊，7客户端，8网站，9外媒，10视频，11博客，12自媒体，13招投标',
  `website_ico` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站logo',
  `mainstream_flag` int(1) NOT NULL DEFAULT 2 COMMENT '是否是主流网站\\\\\\\\n1是，2不是',
  `icp_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'icp备案信息',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核通过时间',
  `sponsor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '主办单位名称',
  `sponsor_nature` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主办单位性质',
  `website_pr` int(11) NULL DEFAULT NULL COMMENT '站点pr值',
  `one_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一级域名',
  `two_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二级域名',
  `website_explain` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '站点说明',
  `website_status` int(11) NULL DEFAULT 1 COMMENT '网站状态',
  `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `url_contains_str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '智能抓取',
  `url_shield_str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '智能抓取',
  `explain_contains_str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '智能抓取',
  `explain_shield_str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '智能抓取',
  `capture_date` datetime(0) NULL DEFAULT NULL COMMENT '智能抓取',
  `capture_type` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '智能抓取',
  `website_city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站所在市',
  `website_province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站所在省',
  `websiteprimaryid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '老数据字段',
  `matchingstatus` int(1) NULL DEFAULT 0 COMMENT '老数据字段',
  `website_type` int(11) NULL DEFAULT NULL COMMENT '原网站类型\\n1新闻，2财经媒体，3重要市场，4公告，5地区板块分类，6热点概念，7行业分类，8政府机构，9央行动态，10外汇市场，11期货市场，12固定收益市场，13基金理财，14创投并购',
  `site_rank` int(11) NULL DEFAULT NULL COMMENT '自定义的排名',
  `websitetype` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面上的网站类型',
  `area` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面上的网站地区',
  `baidu_weight` int(11) NULL DEFAULT NULL COMMENT '百度权重',
  `thas_weight` int(11) NULL DEFAULT NULL COMMENT '360权重',
  `alexa_rank` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Alexa排名',
  `manually_review` int(1) NULL DEFAULT 0 COMMENT '人工审核（1人工审核，0自动获取）',
  `intelligent_match` int(1) NULL DEFAULT 0 COMMENT '智能匹配（0不开启，1开启，2完成,  3执行中，4失败，31第一轮执行中,32第一轮执行失败33,第一轮执行完成,34第二轮执行中,35第二轮执行失败）',
  `rounds` int(1) NULL DEFAULT 0 COMMENT '处理轮次',
  `message_status` int(11) NULL DEFAULT 1 COMMENT '1 不需要补全   2 需要补全',
  `status` int(11) NULL DEFAULT 0 COMMENT '(0：数据未更新，1：已更新)',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新人id',
  `is_del` int(11) NULL DEFAULT 0 COMMENT '是否删除,默认0正常，1已删除',
  `log_error` int(5) NULL DEFAULT 0 COMMENT '预警: 默认0正常，1预警',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `two_domain`(`two_domain`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1042 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_source
-- ----------------------------
INSERT INTO `data_source` VALUES (1005, '新闻资讯', 'http://channel.chinanews.com/cns/cl/stock-scyw.shtml', 'chinanews.com', '', 4, '', 2, NULL, NULL, NULL, NULL, NULL, 'chinanews.com', 'channel.chinanews.com', NULL, 1, 157, '2023-09-04 19:36:18', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 0, 1, 0, 'wangyi', '2023-12-06 15:55:51', 'admin', 1, 0, 1);

-- ----------------------------
-- Table structure for es_record
-- ----------------------------
DROP TABLE IF EXISTS `es_record`;
CREATE TABLE `es_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `website_id` int(11) NULL DEFAULT NULL COMMENT '网站id',
  `seed_id` int(11) NULL DEFAULT NULL COMMENT '种子id',
  `es_index` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据类型',
  `spider_time` datetime(0) NULL DEFAULT NULL COMMENT '采集时间',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `type` int(5) NULL DEFAULT NULL COMMENT '采集类型',
  `article_public_id` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'esid',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `article_public_id`(`article_public_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of es_record
-- ----------------------------

-- ----------------------------
-- Table structure for landing_user_log
-- ----------------------------
DROP TABLE IF EXISTS `landing_user_log`;
CREATE TABLE `landing_user_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `landing_date` datetime(0) NULL DEFAULT NULL,
  `landing_count` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4960 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of landing_user_log
-- ----------------------------
INSERT INTO `landing_user_log` VALUES (4956, 'admin', 'admin', 1, '2024-03-06 18:40:55', '2024-03-06 18:45:43', 4);

-- ----------------------------
-- Table structure for minio_file_data
-- ----------------------------
DROP TABLE IF EXISTS `minio_file_data`;
CREATE TABLE `minio_file_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `size` double(11, 4) NULL DEFAULT NULL COMMENT '大小',
  `file_time` datetime(0) NULL DEFAULT NULL COMMENT '文件采集时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '数据创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `file_name`(`file_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4717 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of minio_file_data
-- ----------------------------

-- ----------------------------
-- Table structure for result_note
-- ----------------------------
DROP TABLE IF EXISTS `result_note`;
CREATE TABLE `result_note`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `english_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文字段',
  `china_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `english_key`(`english_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of result_note
-- ----------------------------

-- ----------------------------
-- Table structure for spider_flow
-- ----------------------------
DROP TABLE IF EXISTS `spider_flow`;
CREATE TABLE `spider_flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seed_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名',
  `seed_type` int(1) NULL DEFAULT NULL COMMENT '任务类型，1代表html 2json 3代表html>cdata',
  `seed_status` int(1) NULL DEFAULT NULL COMMENT '任务状态1正常0关闭',
  `cron` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `website_id` int(11) NULL DEFAULT NULL COMMENT '任务对应网站ID',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `xml` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'xml表达式',
  `data_type_id` int(11) NULL DEFAULT NULL COMMENT '数据类型id',
  `spider_level` int(2) NULL DEFAULT 1,
  `sole_sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '唯一标识, 值最好是 该爬虫访问的列表的url',
  `from_smart_crawler` int(1) NULL DEFAULT 0 COMMENT '是否是智能配置（0不是 1是）',
  `update_time` datetime(0) NULL DEFAULT NULL,
  `error_num` int(11) NULL DEFAULT 0,
  `customlable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户标签',
  `key_sources_flag` int(11) NULL DEFAULT 0 COMMENT '重点信源标记 [默认为0非重点,1重点]',
  `spider_type` int(11) NULL DEFAULT 2 COMMENT '采集类型[搜索引擎1,栏目抓取2,整站抓取3,站内搜索4]',
  `type` int(11) NULL DEFAULT 0 COMMENT '类型: html , file',
  `temp_id` int(11) NULL DEFAULT NULL COMMENT '模板id',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_user_id` int(11) NULL DEFAULT NULL,
  `is_del` int(5) NULL DEFAULT 0 COMMENT '是否删除，默认0，1删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sole_sign`(`sole_sign`) USING BTREE,
  INDEX `website_id`(`website_id`) USING BTREE,
  INDEX `data_type_id`(`data_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 135286087 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spider_flow
-- ----------------------------
INSERT INTO `spider_flow` VALUES (135286058, '中国新闻网', 1, 1, '0 0/3 * * * ? *', 1005, '2023-09-04 19:37:43', '<mxGraphModel>\n  <root>\n    <mxCell id=\"0\">\n      <JsonProperty as=\"data\">\n        {&quot;spiderName&quot;:&quot;未定义名称&quot;,&quot;submit-strategy&quot;:&quot;random&quot;,&quot;threadCount&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"1\" parent=\"0\"/>\n    <mxCell id=\"2\" value=\"开始\" style=\"start\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"80\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;shape&quot;:&quot;start&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"5\" value=\"列表页\" style=\"request\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"290\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;列表页&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;method&quot;:&quot;GET&quot;,&quot;sleep&quot;:&quot;200&quot;,&quot;timeout&quot;:&quot;10000&quot;,&quot;response-charset&quot;:&quot;&quot;,&quot;retryCount&quot;:&quot;1&quot;,&quot;retryInterval&quot;:&quot;200&quot;,&quot;body-type&quot;:&quot;none&quot;,&quot;body-content-type&quot;:&quot;text/plain&quot;,&quot;loopCount&quot;:&quot;&quot;,&quot;url&quot;:&quot;${\\&quot;http://channel.chinanews.com/cns/cl/stock-scyw.shtml?pager=\\&quot;+page}&quot;,&quot;proxy&quot;:&quot;&quot;,&quot;request-body&quot;:&quot;&quot;,&quot;follow-redirect&quot;:&quot;1&quot;,&quot;tls-validate&quot;:&quot;0&quot;,&quot;cookie-auto-set&quot;:&quot;1&quot;,&quot;repeat-enable&quot;:&quot;0&quot;,&quot;shape&quot;:&quot;request&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"6\" value=\"获取详情页url列表\" style=\"variable\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"410\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;获取详情页url列表&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;ziloopVariableName&quot;:&quot;&quot;,&quot;variable-name&quot;:[&quot;orgurl&quot;,&quot;listhtml&quot;,&quot;dataStr&quot;,&quot;dataStr&quot;,&quot;dataJson&quot;,&quot;datalist&quot;],&quot;variable-description&quot;:[&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;],&quot;loopCount&quot;:&quot;&quot;,&quot;ziloopCount&quot;:&quot;&quot;,&quot;variable-value&quot;:[&quot;${resp.url}&quot;,&quot;${resp.html}&quot;,&quot;${listhtml.substring(listhtml.indexOf(&#39;var docArr=&#39;)+11 , listhtml.indexOf(&#39;var pagebean=&#39;)-11)}&quot;,&quot;${\\&quot;{\\\\\\&quot;data\\\\\\&quot;:\\&quot;+dataStr+\\&quot;}\\&quot;}&quot;,&quot;${jsonUtils.parseObject(dataStr)}&quot;,&quot;${dataJson.data}&quot;],&quot;shape&quot;:&quot;variable&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"7\" value=\"循环列表页\" style=\"loop\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"510\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;循环列表页&quot;,&quot;loopItem&quot;:&quot;&quot;,&quot;loopVariableName&quot;:&quot;i&quot;,&quot;loopCount&quot;:&quot;${datalist.size()&gt; 50 ? 50 : datalist.size()}&quot;,&quot;loopStart&quot;:&quot;0&quot;,&quot;loopEnd&quot;:&quot;-1&quot;,&quot;awaitSleep&quot;:&quot;0&quot;,&quot;shape&quot;:&quot;loop&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"8\" value=\"定义变量\" style=\"variable\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"620\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;定义变量&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;ziloopVariableName&quot;:&quot;&quot;,&quot;variable-name&quot;:[&quot;li&quot;,&quot;detail_url&quot;,&quot;detail_url&quot;],&quot;variable-description&quot;:[&quot;&quot;,&quot;&quot;,&quot;&quot;],&quot;loopCount&quot;:&quot;&quot;,&quot;ziloopCount&quot;:&quot;&quot;,&quot;variable-value&quot;:[&quot;${datalist.get(i)}&quot;,&quot;${li.url}&quot;,&quot;${urlUtils.getabsurl(detail_url,orgurl)}&quot;],&quot;shape&quot;:&quot;variable&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"9\" value=\"访问详情页\" style=\"request\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"730\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;访问详情页&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;method&quot;:&quot;GET&quot;,&quot;sleep&quot;:&quot;200&quot;,&quot;timeout&quot;:&quot;10000&quot;,&quot;response-charset&quot;:&quot;&quot;,&quot;retryCount&quot;:&quot;1&quot;,&quot;retryInterval&quot;:&quot;200&quot;,&quot;body-type&quot;:&quot;none&quot;,&quot;body-content-type&quot;:&quot;text/plain&quot;,&quot;loopCount&quot;:&quot;&quot;,&quot;url&quot;:&quot;${detail_url}&quot;,&quot;proxy&quot;:&quot;&quot;,&quot;request-body&quot;:&quot;&quot;,&quot;follow-redirect&quot;:&quot;1&quot;,&quot;tls-validate&quot;:&quot;0&quot;,&quot;cookie-auto-set&quot;:&quot;1&quot;,&quot;repeat-enable&quot;:&quot;0&quot;,&quot;shape&quot;:&quot;request&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"10\" value=\"解析详情页\" style=\"variable\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"860\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;解析详情页&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;ziloopVariableName&quot;:&quot;&quot;,&quot;variable-name&quot;:[&quot;news&quot;,&quot;title&quot;,&quot;publish_time&quot;,&quot;contenthtml&quot;,&quot;content&quot;,&quot;auth&quot;,&quot;author&quot;,&quot;author_url&quot;,&quot;author_avatar&quot;,&quot;summary&quot;],&quot;variable-description&quot;:[&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;,&quot;&quot;],&quot;loopCount&quot;:&quot;&quot;,&quot;ziloopCount&quot;:&quot;&quot;,&quot;variable-value&quot;:[&quot;${extractUtils.getNewsByHtml(resp.html)}&quot;,&quot;${news.getTitle()}&quot;,&quot;${news.getTime()}&quot;,&quot;${news.getContentElement().html()}&quot;,&quot;${news.getContent()}&quot;,&quot;${news.getAuthor()}&quot;,&quot;${auth.getName()}&quot;,&quot;${auth.getUrl()}&quot;,&quot;${auth.getAvatar()}&quot;,&quot;${ (content!=null &amp;&amp; content.length()&gt;100 ) ? content.substring(0,100) : content }&quot;],&quot;shape&quot;:&quot;variable&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"11\" value=\"结果发送\" style=\"redisSend\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"1000\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;结果发送&quot;,&quot;redis&quot;:&quot;article_public_id&quot;,&quot;redis-send&quot;:&quot;0&quot;,&quot;output-name&quot;:[&quot;title&quot;,&quot;publish_time&quot;,&quot;contenthtml&quot;,&quot;content&quot;,&quot;source_url&quot;,&quot;author&quot;,&quot;author_url&quot;,&quot;author_avatar&quot;,&quot;classify&quot;,&quot;similarvolume&quot;,&quot;heatvolume&quot;,&quot;spider_time&quot;,&quot;publish_date&quot;,&quot;publishdate&quot;,&quot;article_public_id&quot;,&quot;hbase_table&quot;,&quot;es_index&quot;,&quot;es_type&quot;,&quot;seed_id&quot;,&quot;otherseedid&quot;,&quot;website_id&quot;,&quot;otherwebsiteid&quot;,&quot;source_name&quot;,&quot;websitelogo&quot;,&quot;sourcewebsitename&quot;,&quot;extend_string_five&quot;,&quot;bloomName&quot;,&quot;summary&quot;],&quot;output-value&quot;:[&quot;${title}&quot;,&quot;${publish_time == null ?dateUtils.getDate() :dateUtils.FormatDate(publish_time)}&quot;,&quot;${contenthtml}&quot;,&quot;${content}&quot;,&quot;${detail_url}&quot;,&quot;${author}&quot;,&quot;${author_url}&quot;,&quot;${author_avatar}&quot;,&quot;${classify}&quot;,&quot;${similarvolume}&quot;,&quot;${heatvolume}&quot;,&quot;${dateUtils.getDate()}&quot;,&quot;${dateUtils.getDateday()}&quot;,&quot;${dateUtils.getDateday()}&quot;,&quot;${md5Utils.getMD5(detail_url)}&quot;,&quot;${hbase_table}&quot;,&quot;${es_index}&quot;,&quot;${es_type}&quot;,&quot;${seed_id}&quot;,&quot;${otherseedid}&quot;,&quot;${website_id}&quot;,&quot;${otherwebsiteid}&quot;,&quot;${source_name}&quot;,&quot;${websitelogo}&quot;,&quot;${sourcewebsitename}&quot;,&quot;${extend_string_five}&quot;,&quot;${bloomName}&quot;,&quot;${summary}&quot;],&quot;shape&quot;:&quot;redisSend&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"15\" value=\"\" style=\"strokeWidth=2;strokeColor=black;sharp=1;\" parent=\"1\" source=\"5\" target=\"6\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;black&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"16\" value=\"\" style=\"strokeWidth=2;strokeColor=blue;sharp=1;\" parent=\"1\" source=\"6\" target=\"7\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;blue&quot;,&quot;condition&quot;:&quot;${ datalist!=null &amp;&amp; datalist.size()&gt;0 }&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"17\" value=\"\" style=\"strokeWidth=2;sharp=1;\" parent=\"1\" source=\"7\" target=\"8\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;black&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"18\" value=\"\" style=\"strokeWidth=2;strokeColor=blue;sharp=1;\" parent=\"1\" source=\"8\" target=\"9\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;blue&quot;,&quot;condition&quot;:&quot;${true || redisUtils.urlifHasOnRedisByServer(md5Utils.getMD5(detail_url),bloomName)==false }&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"19\" value=\"\" style=\"strokeWidth=2;strokeColor=black;sharp=1;\" parent=\"1\" source=\"9\" target=\"10\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;black&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"20\" value=\"\" style=\"strokeWidth=2;strokeColor=#00ff00;sharp=1;\" parent=\"1\" source=\"10\" target=\"11\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;2&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;#00ff00&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"21\" value=\"分页\" style=\"variable\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"180\" y=\"80\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;分页&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;ziloopVariableName&quot;:&quot;&quot;,&quot;variable-name&quot;:[&quot;page&quot;],&quot;variable-description&quot;:[&quot;&quot;],&quot;loopCount&quot;:&quot;&quot;,&quot;ziloopCount&quot;:&quot;&quot;,&quot;variable-value&quot;:[&quot;${&#39;20&#39;.toInt()}&quot;],&quot;shape&quot;:&quot;variable&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"22\" value=\"\" style=\"strokeWidth=2;sharp=1;\" parent=\"1\" source=\"2\" target=\"21\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;black&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"23\" value=\"\" style=\"strokeWidth=2;sharp=1;\" parent=\"1\" source=\"21\" target=\"5\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;black&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"24\" value=\"定义变量\" style=\"variable\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"410\" y=\"180\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;定义变量&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;ziloopVariableName&quot;:&quot;&quot;,&quot;variable-name&quot;:[&quot;page&quot;],&quot;variable-description&quot;:[&quot;&quot;],&quot;loopCount&quot;:&quot;&quot;,&quot;ziloopCount&quot;:&quot;&quot;,&quot;variable-value&quot;:[&quot;${ page+1 }&quot;],&quot;shape&quot;:&quot;variable&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"25\" value=\"\" style=\"strokeWidth=2;strokeColor=blue;sharp=1;\" parent=\"1\" source=\"6\" target=\"24\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;blue&quot;,&quot;condition&quot;:&quot;${ page&lt;23 }&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"26\" value=\"\" style=\"strokeWidth=2;strokeColor=#00ff00;sharp=1;\" parent=\"1\" source=\"24\" target=\"5\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;2&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;#00ff00&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"27\" value=\"结果输出\" style=\"output\" parent=\"1\" vertex=\"1\">\n      <mxGeometry x=\"860\" y=\"166\" width=\"32\" height=\"32\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;结果输出&quot;,&quot;loopVariableName&quot;:&quot;&quot;,&quot;tableName&quot;:&quot;&quot;,&quot;csvName&quot;:&quot;&quot;,&quot;csvEncoding&quot;:&quot;GBK&quot;,&quot;output-name&quot;:[&quot;标题&quot;,&quot;原始链接&quot;,&quot;来源网站&quot;,&quot;发布时间&quot;,&quot;内容&quot;,&quot;html&quot;,&quot;作者&quot;,&quot;摘要&quot;],&quot;loopCount&quot;:&quot;&quot;,&quot;output-value&quot;:[&quot;${title}&quot;,&quot;${detail_url}&quot;,&quot;${source_name}&quot;,&quot;${publish_time}&quot;,&quot;${content}&quot;,&quot;${contenthtml}&quot;,&quot;${author}&quot;,&quot;${summary}&quot;],&quot;output-all&quot;:&quot;0&quot;,&quot;output-database&quot;:&quot;0&quot;,&quot;output-csv&quot;:&quot;0&quot;,&quot;shape&quot;:&quot;output&quot;}\n      </JsonProperty>\n    </mxCell>\n    <mxCell id=\"28\" value=\"\" style=\"strokeWidth=2;sharp=1;\" parent=\"1\" source=\"10\" target=\"27\" edge=\"1\">\n      <mxGeometry relative=\"1\" as=\"geometry\"/>\n      <JsonProperty as=\"data\">\n        {&quot;value&quot;:&quot;&quot;,&quot;exception-flow&quot;:&quot;0&quot;,&quot;lineWidth&quot;:&quot;2&quot;,&quot;line-style&quot;:&quot;sharp&quot;,&quot;lineColor&quot;:&quot;black&quot;,&quot;condition&quot;:&quot;&quot;,&quot;transmit-variable&quot;:&quot;1&quot;}\n      </JsonProperty>\n    </mxCell>\n  </root>\n</mxGraphModel>\n', 1, 2, 'http://channel.chinanews.com/cns/cl/stock-scyw.shtml?pager=0', 0, '2023-09-04 19:37:43', 0, NULL, 1, 2, 1, 3, 'admin', 1, 'admin', 1, 0);

-- ----------------------------
-- Table structure for spider_flow_task
-- ----------------------------
DROP TABLE IF EXISTS `spider_flow_task`;
CREATE TABLE `spider_flow_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seed_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名',
  `seed_type` int(1) NULL DEFAULT NULL COMMENT '任务类型，1代表html 2json 3代表html>cdata',
  `seed_status` int(1) NULL DEFAULT NULL COMMENT '任务状态1正常0关闭',
  `cron` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `website_id` int(11) NULL DEFAULT NULL COMMENT '任务对应网站ID',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `xml` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'xml表达式',
  `data_type_id` int(11) NULL DEFAULT NULL COMMENT '数据类型id',
  `spider_level` int(2) NULL DEFAULT 1,
  `sole_sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '唯一标识, 值最好是 该爬虫访问的列表的url',
  `from_smart_crawler` int(1) NULL DEFAULT 0 COMMENT '是否是智能配置（0不是 1是）',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `error_num` int(11) NULL DEFAULT 0,
  `customlable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户标签',
  `key_sources_flag` int(11) NULL DEFAULT 0 COMMENT '重点信源标记 [默认为0非重点,1重点]',
  `spider_type` int(11) NULL DEFAULT 2 COMMENT '采集类型[搜索引擎1,栏目抓取2,整站抓取3,站内搜索4]',
  `type` int(11) NULL DEFAULT 0 COMMENT '类型: html , file',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_user_id` int(11) NULL DEFAULT NULL,
  `is_del` int(5) NULL DEFAULT 0 COMMENT '是否删除，默认0，',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sole_sign`(`sole_sign`) USING BTREE,
  INDEX `website_id`(`website_id`) USING BTREE,
  INDEX `data_type_id`(`data_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spider_flow_task
-- ----------------------------

-- ----------------------------
-- Table structure for spider_flow_template
-- ----------------------------
DROP TABLE IF EXISTS `spider_flow_template`;
CREATE TABLE `spider_flow_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名',
  `xml` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'xml表达式',
  `environment` int(5) NULL DEFAULT 0 COMMENT '默认0测试，1正式',
  `status` int(5) NULL DEFAULT 0 COMMENT '默认0上架，1下架',
  `type_id` int(5) NULL DEFAULT NULL COMMENT '类型id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_user_id` int(11) NULL DEFAULT NULL,
  `is_del` int(5) NULL DEFAULT 0 COMMENT '是否删除，默认0，1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spider_flow_template
-- ----------------------------

-- ----------------------------
-- Table structure for spider_search_engine
-- ----------------------------
DROP TABLE IF EXISTS `spider_search_engine`;
CREATE TABLE `spider_search_engine`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点名称',
  `logo` varchar(555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'logo链接',
  `website_id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '站点ID',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `paramFlag` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '字段标记',
  `seed_id` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '种子ID',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spider_search_engine
-- ----------------------------

-- ----------------------------
-- Table structure for statistic
-- ----------------------------
DROP TABLE IF EXISTS `statistic`;
CREATE TABLE `statistic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件采集量',
  `data_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件存储量',
  `trend_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '硬盘存储量',
  `status_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '采集总量',
  `type_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '采集一天的量',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statistic
-- ----------------------------

-- ----------------------------
-- Table structure for statistic_count
-- ----------------------------
DROP TABLE IF EXISTS `statistic_count`;
CREATE TABLE `statistic_count`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `es_count` int(11) NULL DEFAULT NULL COMMENT 'es采集总量',
  `es_size` decimal(11, 4) NULL DEFAULT NULL COMMENT '存储es容量',
  `es_count_today` int(11) NULL DEFAULT NULL COMMENT 'es今天的采集量',
  `es_count_day` int(11) NULL DEFAULT NULL COMMENT 'es最近24小时采集量',
  `minio_count` int(11) NULL DEFAULT NULL COMMENT 'minio采集量',
  `minio_size` int(11) NULL DEFAULT NULL COMMENT 'minio存储量',
  `minio_disk_size` decimal(11, 4) NULL DEFAULT NULL COMMENT 'minio磁盘占用',
  `es_count_yesterday` int(11) NULL DEFAULT NULL COMMENT 'es昨天采集量',
  `es_count_week` int(11) NULL DEFAULT NULL COMMENT 'es最近一周采集量',
  `group_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'es时间分组json',
  `category_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分类占比json',
  `type_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '类型占比json',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_date` date NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2036 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statistic_count
-- ----------------------------

-- ----------------------------
-- Table structure for stonedt_user
-- ----------------------------
DROP TABLE IF EXISTS `stonedt_user`;
CREATE TABLE `stonedt_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名,默认admin',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码,默认123456',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司职务',
  `job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态：是否停用 1正常 2停用',
  `user_power` int(11) NOT NULL DEFAULT 2 COMMENT '权限：1为管理员2为普通用户',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 153 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stonedt_user
-- ----------------------------
INSERT INTO `stonedt_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 'admin', NULL, 1, 1, '2019-03-06 10:34:56', '2024-03-06 18:41:45', NULL);

-- ----------------------------
-- Table structure for vps
-- ----------------------------
DROP TABLE IF EXISTS `vps`;
CREATE TABLE `vps`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `environment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机器环境: 测试、正式',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `configure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置',
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
  `is_del` int(5) NULL DEFAULT 0 COMMENT '是否删除, 默认0，1已删除',
  `cpu_count` decimal(11, 4) NULL DEFAULT NULL,
  `ram_count` decimal(11, 4) NULL DEFAULT NULL,
  `disk_count` decimal(11, 4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vps
-- ----------------------------

-- ----------------------------
-- Table structure for website_category
-- ----------------------------
DROP TABLE IF EXISTS `website_category`;
CREATE TABLE `website_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '网站分类id',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `create_user_id` int(11) NULL DEFAULT 0 COMMENT '创建用户id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user_id` int(11) NULL DEFAULT 0 COMMENT '更新用户id',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `category_name`(`category_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of website_category
-- ----------------------------
INSERT INTO `website_category` VALUES (1, '百度百科', 1, '2023-08-16 11:50:01', 1, '2023-10-18 17:09:09');
INSERT INTO `website_category` VALUES (2, '社交媒体', 1, '2023-08-16 11:50:02', 1, '2023-10-18 17:09:12');
INSERT INTO `website_category` VALUES (3, '网络视频', 1, '2023-08-16 11:50:03', 1, '2023-10-18 17:09:15');
INSERT INTO `website_category` VALUES (4, '新闻资讯', 1, '2023-08-16 11:50:04', 1, '2023-10-18 17:09:19');
INSERT INTO `website_category` VALUES (5, '博客智库', 1, '2023-08-16 11:50:05', 1, '2023-10-18 17:09:22');
INSERT INTO `website_category` VALUES (6, '论坛贴吧', 1, '2023-08-16 11:50:06', 1, '2023-10-18 17:09:26');
INSERT INTO `website_category` VALUES (7, '搜索引擎', 1, '2023-08-16 11:50:07', 1, '2023-10-18 17:09:30');
INSERT INTO `website_category` VALUES (8, '电商网站', 1, '2023-08-16 11:50:08', 1, '2023-10-18 17:10:06');
INSERT INTO `website_category` VALUES (9, '生活方式', 1, '2023-08-16 11:50:09', 1, '2023-10-18 17:10:09');

-- ----------------------------
-- Table structure for websitetype
-- ----------------------------
DROP TABLE IF EXISTS `websitetype`;
CREATE TABLE `websitetype`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updatetime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `userid` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `estype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'es中type',
  `esindex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Esindex',
  `hbase_table` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Hbase通用接口',
  `kafka_queue_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'kfaka队列名',
  `bloomname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布隆名称',
  `is_del` int(5) NULL DEFAULT 0 COMMENT '删除,默认0，1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of websitetype
-- ----------------------------
INSERT INTO `websitetype` VALUES (1, '资讯', '2023-08-29 18:25:10', 1, 'infor', 'postal', 'stonedt_information', 'informationTopic', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (2, '天气', '2020-03-24 14:19:59', 1, 'infor', 'stonedt_weather', 'stonedt_weather', 'proStonedtData', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (4, '公告', '2020-03-25 13:37:06', 1, 'infor', 'stonedt_announcement', 'stonedt_announcement', 'proStonedtData', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (5, '研报', '2020-03-25 13:37:17', 1, 'infor', 'stonedt_researchreport', 'stonedt_researchreport', 'proStonedtData', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (6, '小红书', '2020-03-26 19:49:24', 1, 'infor', 'stonedt_littleredbook', 'stonedt_littleredbook', 'proStonedtData', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (7, '天猫', '2020-03-27 17:09:38', 1, 'infor', 'stonedt_tmall', 'stonedt_tmall', 'proStonedtData', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (8, '招投标', '2020-03-27 17:15:42', 1, 'infor', 'stonedt_biao', 'stonedt_biao', 'BiddingproStonedtData', 'BLOOM_INFOSPS', 0);
INSERT INTO `websitetype` VALUES (15, '专利', '2020-03-30 15:47:33', 1, 'infor', 'stonedt_patent', 'stonedt_patent', 'proStonedtData', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (16, '招聘', '2020-03-30 15:53:18', 1, 'infor', 'stonedt_jobs', 'stonedt_jobs', 'proStonedtData', 'BLOOM_INFOS', 0);
INSERT INTO `websitetype` VALUES (17, '论坛', '2023-08-29 20:18:17', 1, 'infor', 'stonedt_forum', 'stonedt_forum', 'proStonedtData', 'BLOOM_INFOS', 0);

SET FOREIGN_KEY_CHECKS = 1;
