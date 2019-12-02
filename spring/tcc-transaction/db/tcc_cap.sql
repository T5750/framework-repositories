/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : tcc_cap

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2019-01-15 10:54:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cap_capital_account`
-- ----------------------------
DROP TABLE IF EXISTS `cap_capital_account`;
CREATE TABLE `cap_capital_account` (
  `CAPITAL_ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BALANCE_AMOUNT` decimal(10,0) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CAPITAL_ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cap_capital_account
-- ----------------------------
INSERT INTO `cap_capital_account` VALUES ('1', '10000', '1000');
INSERT INTO `cap_capital_account` VALUES ('2', '10000', '2000');

-- ----------------------------
-- Table structure for `cap_trade_order`
-- ----------------------------
DROP TABLE IF EXISTS `cap_trade_order`;
CREATE TABLE `cap_trade_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SELF_USER_ID` bigint(11) DEFAULT NULL,
  `OPPOSITE_USER_ID` bigint(11) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) DEFAULT NULL,
  `AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cap_trade_order
-- ----------------------------
