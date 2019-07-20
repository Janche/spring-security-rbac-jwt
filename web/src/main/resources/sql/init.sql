/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : spring-security-rbac-jwt

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 20/07/2019 16:12:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu_right
-- ----------------------------
DROP TABLE IF EXISTS `menu_right`;
CREATE TABLE `menu_right`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父节点id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `method` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法（get、post等）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标样式',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态：0-禁用，1-启用',
  `grades` int(11) NULL DEFAULT NULL COMMENT '层级',
  `seq` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu_right
-- ----------------------------
INSERT INTO `menu_right` VALUES (1, 1, '首页', NULL, '', NULL, 1, 1, NULL, '2019-07-20 15:58:07', NULL);
INSERT INTO `menu_right` VALUES (2, 1, '系统管理', NULL, NULL, NULL, 1, 1, NULL, '2019-07-20 15:58:48', NULL);
INSERT INTO `menu_right` VALUES (3, 2, '用户管理', NULL, NULL, NULL, 1, 2, NULL, '2019-07-20 15:59:09', NULL);
INSERT INTO `menu_right` VALUES (4, 2, '角色管理', NULL, NULL, NULL, 1, 2, NULL, '2019-07-20 15:59:21', NULL);
INSERT INTO `menu_right` VALUES (10, 3, '新增用户', 'POST', '/user', NULL, 1, 3, NULL, '2019-07-20 15:59:54', NULL);
INSERT INTO `menu_right` VALUES (11, 3, '删除用户', 'DELETE', '/user', NULL, 1, 3, NULL, '2019-07-20 16:00:15', NULL);
INSERT INTO `menu_right` VALUES (12, 3, '查询用户', 'GET', '/user', NULL, 1, 3, NULL, '2019-07-20 16:01:23', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父节点id',
  `role_index` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有父级节点和当前节点',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称描述',
  `seq` int(11) NULL DEFAULT NULL COMMENT '角色排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 1, '1', '超级管理员', NULL, NULL, '2019-07-20 16:11:30', NULL);
INSERT INTO `role` VALUES (2, 1, '1,2', '普通用户', NULL, NULL, '2019-07-20 16:11:33', NULL);

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
DROP TABLE IF EXISTS `role_right`;
CREATE TABLE `role_right`  (
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `menu_id` bigint(11) NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_right
-- ----------------------------
INSERT INTO `role_right` VALUES (2, 1);
INSERT INTO `role_right` VALUES (2, 2);
INSERT INTO `role_right` VALUES (2, 3);
INSERT INTO `role_right` VALUES (2, 4);
INSERT INTO `role_right` VALUES (2, 10);
INSERT INTO `role_right` VALUES (2, 11);
INSERT INTO `role_right` VALUES (2, 12);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `log_type` int(11) NULL DEFAULT NULL COMMENT '日志类型  根据系统模块来定义日志类型，采用一级菜单的ID，0为默认值',
  `operation` int(11) NULL DEFAULT NULL COMMENT '操作类型： 添加-1 删除-2 更新-3 查看-4 登录-5 登出-6 导入-7 导出-8 0为默认值',
  `log_user` bigint(11) NULL DEFAULT NULL COMMENT '操作人员ID',
  `log_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问IP',
  `log_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `log_params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `log_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志描述',
  `log_time` bigint(32) NULL DEFAULT NULL COMMENT '响应时间',
  `exception_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常码',
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1249 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(126) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `actual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别: 0-男，1-女',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `post_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职称',
  `status` int(2) NULL DEFAULT 1 COMMENT '状态: 0-禁用，1-启用',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$3LZ0PbXV2wLV00FwW73mZeq5OUvhAN.g3/0eWLQKn3MZja9RbkUzW', '张三', 0, 'abc@qq.com', '13696003468', '成都市', '部门经理', 1, '2019-07-20 16:03:57', NULL);
INSERT INTO `user` VALUES (2, 'test', '$2a$10$64iuSLkKNhpTN19jGHs7xePvFsub7ZCcCmBqEYw8fbACGTE3XetYq', '小美', 1, '234@163.com', '19263231589', '达州市', '普通员工', 1, '2019-07-20 16:04:37', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `user_id` bigint(11) NOT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
