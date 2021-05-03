/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50638
 Source Host           : localhost:3306
 Source Schema         : nginx-lua-web

 Target Server Type    : MySQL
 Target Server Version : 50638
 File Encoding         : 65001

 Date: 19/09/2019 10:01:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ad_0
-- ----------------------------
DROP TABLE IF EXISTS `ad_0`;
CREATE TABLE `ad_0`  (
  `sku_id` bigint(20) NULL DEFAULT NULL,
  `content` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ad_1
-- ----------------------------
DROP TABLE IF EXISTS `ad_1`;
CREATE TABLE `ad_1`  (
  `sku_id` bigint(20) NULL DEFAULT NULL,
  `content` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
