DROP TABLE IF EXISTS `rp_notify_record`;
CREATE TABLE `rp_notify_record` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT '主键ID',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本事情',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `notify_rule` varchar(255) DEFAULT NULL COMMENT '通知规则（单位:分钟）',
  `notify_times` int(11) NOT NULL DEFAULT '0' COMMENT '已通知次数',
  `limit_notify_times` int(11) NOT NULL DEFAULT '0' COMMENT '最大通知次数限制',
  `url` varchar(2000) NOT NULL DEFAULT '' COMMENT '通知请求链接（包含通知内容）',
  `merchant_order_no` varchar(50) NOT NULL DEFAULT '' COMMENT '商户订单号',
  `merchant_no` varchar(50) NOT NULL DEFAULT '' COMMENT '商户编号',
  `status` varchar(50) NOT NULL DEFAULT '' COMMENT '通知状态（对应枚举值）',
  `notify_type` varchar(30) DEFAULT NULL COMMENT '通知类型',
  PRIMARY KEY (`id`),
  KEY `AK_KEY_2` (`merchant_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知记录表 RP_NOTIFY_RECORD';
