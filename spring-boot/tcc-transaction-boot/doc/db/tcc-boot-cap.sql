/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50638
 Source Host           : localhost:3306
 Source Schema         : tcc-boot-cap

 Target Server Type    : MySQL
 Target Server Version : 50638
 File Encoding         : 65001

 Date: 25/01/2019 08:48:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cap_capital_account
-- ----------------------------
DROP TABLE IF EXISTS `cap_capital_account`;
CREATE TABLE `cap_capital_account`  (
  `CAPITAL_ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BALANCE_AMOUNT` decimal(10, 0) NULL DEFAULT NULL,
  `USER_ID` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`CAPITAL_ACCOUNT_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cap_capital_account
-- ----------------------------
INSERT INTO `cap_capital_account` VALUES (1, 10000, 1000);
INSERT INTO `cap_capital_account` VALUES (2, 10000, 2000);

-- ----------------------------
-- Table structure for cap_trade_order
-- ----------------------------
DROP TABLE IF EXISTS `cap_trade_order`;
CREATE TABLE `cap_trade_order`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SELF_USER_ID` bigint(11) NULL DEFAULT NULL,
  `OPPOSITE_USER_ID` bigint(11) NULL DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `AMOUNT` decimal(10, 0) NULL DEFAULT NULL,
  `STATUS` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VERSION` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `UX_MERCHANT_ORDER_NO`(`MERCHANT_ORDER_NO`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
