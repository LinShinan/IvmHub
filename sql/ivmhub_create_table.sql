-- MySQL 建表语句 - ivmhub数据库
-- 仅包含表结构、约束、索引，无数据
-- 执行顺序：先运行此文件，再运行 insert_data.sql
-- 已注释所有物理外键，使用逻辑外键（企业标准方案）

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 使用数据库
USE ivmhub;

-- ----------------------------
-- 表结构：商品类型表
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku_class`;
CREATE TABLE `tb_sku_class` (
  `class_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `class_name` varchar(50) DEFAULT '' COMMENT '类别名称',
  `parent_id` int DEFAULT '0' COMMENT '上级id',
  PRIMARY KEY (`class_id`),
  UNIQUE KEY `tb_sku_class_class_name_uindex` (`class_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品类型表';

-- ----------------------------
-- 表结构：商品表
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku`;
CREATE TABLE `tb_sku` (
  `sku_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sku_name` varchar(50) NOT NULL COMMENT '商品名称',
  `sku_image` varchar(500) NOT NULL COMMENT '商品图片',
  `brand_Name` varchar(50) NOT NULL COMMENT '品牌',
  `unit` varchar(20) DEFAULT NULL COMMENT '规格(净含量)',
  `price` int NOT NULL DEFAULT '1' COMMENT '商品价格，单位分',
  `class_id` int NOT NULL COMMENT '商品类型Id',
  `is_discount` tinyint(1) DEFAULT '0' COMMENT '是否打折促销',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`sku_id`),
  UNIQUE KEY `tb_sku_sku_name_uindex` (`sku_name`),
  KEY `sku_sku_class_ClassId_fk` (`class_id`)
  -- 注释物理外键 CONSTRAINT `tb_sku_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `tb_sku_class` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';

-- ----------------------------
-- 表结构：策略表
-- ----------------------------
DROP TABLE IF EXISTS `tb_policy`;
CREATE TABLE `tb_policy` (
  `policy_id` bigint NOT NULL AUTO_INCREMENT COMMENT '策略id',
  `policy_name` varchar(30) DEFAULT NULL COMMENT '策略名称',
  `discount` int DEFAULT NULL COMMENT '策略方案，如：80代表8折',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`policy_id`),
  UNIQUE KEY `tb_policy_policy_name_uindex` (`policy_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='策略表';

-- ----------------------------
-- 表结构：设备类型表
-- ----------------------------
DROP TABLE IF EXISTS `tb_vm_type`;
CREATE TABLE `tb_vm_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(15) NOT NULL COMMENT '型号名称',
  `model` varchar(20) DEFAULT NULL COMMENT '型号编码',
  `image` varchar(500) DEFAULT NULL COMMENT '设备图片',
  `vm_row` int NOT NULL DEFAULT '1' COMMENT '货道行',
  `vm_col` int NOT NULL DEFAULT '1' COMMENT '货道列',
  `channel_max_capacity` int DEFAULT '0' COMMENT '设备容量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_vm_type_name_uindex` (`name`),
  UNIQUE KEY `tb_vm_type_model_uindex` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备类型表';

-- ----------------------------
-- 表结构：设备表
-- ----------------------------
DROP TABLE IF EXISTS `tb_vending_machine`;
CREATE TABLE `tb_vending_machine` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inner_code` varchar(15) DEFAULT '000' COMMENT '设备编号',
  `channel_max_capacity` int DEFAULT NULL COMMENT '设备容量',
  `node_id` int NOT NULL COMMENT '点位Id',
  `addr` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `last_supply_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '上次补货时间',
  `business_type` int NOT NULL COMMENT '商圈类型',
  `region_id` int NOT NULL COMMENT '区域Id',
  `partner_id` int NOT NULL COMMENT '合作商Id',
  `vm_type_id` int NOT NULL DEFAULT '0' COMMENT '设备型号',
  `vm_status` int NOT NULL DEFAULT '0' COMMENT '设备状态，0:未投放;1-运营;3-撤机',
  `running_status` varchar(100) DEFAULT NULL COMMENT '运行状态',
  `longitudes` double DEFAULT '0' COMMENT '经度',
  `latitude` double DEFAULT '0' COMMENT '维度',
  `client_id` varchar(50) DEFAULT NULL COMMENT '客户端连接Id,做emq认证用',
  `policy_id` bigint DEFAULT NULL COMMENT '策略id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vendingmachine_VmId_uindex` (`inner_code`),
  KEY `vendingmachine_node_Id_fk` (`node_id`),
  KEY `vendingmachine_vmtype_TypeId_fk` (`vm_type_id`),
  KEY `policy_id` (`policy_id`)
  -- 注释物理外键
  -- CONSTRAINT `tb_vending_machine_ibfk_1` FOREIGN KEY (`vm_type_id`) REFERENCES `tb_vm_type` (`id`),
  -- CONSTRAINT `tb_vending_machine_ibfk_2` FOREIGN KEY (`node_id`) REFERENCES `tb_node` (`id`),
  -- CONSTRAINT `tb_vending_machine_ibfk_3` FOREIGN KEY (`policy_id`) REFERENCES `tb_policy` (`policy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备表';

-- ----------------------------
-- 表结构：售货机货道表
-- ----------------------------
DROP TABLE IF EXISTS `tb_channel`;
CREATE TABLE `tb_channel` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_code` varchar(10) NOT NULL COMMENT '货道编号',
  `sku_id` bigint DEFAULT '0' COMMENT '商品Id',
  `vm_id` bigint NOT NULL COMMENT '售货机Id',
  `inner_code` varchar(15) NOT NULL COMMENT '售货机软编号',
  `max_capacity` int NOT NULL DEFAULT '0' COMMENT '货道最大容量',
  `current_capacity` int DEFAULT '0' COMMENT '货道当前容量',
  `last_supply_time` datetime DEFAULT NULL COMMENT '上次补货时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `channel_vendingmachine_Id_fk` (`vm_id`),
  KEY `tb_channel_inner_code_index` (`inner_code`)
  -- 注释物理外键 CONSTRAINT `tb_channel_ibfk_1` FOREIGN KEY (`vm_id`) REFERENCES `tb_vending_machine` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5209 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='售货机货道表';

-- ----------------------------
-- 表结构：出货流水表
-- ----------------------------
DROP TABLE IF EXISTS `tb_vendout_running`;
CREATE TABLE `tb_vendout_running` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(38) NOT NULL DEFAULT '' COMMENT '订单编号',
  `inner_code` varchar(15) NOT NULL COMMENT '售货机编号',
  `channel_code` varchar(10) DEFAULT NULL COMMENT '货道编号',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1665296081440129026 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出货流水';

-- ----------------------------
-- 表结构：合作商订单汇总表
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_collect`;
CREATE TABLE `tb_order_collect` (
  `id` bigint NOT NULL COMMENT 'Id',
  `partner_id` int DEFAULT NULL COMMENT '合作商Id',
  `partner_name` varchar(100) DEFAULT NULL COMMENT '合作商名称',
  `order_total_money` bigint DEFAULT NULL COMMENT '日订单收入总金额(平台端总数)',
  `order_date` date DEFAULT NULL COMMENT '发生日期',
  `total_bill` int DEFAULT '0' COMMENT '分成总金额',
  `node_id` int DEFAULT NULL,
  `node_name` varchar(50) DEFAULT NULL COMMENT '点位',
  `order_count` int DEFAULT NULL COMMENT '订单数',
  `ratio` int DEFAULT NULL COMMENT '分成比例',
  `region_name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_order_collect_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='合作商订单汇总表';

-- ----------------------------
-- 表结构：按月统计销售表
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_month_collect`;
CREATE TABLE `tb_order_month_collect` (
  `id` bigint NOT NULL COMMENT 'id',
  `partner_id` int DEFAULT NULL COMMENT '合作商Id',
  `partner_name` varchar(100) DEFAULT NULL COMMENT '合作商名称',
  `region_id` int DEFAULT NULL COMMENT '区域Id',
  `region_name` varchar(50) DEFAULT NULL COMMENT '地区名称',
  `order_total_money` bigint DEFAULT NULL COMMENT '订单总金额',
  `order_total_count` bigint DEFAULT NULL COMMENT '订单总数',
  `month` int DEFAULT NULL COMMENT '月份',
  `year` int DEFAULT NULL COMMENT '年份',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_order_month_collect_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='按月统计各公司销售情况表';

-- ----------------------------
-- 表结构：订单表
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` bigint NOT NULL COMMENT '主键',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `third_no` varchar(34) DEFAULT NULL COMMENT '第三方平台单号',
  `inner_code` varchar(15) DEFAULT NULL COMMENT '机器编号',
  `channel_code` varchar(10) DEFAULT NULL COMMENT '货道编号',
  `sku_id` bigint DEFAULT NULL COMMENT 'skuId',
  `sku_name` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `class_id` int DEFAULT NULL COMMENT '商品类别Id',
  `status` int DEFAULT NULL COMMENT '订单状态:0-待支付;1-支付完成;2-出货成功;3-出货失败;4-已取消',
  `amount` int NOT NULL DEFAULT '0' COMMENT '支付金额',
  `price` int NOT NULL DEFAULT '0' COMMENT '商品金额',
  `pay_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付类型，1支付宝 2微信',
  `pay_status` int DEFAULT '0' COMMENT '支付状态，0-未支付;1-支付完成;2-退款中;3-退款完成',
  `bill` int DEFAULT '0' COMMENT '合作商账单金额',
  `addr` varchar(200) DEFAULT NULL COMMENT '点位地址',
  `region_id` bigint DEFAULT NULL COMMENT '所属区域Id',
  `region_name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `business_type` int DEFAULT NULL COMMENT '所属商圈',
  `partner_id` int DEFAULT NULL COMMENT '合作商Id',
  `open_id` varchar(200) DEFAULT NULL COMMENT '跨站身份验证',
  `node_id` bigint DEFAULT NULL COMMENT '点位Id',
  `node_name` varchar(50) DEFAULT NULL COMMENT '点位名称',
  `cancel_desc` varchar(200) DEFAULT '' COMMENT '取消原因',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Order_OrderNo_uindex` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';

-- ----------------------------
-- 表结构：工单角色表
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `tb_role_role_code_uindex` (`role_code`),
  UNIQUE KEY `tb_role_role_name_uindex` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工单角色表';

-- ----------------------------
-- 表结构：工单员工表
-- ----------------------------
DROP TABLE IF EXISTS `tb_emp`;
CREATE TABLE `tb_emp` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) NOT NULL COMMENT '员工名称',
  `region_id` int DEFAULT NULL COMMENT '所属区域Id',
  `region_name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `role_code` varchar(10) DEFAULT NULL COMMENT '角色编号',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `mobile` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `image` varchar(500) DEFAULT NULL COMMENT '员工头像',
  `status` tinyint DEFAULT '1' COMMENT '是否启用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_user_Id_uindex` (`id`),
  UNIQUE KEY `tb_user_user_name_uindex` (`user_name`),
  UNIQUE KEY `tb_user_mobile_uindex` (`mobile`),
  KEY `role_id` (`role_id`),
  KEY `region_id` (`region_id`)
  -- 注释物理外键
  -- CONSTRAINT `tb_emp_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`role_id`),
  -- CONSTRAINT `tb_emp_ibfk_2` FOREIGN KEY (`region_id`) REFERENCES `tb_region` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工单员工表';

-- ----------------------------
-- 表结构：工单类型表
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_type`;
CREATE TABLE `tb_task_type` (
  `type_id` int NOT NULL,
  `type_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型名称',
  `type` int DEFAULT '1' COMMENT '工单类型。1:维修工单;2:运营工单',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单类型';

-- ----------------------------
-- 表结构：工单表
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task` (
  `task_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `task_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '工单编号',
  `task_status` int DEFAULT NULL COMMENT '工单状态',
  `create_type` int DEFAULT NULL COMMENT '创建类型 0：自动 1：手动',
  `inner_code` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '售货机编码',
  `user_id` int DEFAULT NULL COMMENT '执行人id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '执行人名称',
  `region_id` bigint DEFAULT NULL COMMENT '所属区域Id',
  `descri` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `product_type_id` int DEFAULT '1' COMMENT '工单类型id',
  `assignor_id` int DEFAULT NULL COMMENT '指派人Id',
  `addr` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `tb_task_task_code_uindex` (`task_code`),
  KEY `task_productiontype_TypeId_fk` (`product_type_id`),
  KEY `task_taskstatustype_StatusID_fk` (`task_status`),
  KEY `task_tasktype_TypeId_fk` (`create_type`)
) ENGINE=InnoDB AUTO_INCREMENT=544 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单表';

-- ----------------------------
-- 表结构：工单详情表
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_details`;
CREATE TABLE `tb_task_details` (
  `details_id` bigint NOT NULL AUTO_INCREMENT,
  `task_id` bigint DEFAULT NULL COMMENT '工单Id',
  `channel_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '货道编号',
  `expect_capacity` int NOT NULL DEFAULT '0' COMMENT '补货期望容量',
  `sku_id` bigint DEFAULT NULL COMMENT '商品Id',
  `sku_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sku_image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`details_id`),
  KEY `taskdetails_task_TaskId_fk` (`task_id`)
  -- 注释物理外键 CONSTRAINT `taskdetails_task_TaskId_fk` FOREIGN KEY (`task_id`) REFERENCES `tb_task` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3770 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单详情，只有补货工单才有';

-- ----------------------------
-- 表结构：工单按日统计表
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_collect`;
CREATE TABLE `tb_task_collect` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `finish_count` int DEFAULT '0' COMMENT '当日工单完成数',
  `progress_count` int DEFAULT '0' COMMENT '当日进行中的工单数',
  `cancel_count` int DEFAULT '0' COMMENT '当日取消工单数',
  `collect_date` date DEFAULT NULL COMMENT '汇总的日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单按日统计表';

-- ----------------------------
-- 表结构：自动补货任务表
-- ----------------------------
DROP TABLE IF EXISTS `tb_job`;
CREATE TABLE `tb_job` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `alert_value` int DEFAULT '0' COMMENT '警戒值百分比',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='自动补货任务';

-- 恢复数据库默认配置
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;