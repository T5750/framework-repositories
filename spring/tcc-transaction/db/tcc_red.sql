/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : tcc_red

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2019-01-15 10:56:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `red_red_packet_account`
-- ----------------------------
DROP TABLE IF EXISTS `red_red_packet_account`;
CREATE TABLE `red_red_packet_account` (
  `RED_PACKET_ACCOUNT_ID` int(11) NOT NULL,
  `BALANCE_AMOUNT` decimal(10,0) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RED_PACKET_ACCOUNT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of red_red_packet_account
-- ----------------------------
INSERT INTO `red_red_packet_account` VALUES ('1', '950', '1000');
INSERT INTO `red_red_packet_account` VALUES ('2', '500', '2000');

-- ----------------------------
-- Table structure for `red_trade_order`
-- ----------------------------
DROP TABLE IF EXISTS `red_trade_order`;
CREATE TABLE `red_trade_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SELF_USER_ID` bigint(11) DEFAULT NULL,
  `OPPOSITE_USER_ID` bigint(11) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) DEFAULT NULL,
  `AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of red_trade_order
-- ----------------------------
