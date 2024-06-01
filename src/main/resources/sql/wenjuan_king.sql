/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : wenjuan_king

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 01/06/2024 17:15:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `aid` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '答题ID',
  `pid` int UNSIGNED NOT NULL COMMENT '问卷ID',
  `qid` int UNSIGNED NOT NULL COMMENT '问题ID',
  `uid` int UNSIGNED NOT NULL COMMENT '回答问题的用户的id',
  `question_type` int NOT NULL COMMENT '问题类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `answer_content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '答案内容',
  PRIMARY KEY (`aid`) USING BTREE,
  INDEX `FK_paper_id_answer`(`pid` ASC) USING BTREE,
  INDEX `FK_question_id`(`qid` ASC) USING BTREE,
  INDEX `answer_user_uid_fk`(`uid` ASC) USING BTREE,
  CONSTRAINT `answer_user_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_paper_id_answer` FOREIGN KEY (`pid`) REFERENCES `paper` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_question_id` FOREIGN KEY (`qid`) REFERENCES `question` (`qid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '答题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer
-- ----------------------------

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `pid` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '问卷ID',
  `uid` int UNSIGNED NOT NULL COMMENT '用户ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问卷标题',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `FK_user_id`(`uid` ASC) USING BTREE,
  CONSTRAINT `FK_user_id` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问卷表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES (7, 15, '你幸福吗的调查', '2024-06-01 15:48:31', 0, '2018-09-12 00:00:00', '2018-10-01 00:00:00');
INSERT INTO `paper` VALUES (8, 15, '你幸福吗的调查', '2024-06-01 15:48:33', 0, '2018-09-12 00:00:00', '2018-10-01 00:00:00');
INSERT INTO `paper` VALUES (9, 15, '你幸福吗的调查', '2024-06-01 17:05:04', 0, '2018-09-12 00:00:00', '2018-10-01 00:00:00');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `qid` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  `pid` int UNSIGNED NOT NULL COMMENT '问卷ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `question_type` int NOT NULL COMMENT '问题类型',
  `question_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题标题',
  `question_option` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问题选项',
  PRIMARY KEY (`qid`) USING BTREE,
  INDEX `FK_paper_id`(`pid` ASC) USING BTREE,
  CONSTRAINT `FK_paper_id` FOREIGN KEY (`pid`) REFERENCES `paper` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '问题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (8, 7, '2024-06-01 15:48:31', 1, '你的收入是多少？', '[\"2000以下\",\"2000-5000\",\"5000+\"]');
INSERT INTO `question` VALUES (9, 7, '2024-06-01 15:48:31', 2, '你家里有哪些家电？', '[\"冰箱\",\"洗衣机\",\"空调\",\"麻将机\"]');
INSERT INTO `question` VALUES (10, 7, '2024-06-01 15:48:31', 3, '说一说你觉得最幸福的事', '[]');
INSERT INTO `question` VALUES (11, 8, '2024-06-01 15:48:33', 1, '你的收入是多少？', '[\"2000以下\",\"2000-5000\",\"5000+\"]');
INSERT INTO `question` VALUES (12, 8, '2024-06-01 15:48:33', 2, '你家里有哪些家电？', '[\"冰箱\",\"洗衣机\",\"空调\",\"麻将机\"]');
INSERT INTO `question` VALUES (13, 8, '2024-06-01 15:48:33', 3, '说一说你觉得最幸福的事', '[]');
INSERT INTO `question` VALUES (14, 9, '2024-06-01 17:05:04', 1, '你的收入是多少？', '[\"2000以下\",\"2000-5000\",\"5000+\"]');
INSERT INTO `question` VALUES (15, 9, '2024-06-01 17:05:04', 2, '你家里有哪些家电？', '[\"冰箱\",\"洗衣机\",\"空调\",\"麻将机\"]');
INSERT INTO `question` VALUES (16, 9, '2024-06-01 17:05:04', 3, '说一说你觉得最幸福的事', '[]');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `UK_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (15, 'mmm', 'e35cf7b66449df565f93c607d5a81d09', '2024-05-31 16:32:14', '2024-05-31 16:32:14');
INSERT INTO `user` VALUES (16, 'ZhangSan', 'e10adc3949ba59abbe56e057f20f883e', '2024-05-31 16:47:19', '2024-05-31 16:47:19');
INSERT INTO `user` VALUES (17, 'xiaomin', 'e10adc3949ba59abbe56e057f20f883e', '2024-05-31 21:40:04', '2024-05-31 21:40:04');
INSERT INTO `user` VALUES (19, 'xiaominuuu', 'e10adc3949ba59abbe56e057f20f883e', '2024-05-31 21:40:23', '2024-05-31 21:40:23');
INSERT INTO `user` VALUES (21, 'kkkk', '25f9e794323b453885f5181f1b624d0b', '2024-05-31 21:45:37', '2024-05-31 21:45:37');
INSERT INTO `user` VALUES (22, 'wss', '96e79218965eb72c92a549dd5a330112', '2024-05-31 21:54:55', '2024-05-31 21:54:55');
INSERT INTO `user` VALUES (25, 'wsse', '96e79218965eb72c92a549dd5a330112', '2024-05-31 21:56:00', '2024-05-31 21:56:00');
INSERT INTO `user` VALUES (26, 'wse', '5d93ceb70e2bf5daa84ec3d0cd2c731a', '2024-05-31 22:01:59', '2024-05-31 22:01:59');
INSERT INTO `user` VALUES (28, 'wse2', '5d93ceb70e2bf5daa84ec3d0cd2c731a', '2024-05-31 22:05:42', '2024-05-31 22:05:42');
INSERT INTO `user` VALUES (29, 'wse3', '25d55ad283aa400af464c76d713c07ad', '2024-05-31 22:05:59', '2024-05-31 22:05:59');
INSERT INTO `user` VALUES (30, 'ran', '797a5a33b8e50be2084c6e54a64abac4', '2024-05-31 22:10:29', '2024-05-31 22:10:29');

SET FOREIGN_KEY_CHECKS = 1;
