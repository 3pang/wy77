-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.6.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 导出  表 test.ww_operator 结构
DROP TABLE IF EXISTS `ww_operator`;
CREATE TABLE IF NOT EXISTS `ww_operator` (
  `oper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `oper_addr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `oper_ext` int(11) DEFAULT NULL,
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='operator';

-- 数据导出被取消选择。

-- 导出  表 test.ww_oper_ext 结构
DROP TABLE IF EXISTS `ww_oper_ext`;
CREATE TABLE IF NOT EXISTS `ww_oper_ext` (
  `ext_id` int(11) NOT NULL AUTO_INCREMENT,
  `ext_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ext_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。

-- 导出  表 test.ww_oper_orle_rel 结构
DROP TABLE IF EXISTS `ww_oper_orle_rel`;
CREATE TABLE IF NOT EXISTS `ww_oper_orle_rel` (
  `rel_ie` int(11) NOT NULL AUTO_INCREMENT,
  `oper_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`rel_ie`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。

-- 导出  表 test.ww_role 结构
DROP TABLE IF EXISTS `ww_role`;
CREATE TABLE IF NOT EXISTS `ww_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
