/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : lly_equipment

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 12/02/2023 13:25:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for msg_equipment
-- ----------------------------
DROP TABLE IF EXISTS `msg_equipment`;
CREATE TABLE `msg_equipment`  (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '设备名称',
  `equipment_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备描述',
  `create_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '设备信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of msg_equipment
-- ----------------------------
INSERT INTO `msg_equipment` VALUES ('1598571596547272705', '打印机', '很不错的打印机', '2022-12-02 14:55:43', 0);
INSERT INTO `msg_equipment` VALUES ('1598571644693688322', '显示器', '还不错的显示器', '2022-12-02 14:55:54', 0);
INSERT INTO `msg_equipment` VALUES ('1598572230545674242', '主机', '很不错的主机', '2022-12-02 14:58:14', 0);

-- ----------------------------
-- Table structure for rep_record
-- ----------------------------
DROP TABLE IF EXISTS `rep_record`;
CREATE TABLE `rep_record`  (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `equipment_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备id(关联msg_equipment)',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修地址',
  `record_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修描述',
  `level` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申报级别  0: 普通维修  1: 紧急维修',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修状态  0: 未处理  1: 已处理',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请时间',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请联系人电话',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  `student_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请学生信息id(管理sys_student表)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '维修记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rep_record
-- ----------------------------
INSERT INTO `rep_record` VALUES ('1598578225858674690', '1598571596547272705', '山大路', '打印机坏了', '1', '1', '2022-12-02 15:22:03', '17863648895', '袁凯', '1598577582620213250');

-- ----------------------------
-- Table structure for sys_equipment_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_equipment_admin`;
CREATE TABLE `sys_equipment_admin`  (
  `admin_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修管理员id',
  `equipment_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备id'
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '维修管理员关联设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_equipment_admin
-- ----------------------------
INSERT INTO `sys_equipment_admin` VALUES ('1001', '1598571596547272705');
INSERT INTO `sys_equipment_admin` VALUES ('1001', '1598571644693688322');
INSERT INTO `sys_equipment_admin` VALUES ('1001', '1598572230545674242');

-- ----------------------------
-- Table structure for sys_repair_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_repair_admin`;
CREATE TABLE `sys_repair_admin`  (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修管理员姓名',
  `username` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修管理员联系电话',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `status` varchar(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '账号状态 1正常 0,禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '维修管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_repair_admin
-- ----------------------------
INSERT INTO `sys_repair_admin` VALUES ('1001', '袁凯', 'admin', '666', '17863648895', '2022-12-02', '1');

-- ----------------------------
-- Table structure for sys_student
-- ----------------------------
DROP TABLE IF EXISTS `sys_student`;
CREATE TABLE `sys_student`  (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `username` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `secondary_schools` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级学院名称',
  `grade` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年级',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批状态(0未审批  1已审批)',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_student
-- ----------------------------
INSERT INTO `sys_student` VALUES ('1598577582620213250', '袁凯', 'yuankai', '123456', '17863648895', '计算机学院', '2019级', '1', 0, '2022-12-02 15:19:30');

SET FOREIGN_KEY_CHECKS = 1;
