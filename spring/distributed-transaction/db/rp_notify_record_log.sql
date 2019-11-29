DROP TABLE IF EXISTS `rp_notify_record_log`;
CREATE TABLE `rp_notify_record_log` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT 'ID',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `notify_id` varchar(50) NOT NULL DEFAULT '' COMMENT '通知记录ID',
  `request` varchar(2000) NOT NULL DEFAULT '' COMMENT '请求内容',
  `response` varchar(2000) NOT NULL DEFAULT '' COMMENT '响应内容',
  `merchant_no` varchar(50) NOT NULL DEFAULT '' COMMENT '商户编号',
  `merchant_order_no` varchar(50) NOT NULL COMMENT '商户订单号',
  `http_status` varchar(50) NOT NULL COMMENT 'HTTP状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知记录日志表 RP_NOTIFY_RECORD_LOG';
