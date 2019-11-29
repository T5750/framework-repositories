/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : pay_dubbo_accounting

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-01-05 15:23:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rp_accounting_voucher
-- ----------------------------
DROP TABLE IF EXISTS `rp_accounting_voucher`;
CREATE TABLE `rp_accounting_voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `entry_type` smallint(6) NOT NULL COMMENT '会计分录类型',
  `request_no` varchar(32) NOT NULL COMMENT '请求号',
  `from_system` smallint(6) NOT NULL COMMENT '来源系统',
  `voucher_no` varchar(32) DEFAULT NULL COMMENT '原始凭证号',
  `accounting_date` date DEFAULT NULL COMMENT '会计日期',
  `bank_change_amount` decimal(24,10) DEFAULT NULL COMMENT '平台银行帐户变动金额',
  `payer_account_no` varchar(20) DEFAULT NULL COMMENT '付款方账号',
  `receiver_account_no` varchar(20) DEFAULT NULL COMMENT '收款方账号',
  `bank_account` varchar(20) DEFAULT NULL COMMENT '银行账户',
  `bank_channel_code` varchar(32) DEFAULT NULL COMMENT '银行渠道编码',
  `payer_change_amount` decimal(24,10) DEFAULT NULL COMMENT '付款方账户变动金额',
  `receiver_change_amount` decimal(24,10) DEFAULT NULL COMMENT '收款方账户变动金额',
  `profit` decimal(24,10) DEFAULT NULL COMMENT '利润',
  `income` decimal(24,10) DEFAULT NULL COMMENT '收入',
  `cost` decimal(24,10) DEFAULT NULL COMMENT '成本',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `bank_order_no` varchar(32) DEFAULT NULL COMMENT '银行订单号',
  `payer_account_type` smallint(6) DEFAULT NULL COMMENT '付款方帐户类型',
  `pay_amount` decimal(20,6) DEFAULT NULL COMMENT '支付金额',
  `receiver_account_type` smallint(6) DEFAULT NULL COMMENT '收款方帐户类型',
  `receiver_fee` decimal(20,6) DEFAULT NULL COMMENT '收款方手续费',
  `payer_fee` decimal(20,6) DEFAULT NULL COMMENT '付款方手续费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `REQUEST_NOTE_UKEY` (`entry_type`,`voucher_no`,`from_system`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会计原始凭证表';

-- ----------------------------
-- Records of rp_accounting_voucher
-- ----------------------------

-- ----------------------------
-- Table structure for seq_table
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
INSERT INTO `seq_table` VALUES ('ACCOUNTING_REQUEST_NO_SEQ', '1000000001', '1', '会计--请求号自增序列');

-- ----------------------------
-- Function structure for FUN_DATE_ADD
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
-- Function structure for FUN_NOW
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
-- Function structure for FUN_SEQ
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
-- Function structure for FUN_SEQ_CURRENT_VALUE
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
-- Function structure for FUN_SEQ_SET_VALUE
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
