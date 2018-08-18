--
--初始化数据库脚本
--步骤一、设置hibernate.hbm2ddl.auto为create-drop或者create
--步骤二、创建import.sql文件，并添加到classpath中，这样hibernate启动时就会执行import.sql的内容。
--注意：在import.sql文件中的SQL语句,一行必须是一个完整的SQL语句,否则就可能导入数据失败.
--
/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : gszb

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2017-12-11 20:30:47
*/
use zmkm;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(30) NOT NULL,
  `macAddress` varchar(64) NOT NULL,
  `name` varchar(30) NOT NULL,
  `open_cmd` varchar(64) NOT NULL,
  `parentId` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`pkid`),
  UNIQUE KEY `UK_e72x2d59hsbesrbtiniy6m72r` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card
-- ----------------------------
INSERT INTO `card` VALUES ('1', '2017-12-9 12:42:20', '98:5D:AD:24:1B:FF', '金辉电梯公寓门禁', '04a1a50000', '3', '1', '2');
INSERT INTO `card` VALUES ('2', '2017-12-9 12:52:20', '98:5D:AD:24:1B:11', '金辉电梯公寓测试门禁', '04a1a50000', '3', '1', '2');
INSERT INTO `card` VALUES ('3', '  ', ' ', '荷光路金辉电梯公寓', ' ', '0', '1', '1');
INSERT INTO `card` VALUES ('4', '2017-12-10 12:52:20', '98:5D:AD:24:1B:22', '晴天大厦门禁', '04a1a50000', '5', '1', '2');
INSERT INTO `card` VALUES ('5', ' ', ' ', ' 晴天大厦', ' ', '0', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(30) NOT NULL,
  `image` varchar(30) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `phone` varchar(30) NOT NULL,
  `sex` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`pkid`),
  UNIQUE KEY `UK_589idila9li6a4arw1t8ht1gx` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2017-12-09 14:15:12', 'image', '用户0078', '13751730078', '3', '1', '2');
INSERT INTO `user` VALUES ('4', '2017-12-09 19:01:31', 'image', '用户0079', '13751730079', '3', '1', '2');
INSERT INTO `user` VALUES ('5', '2017-12-09 19:14:38', 'image', '用户0000', '13751730000', '3', '1', '2');
INSERT INTO `user` VALUES ('6', '2017-12-10 23:26:46', 'image', '德玛西亚', '18520648625', '3', '1', '2');

-- ----------------------------
-- Table structure for user_card_mapping
-- ----------------------------
DROP TABLE IF EXISTS `user_card_mapping`;
CREATE TABLE `user_card_mapping` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`pkid`),
  UNIQUE KEY `UK_pttomsc3wr23p7im708wdh2b7` (`card_id`,`user_id`),
  KEY `FK_5kg87fga4brlk0frhag0go2iw` (`user_id`),
  CONSTRAINT `FK_5kg87fga4brlk0frhag0go2iw` FOREIGN KEY (`user_id`) REFERENCES `user` (`pkid`),
  CONSTRAINT `FK_nfh9yxra3y9yam9sn41o87s8a` FOREIGN KEY (`card_id`) REFERENCES `card` (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_card_mapping
-- ----------------------------
INSERT INTO `user_card_mapping` VALUES ('1', '1', '1');
INSERT INTO `user_card_mapping` VALUES ('2', '2', '1');
INSERT INTO `user_card_mapping` VALUES ('3', '4', '5');

-- ----------------------------
-- Table structure for visitors
-- ----------------------------
DROP TABLE IF EXISTS `visitors`;
CREATE TABLE `visitors` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT,
  `end_time` varchar(30) NOT NULL,
  `start_time` varchar(30) NOT NULL,
  `user_card_mapping_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  PRIMARY KEY (`pkid`),
  UNIQUE KEY `UK_44nkwr2x97h5ph05dugsv31ry` (`user_card_mapping_id`,`user_id`),
  KEY `FK_ebfs2x6whs8j2v4ndc99bdnuk` (`user_id`),
  CONSTRAINT `FK_aohnn1r9mflq3ggaufthm495b` FOREIGN KEY (`user_card_mapping_id`) REFERENCES `user_card_mapping` (`pkid`),
  CONSTRAINT `FK_ebfs2x6whs8j2v4ndc99bdnuk` FOREIGN KEY (`user_id`) REFERENCES `user` (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visitors
-- ----------------------------
INSERT INTO `visitors` VALUES ('1', ' 2017-12-13 23:59:59', ' 2017-12-09 19:14:38', '3', '1', '6');
INSERT INTO `visitors` VALUES ('2', '2017-12-11 23:59:59', '2017-12-10 21:56:34', '1', '5', '6');
INSERT INTO `visitors` VALUES ('3', '2017-12-11 23:59:59', '2017-12-10 23:33:45', '1', '6', '6');
