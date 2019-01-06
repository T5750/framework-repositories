/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : pay_dubbo_base

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-01-06 16:15:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rp_notify_record
-- ----------------------------
DROP TABLE IF EXISTS `rp_notify_record`;
CREATE TABLE `rp_notify_record` (
  `id` varchar(50) NOT NULL,
  `version` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '修改者',
  `creater` varchar(100) DEFAULT NULL COMMENT '创建者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `notify_times` int(11) NOT NULL,
  `limit_notify_times` int(11) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `merchant_order_no` varchar(50) NOT NULL,
  `merchant_no` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL COMMENT '100:成功 101:失败',
  `notify_type` varchar(30) DEFAULT NULL COMMENT '通知类型',
  PRIMARY KEY (`id`),
  KEY `AK_KEY_2` (`merchant_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知记录表 RP_NOTIFY_RECORD';

-- ----------------------------
-- Records of rp_notify_record
-- ----------------------------
INSERT INTO `rp_notify_record` VALUES ('08f57b13cd924383ad71160b0f30b82a', '0', '2019-01-06 16:05:27', null, null, '2019-01-06 16:05:28', '1', '5', 'http://127.0.0.1:8083/pay-web-sample-shop/microPayNotify/notify?field1=扩展字段1&field2=扩展字段2&field3=扩展字段3&field4=扩展字段4&field5=扩展字段5&orderDate=20190106&orderNo=pt92019010616051200&orderPrice=10.000000&orderTime=20190106160512&payKey=4c52295065654407b42797cda80dd07d&payWayCode=TEST_PAY_HTTP_CLIENT&productName=模拟支付网关支付产品&remark=支付备注&tradeStatus=SUCCESS&sign=2155C5AB28DC6F3B0FD01697AE3E9661', 'pt92019010616051200', '88882016071900000003', 'SUCCESS', 'MERCHANT');
INSERT INTO `rp_notify_record` VALUES ('ff7a0ea9a7754de882988f95c1d6067b', '0', '2019-01-06 16:05:27', null, null, '2019-01-06 16:05:28', '1', '5', 'http://127.0.0.1:8083/pay-web-sample-shop/microPayNotify/notify?field1=扩展字段1&field2=扩展字段2&field3=扩展字段3&field4=扩展字段4&field5=扩展字段5&orderDate=20190106&orderNo=pt102019010616051200&orderPrice=10.000000&orderTime=20190106160512&payKey=abcf900288114d5fa7fde764966eb2ff&payWayCode=TEST_PAY_HTTP_CLIENT&productName=模拟支付网关支付产品&remark=支付备注&tradeStatus=SUCCESS&sign=BFB59CBDA934B62CBFBD20696AA27AB6', 'pt102019010616051200', '88882016072200000005', 'SUCCESS', 'MERCHANT');

-- ----------------------------
-- Table structure for rp_notify_record_log
-- ----------------------------
DROP TABLE IF EXISTS `rp_notify_record_log`;
CREATE TABLE `rp_notify_record_log` (
  `id` varchar(50) NOT NULL,
  `version` int(11) NOT NULL,
  `editor` varchar(100) DEFAULT NULL COMMENT '修改者',
  `creater` varchar(100) DEFAULT NULL COMMENT '创建者',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `create_time` datetime NOT NULL,
  `notify_id` varchar(50) NOT NULL,
  `request` varchar(2000) NOT NULL,
  `response` varchar(2000) NOT NULL,
  `merchant_no` varchar(50) NOT NULL,
  `merchant_order_no` varchar(50) NOT NULL COMMENT '商户订单号',
  `http_status` varchar(50) NOT NULL COMMENT 'HTTP状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知记录日志表 RP_NOTIFY_RECORD_LOG';

-- ----------------------------
-- Records of rp_notify_record_log
-- ----------------------------
INSERT INTO `rp_notify_record_log` VALUES ('509d3e3733d24fb08b8f565ca74d0dc8', '0', null, null, '2019-01-06 16:05:28', '2019-01-06 16:05:28', 'ff7a0ea9a7754de882988f95c1d6067b', 'http://127.0.0.1:8083/pay-web-sample-shop/microPayNotify/notify?field1=扩展字段1&field2=扩展字段2&field3=扩展字段3&field4=扩展字段4&field5=扩展字段5&orderDate=20190106&orderNo=pt102019010616051200&orderPrice=10.000000&orderTime=20190106160512&payKey=abcf900288114d5fa7fde764966eb2ff&payWayCode=TEST_PAY_HTTP_CLIENT&productName=模拟支付网关支付产品&remark=支付备注&tradeStatus=SUCCESS&sign=BFB59CBDA934B62CBFBD20696AA27AB6', 'success', '88882016072200000005', 'pt102019010616051200', '200');
INSERT INTO `rp_notify_record_log` VALUES ('b4b3c82c3f0d4a33b22bc1190f5d7a9d', '0', null, null, '2019-01-06 16:05:28', '2019-01-06 16:05:28', '08f57b13cd924383ad71160b0f30b82a', 'http://127.0.0.1:8083/pay-web-sample-shop/microPayNotify/notify?field1=扩展字段1&field2=扩展字段2&field3=扩展字段3&field4=扩展字段4&field5=扩展字段5&orderDate=20190106&orderNo=pt92019010616051200&orderPrice=10.000000&orderTime=20190106160512&payKey=4c52295065654407b42797cda80dd07d&payWayCode=TEST_PAY_HTTP_CLIENT&productName=模拟支付网关支付产品&remark=支付备注&tradeStatus=SUCCESS&sign=2155C5AB28DC6F3B0FD01697AE3E9661', 'success', '88882016071900000003', 'pt92019010616051200', '200');

-- ----------------------------
-- Table structure for rp_pay_product
-- ----------------------------
DROP TABLE IF EXISTS `rp_pay_product`;
CREATE TABLE `rp_pay_product` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `status` varchar(36) NOT NULL,
  `product_code` varchar(50) NOT NULL COMMENT '支付产品编号',
  `product_name` varchar(200) NOT NULL COMMENT '支付产品名称',
  `audit_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付产品表';

-- ----------------------------
-- Records of rp_pay_product
-- ----------------------------
INSERT INTO `rp_pay_product` VALUES ('5ac23e853c454edcb2bf51624bdbc194', '2016-07-19 17:53:12', '2016-07-19 17:58:21', '0', 'ACTIVE', 'Pay', '支付', 'YES');
INSERT INTO `rp_pay_product` VALUES ('7e496b1206714527863fa2cf3f836db5', '2016-07-19 18:51:38', '2016-07-21 16:39:58', '0', 'ACTIVE', 'RC', 'RC', 'YES');
INSERT INTO `rp_pay_product` VALUES ('ec7502bbf1894cb69ede121433594285', '2016-09-17 18:08:50', '2016-09-17 18:09:16', '0', 'UNACTIVE', 'BBB', 'BBB', 'NO');

-- ----------------------------
-- Table structure for rp_pay_way
-- ----------------------------
DROP TABLE IF EXISTS `rp_pay_way`;
CREATE TABLE `rp_pay_way` (
  `ID` varchar(50) NOT NULL COMMENT 'ID',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT 'version',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `edit_time` datetime DEFAULT NULL COMMENT '修改时间',
  `pay_way_code` varchar(50) NOT NULL COMMENT '支付方式编号',
  `pay_way_name` varchar(100) NOT NULL COMMENT '支付方式名称',
  `pay_type_code` varchar(50) NOT NULL COMMENT '支付类型编号',
  `pay_type_name` varchar(100) NOT NULL COMMENT '支付类型名称',
  `pay_product_code` varchar(50) DEFAULT NULL COMMENT '支付产品编号',
  `status` varchar(36) NOT NULL COMMENT '状态(100:正常状态,101非正常状态)',
  `sorts` int(11) DEFAULT '1000' COMMENT '排序(倒序排序,默认值1000)',
  `pay_rate` double NOT NULL COMMENT '商户支付费率',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付方式';

-- ----------------------------
-- Records of rp_pay_way
-- ----------------------------
INSERT INTO `rp_pay_way` VALUES ('1431d5a280fc4c51afcfbca9f32822d8', '0', '2016-07-19 17:53:52', null, 'WEIXIN', '微信', 'SCANPAY', '扫码支付', 'Pay', 'ACTIVE', null, '0.8');
INSERT INTO `rp_pay_way` VALUES ('3ee734a9ed68405c89ead08683cad6c7', '0', '2016-09-17 18:09:05', '2016-09-17 18:09:10', 'TEST_PAY', '测试模拟支付', 'TEST_PAY', '测试模拟支付', 'BBB', 'UNACTIVE', null, '9');
INSERT INTO `rp_pay_way` VALUES ('a4af58b75b3e40c4bd38ac647e27800f', '0', '2016-07-19 18:51:52', null, 'TEST_PAY', '测试模拟支付', 'TEST_PAY', '测试模拟支付', 'RC', 'ACTIVE', null, '0.8');
INSERT INTO `rp_pay_way` VALUES ('c5cdda3d8f1e47bca9ac96ffb2ea17b1', '0', '2016-07-19 17:54:16', '2016-07-19 17:54:25', 'ALIPAY', '支付宝', 'DIRECT_PAY', '即时到账', 'Pay', 'ACTIVE', null, '0.8');
INSERT INTO `rp_pay_way` VALUES ('db522b1446ff4dc2bd6fcd73bc1f3423', '0', '2016-07-21 16:39:51', null, 'TEST_PAY_HTTP_CLIENT', '测试模拟httpclient支付', 'TEST_PAY_HTTP_CLIENT', '测试模拟httpclient支付', 'RC', 'ACTIVE', null, '0.8');
INSERT INTO `rp_pay_way` VALUES ('dcda9a88435e47b3b6d24df2c6358be1', '0', '2016-07-19 18:52:41', null, 'ALIPAY', '支付宝', 'DIRECT_PAY', '即时到账', 'RC', 'ACTIVE', null, '0.8');
INSERT INTO `rp_pay_way` VALUES ('ff242c5f9c3e4ea7a857860e57f6ddd4', '0', '2016-07-19 18:52:29', null, 'WEIXIN', '微信', 'SCANPAY', '扫码支付', 'RC', 'ACTIVE', null, '0.8');

-- ----------------------------
-- Table structure for rp_user_info
-- ----------------------------
DROP TABLE IF EXISTS `rp_user_info`;
CREATE TABLE `rp_user_info` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` varchar(36) NOT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `account_no` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='该表用来存放用户的基本信息';

-- ----------------------------
-- Records of rp_user_info
-- ----------------------------
INSERT INTO `rp_user_info` VALUES ('0101ff47cd1f43a5b6a52144e9a78e30', '2016-07-24 20:20:26', 'ACTIVE', '88882016072400000006', 'tc', '99992016072400000006');
INSERT INTO `rp_user_info` VALUES ('0764455933ce43a093bbe0f60867ee91', '2016-07-22 15:56:26', 'ACTIVE', '88882016072200000005', 'sh', '99992016072200000005');
INSERT INTO `rp_user_info` VALUES ('18fee8f2fafe4251a5aa2e95e30dc126', '2016-07-21 01:06:51', 'ACTIVE', '88882016072100000004', 'fs', '99992016072100000004');
INSERT INTO `rp_user_info` VALUES ('64b098ee76574c5681710b47d10bcd64', '2016-07-24 20:21:45', 'ACTIVE', '88882016072400000007', 'mk', '99992016072400000007');
INSERT INTO `rp_user_info` VALUES ('c48f5ac8024a4547878b5708b311215c', '2016-07-19 16:55:09', 'ACTIVE', '88882016071900000003', 'bm', '99992016071900000003');

-- ----------------------------
-- Table structure for rp_user_pay_config
-- ----------------------------
DROP TABLE IF EXISTS `rp_user_pay_config`;
CREATE TABLE `rp_user_pay_config` (
  `id` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `status` varchar(36) NOT NULL,
  `audit_status` varchar(45) DEFAULT NULL,
  `is_auto_sett` varchar(36) NOT NULL DEFAULT 'NO',
  `product_code` varchar(50) NOT NULL COMMENT '支付产品编号',
  `product_name` varchar(200) NOT NULL COMMENT '支付产品名称',
  `user_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `risk_day` int(11) DEFAULT NULL,
  `pay_key` varchar(50) DEFAULT NULL,
  `fund_into_type` varchar(50) DEFAULT NULL,
  `pay_secret` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付设置表';

-- ----------------------------
-- Records of rp_user_pay_config
-- ----------------------------
INSERT INTO `rp_user_pay_config` VALUES ('2a7f8f1c8ebf4379a42a6f88b8b8bc56', '2016-07-22 15:57:40', null, '0', null, 'ACTIVE', 'YES', 'NO', 'RC', 'RC', '88882016072100000004', 'sh', '1', '8ba459f55ff04f39b0128a3cb4a48f2b', 'PLAT_RECEIVES', '3e2ca2eb1f024570b241d65eb4832c37');
INSERT INTO `rp_user_pay_config` VALUES ('3eef704ce6ef4f27b39f8b9ba1af71e4', '2016-07-24 20:22:48', null, '0', null, 'ACTIVE', 'YES', 'NO', 'RC', 'RC', '88882016072400000007', 'tc', '1', '93d1ea2f9f3b448994f39d6efc7746ef', 'PLAT_RECEIVES', 'fad7ea79810c4af7a973c091aa7c6143');
INSERT INTO `rp_user_pay_config` VALUES ('8d5a1884969a4bf68dc9c19b7a0bdc18', '2016-07-22 15:56:57', null, '0', null, 'ACTIVE', 'YES', 'NO', 'RC', 'RC', '88882016072200000005', 'fs', '1', 'abcf900288114d5fa7fde764966eb2ff', 'PLAT_RECEIVES', 'd94d7c2d56eb4b06828cf746c8be17e6');
INSERT INTO `rp_user_pay_config` VALUES ('c101c24326554b848f0f497234f129d7', '2016-07-19 17:59:38', '2019-01-06 14:35:03', '0', null, 'ACTIVE', 'YES', 'NO', 'RC', 'RC', '88882016071900000003', 'bm', '1', '4c52295065654407b42797cda80dd07d', 'PLAT_RECEIVES', '6606353e0dab4f7b9a723f2d3e3276b7');
INSERT INTO `rp_user_pay_config` VALUES ('e510d10eed13497d9fafb492688d09d3', '2016-07-24 20:23:11', null, '0', null, 'ACTIVE', 'YES', 'NO', 'RC', 'RC', '88882016072400000006', 'mk', '1', 'ca6577dff6d647ac882dfb405ceda21e', 'PLAT_RECEIVES', '1b8da6c9b7544856955fcff6bf920f84');

-- ----------------------------
-- Table structure for rp_user_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `rp_user_pay_info`;
CREATE TABLE `rp_user_pay_info` (
  `id_` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `status` varchar(36) NOT NULL,
  `app_id` varchar(50) NOT NULL,
  `app_sectet` varchar(100) DEFAULT NULL,
  `merchant_id` varchar(50) DEFAULT NULL,
  `app_type` varchar(50) DEFAULT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `partner_key` varchar(100) DEFAULT NULL,
  `pay_way_code` varchar(50) NOT NULL COMMENT '支付方式编号',
  `pay_way_name` varchar(100) NOT NULL COMMENT '支付方式名称',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='该表用来存放用户开通的第三方支付信息';

-- ----------------------------
-- Records of rp_user_pay_info
-- ----------------------------
INSERT INTO `rp_user_pay_info` VALUES ('04a257f258f74d4eb8a2182b627a199f', '2016-07-24 20:22:48', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072400000007', 'tc', '', 'WEIXIN', '微信');
INSERT INTO `rp_user_pay_info` VALUES ('1834040201214f9a8ca0557045fc7c72', '2016-07-24 20:23:11', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072400000006', 'mk', '', 'ALIPAY', '支付宝');
INSERT INTO `rp_user_pay_info` VALUES ('45ecd39829cb4c009ad9c5a1153cbd57', '2016-07-22 15:56:57', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072200000005', 'fs', '', 'WEIXIN', '微信');
INSERT INTO `rp_user_pay_info` VALUES ('4821fb3f63db4f58a0fc75a91a38bc04', '2016-07-22 15:57:40', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072100000004', 'sh', '', 'WEIXIN', '微信');
INSERT INTO `rp_user_pay_info` VALUES ('4ee1f39ecb0544e792ef5d5df6ec6fb2', '2016-07-24 20:23:11', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072400000006', 'mk', '', 'WEIXIN', '微信');
INSERT INTO `rp_user_pay_info` VALUES ('52bca84256154c588d2952b34a68dad4', '2016-07-22 15:56:57', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072200000005', 'fs', '', 'ALIPAY', '支付宝');
INSERT INTO `rp_user_pay_info` VALUES ('8a23a87d6afb425cb8974110bb015f06', '2016-07-22 15:57:40', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072100000004', 'sh', '', 'ALIPAY', '支付宝');
INSERT INTO `rp_user_pay_info` VALUES ('8f3064039e8241029b71be1b8a9be7ce', '2016-07-19 17:59:38', '2019-01-06 14:35:03', '0', null, 'ACTIVE', '', null, '', null, '88882016071900000003', 'bm', '', 'ALIPAY', '支付宝');
INSERT INTO `rp_user_pay_info` VALUES ('981cd025a111452cafb9c103c5df0f9d', '2016-07-24 20:22:48', null, '0', null, 'ACTIVE', '', null, '', null, '88882016072400000007', 'tc', '', 'ALIPAY', '支付宝');
INSERT INTO `rp_user_pay_info` VALUES ('d6ecaedb883149a28356d2510b711793', '2016-07-19 17:59:38', '2019-01-06 14:35:03', '0', null, 'ACTIVE', '', null, '', null, '88882016071900000003', 'bm', '', 'WEIXIN', '微信');

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
INSERT INTO `seq_table` VALUES ('ACCOUNT_NO_SEQ', '1000000007', '1', '账户--账户编号');
INSERT INTO `seq_table` VALUES ('USER_NO_SEQ', '1000000007', '1', '用户--用户编号');

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
