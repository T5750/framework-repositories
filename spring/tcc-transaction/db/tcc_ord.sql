/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : tcc_ord

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2019-01-15 10:55:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ord_order`
-- ----------------------------
DROP TABLE IF EXISTS `ord_order`;
CREATE TABLE `ord_order` (
  `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PAYER_USER_ID` int(11) DEFAULT NULL,
  `PAYEE_USER_ID` int(11) DEFAULT NULL,
  `RED_PACKET_PAY_AMOUNT` decimal(10,0) DEFAULT NULL,
  `CAPITAL_PAY_AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ord_order
-- ----------------------------

-- ----------------------------
-- Table structure for `ord_order_line`
-- ----------------------------
DROP TABLE IF EXISTS `ord_order_line`;
CREATE TABLE `ord_order_line` (
  `ORDER_LINE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) DEFAULT NULL,
  `QUANTITY` decimal(10,0) DEFAULT NULL,
  `UNIT_PRICE` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ORDER_LINE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ord_order_line
-- ----------------------------

-- ----------------------------
-- Table structure for `ord_product`
-- ----------------------------
DROP TABLE IF EXISTS `ord_product`;
CREATE TABLE `ord_product` (
  `PRODUCT_ID` int(11) NOT NULL,
  `SHOP_ID` int(11) NOT NULL,
  `PRODUCT_NAME` varchar(64) DEFAULT NULL,
  `PRICE` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ord_product
-- ----------------------------
INSERT INTO `ord_product` VALUES ('1', '1', 'IPhone6S', '5288');
INSERT INTO `ord_product` VALUES ('2', '1', 'MAC Pro', '10288');
INSERT INTO `ord_product` VALUES ('3', '1', 'IWatch', '2288');

-- ----------------------------
-- Table structure for `ord_shop`
-- ----------------------------
DROP TABLE IF EXISTS `ord_shop`;
CREATE TABLE `ord_shop` (
  `SHOP_ID` int(11) NOT NULL,
  `OWNER_USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`SHOP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ord_shop
-- ----------------------------
INSERT INTO `ord_shop` VALUES ('1', '1000');
