/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : pay_dubbo_point

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2019-01-21 16:04:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `rp_point_account`
-- ----------------------------
DROP TABLE IF EXISTS `rp_point_account`;
CREATE TABLE `rp_point_account` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `edit_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) NOT NULL COMMENT '版本号',
  `user_no` varchar(50) NOT NULL COMMENT '用户编号',
  `balance` int(11) NOT NULL COMMENT '积分余额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_KEY_2` (`user_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分账户表';

-- ----------------------------
-- Records of rp_point_account
-- ----------------------------

-- ----------------------------
-- Table structure for `rp_point_account_history`
-- ----------------------------
DROP TABLE IF EXISTS `rp_point_account_history`;
CREATE TABLE `rp_point_account_history` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `amount` int(20) NOT NULL,
  `balance` int(20) NOT NULL,
  `fund_direction` varchar(36) NOT NULL,
  `request_no` varchar(36) NOT NULL,
  `bank_trx_no` varchar(50) DEFAULT NULL,
  `trx_type` varchar(36) NOT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  `status` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资金账户流水表';

-- ----------------------------
-- Records of rp_point_account_history
-- ----------------------------

-- ----------------------------
-- Table structure for `seq_table`
-- ----------------------------
DROP TABLE IF EXISTS `seq_table`;
CREATE TABLE `seq_table` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `CURRENT_VALUE` bigint(20) NOT NULL DEFAULT '1000000002',
  `INCREMENT` smallint(6) NOT NULL DEFAULT '1',
  `REMARK` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seq_table
-- ----------------------------
INSERT INTO `seq_table` VALUES ('POINT_ACCOUNT_NO_SEQ', '1000000002', '1', '积分--积分账户编号');

-- ----------------------------
-- Function structure for `FUN_DATE_ADD`
-- ----------------------------
DROP FUNCTION IF EXISTS `FUN_DATE_ADD`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `FUN_DATE_ADD`(STR_DATE VARCHAR(10), STR_INTERVAL INTEGER) RETURNS date
BEGIN
     RETURN date_add(STR_DATE, INTERVAL STR_INTERVAL DAY);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `FUN_NOW`
-- ----------------------------
DROP FUNCTION IF EXISTS `FUN_NOW`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `FUN_NOW`() RETURNS datetime
BEGIN 
		RETURN now();
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `FUN_SEQ`
-- ----------------------------
DROP FUNCTION IF EXISTS `FUN_SEQ`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `FUN_SEQ`(SEQ VARCHAR(50)) RETURNS bigint(20)
BEGIN
     UPDATE SEQ_TABLE
     SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT
     WHERE  SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `FUN_SEQ_CURRENT_VALUE`
-- ----------------------------
DROP FUNCTION IF EXISTS `FUN_SEQ_CURRENT_VALUE`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `FUN_SEQ_CURRENT_VALUE`(SEQ VARCHAR(50)) RETURNS bigint(20)
BEGIN
    DECLARE VALUE INTEGER;
    SET VALUE=0;
    SELECT CURRENT_VALUE INTO VALUE
    FROM SEQ_TABLE 
    WHERE SEQ_NAME=SEQ;
    RETURN VALUE;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `FUN_SEQ_SET_VALUE`
-- ----------------------------
DROP FUNCTION IF EXISTS `FUN_SEQ_SET_VALUE`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `FUN_SEQ_SET_VALUE`(SEQ VARCHAR(50), VALUE INTEGER) RETURNS bigint(20)
BEGIN
     UPDATE SEQ_TABLE 
     SET CURRENT_VALUE=VALUE
     WHERE SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END
;;
DELIMITER ;
