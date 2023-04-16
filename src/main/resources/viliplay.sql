/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : viliplay

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 16/04/2023 17:56:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collection
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`              bigint                                                  NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `video_id`        bigint                                                  NOT NULL COMMENT '视频ID',
    `commentator_id`  bigint                                                  NOT NULL COMMENT '评论用户id',
    `comment_time`    datetime                                                NOT NULL COMMENT '评论发表时间',
    `comment_info`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
    `comment_ip`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论用户ip',
    `comment_like`    bigint(20) UNSIGNED ZEROFILL                            NOT NULL DEFAULT 00000000000000000000 COMMENT '评论点赞次数',
    `comment_dislike` bigint(20) UNSIGNED ZEROFILL                            NOT NULL DEFAULT 00000000000000000000 COMMENT '评论点踩次数',
    `commentator`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `video_username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for danmu
-- ----------------------------
DROP TABLE IF EXISTS `danmu`;
CREATE TABLE `danmu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '弹幕ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '弹幕内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of danmu
-- ----------------------------

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `follower_id` bigint NOT NULL COMMENT '用户ID',
  `followee_id` bigint NOT NULL COMMENT '被关注用户ID',
  `follow_time` datetime NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`follower_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of follow
-- ----------------------------

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `video_id` bigint NOT NULL COMMENT '视频ID',
  `like_time` datetime NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of like
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id`          int                                                     NOT NULL AUTO_INCREMENT COMMENT '用户id',
                         `username`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
                         `password`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
                         `email`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户邮箱',
                         `user_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像',
                         `is_valid`    tinyint                                                 NOT NULL DEFAULT 0 COMMENT '用户是否有效(1无效,0有效)',
                         `type`        tinyint                                                 NOT NULL DEFAULT 0 COMMENT '用户类型（0：普通用户，1：管理员，2：超级管理员，3：VIP用户)',
                         `u_ip`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ip地址',
                         `create_time` datetime                                                NOT NULL COMMENT '用户创建时间',
                         `update_time` datetime                                                NOT NULL COMMENT '用户更新时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'admin', '9vlGM8l1aY4Vt6DfXtOjgQ==', 'admin@qq.com', '/static/default_logo/1.png', 0, 0, '192.168.227.1',
        '2023-01-11 21:06:39', '2023-01-25 20:02:37');
INSERT INTO `user`
VALUES (2, 'test', 'ZURVvDoaGYQVX4okQDO1ag==', 'test@qq.com', '/static/avatar/067672ad-109b-408e-a7a9-376f1973addb.png',
        0, 0, '192.168.227.1', '2023-01-14 23:50:31', '2023-01-14 23:50:31');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `user_id`   int                                                           NOT NULL COMMENT '用户ID',
    `phone`     int                                                           NULL DEFAULT NULL COMMENT '用户电话',
    `user_num`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户账号',
    `gender`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '性别',
    `birthdate` date                                                          NULL DEFAULT NULL COMMENT '出生日期',
    `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
    `school`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校',
    `location`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info`
VALUES (1, NULL, '23429113150', '男', '2023-04-15', '', '未知大学', '北京朝阳');
INSERT INTO `user_info`
VALUES (2, NULL, NULL, '女', '2004-06-01', NULL, '未知大学', '上海浦东');

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`
(
    `id`             bigint                                                   NOT NULL AUTO_INCREMENT COMMENT '视频id',
    `title`          varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频标题',
    `videos_cover`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '视频封面地址',
    `description`    varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '视频描述',
    `videos_time`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '视频时长',
    `videos_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '视频地址',
    `upload_time`    datetime                                                 NOT NULL COMMENT '视频上传时间',
    `play_count`     bigint(20) UNSIGNED ZEROFILL                             NOT NULL DEFAULT 00000000000000000000 COMMENT '视频播放次数',
    `like_count`     bigint(20) UNSIGNED ZEROFILL                             NOT NULL DEFAULT 00000000000000000000 COMMENT '视频点赞次数',
    `dislike_count`  bigint(20) UNSIGNED ZEROFILL                             NOT NULL DEFAULT 00000000000000000000 COMMENT '视频点踩次数',
    `comment_count`  bigint(20) UNSIGNED ZEROFILL                             NOT NULL DEFAULT 00000000000000000000 COMMENT '视频评论数量',
    `status`         int                                                      NOT NULL DEFAULT 0 COMMENT '默认为0对所有人可见，1仅粉丝可见，2仅自己可见，3置顶',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos`
VALUES (1, 'test', 'ac810075-a15d-42fe-99c8-28510bad4d63.png', '无', '00:00:15',
        '86a959bd-cbf9-4342-a46c-0e856aa00b3c.mp4', '2023-01-23 23:21:50', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (2, 'test', '3b724b33-8297-4a3b-bc86-e30be5763ed6.png', '无', '00:00:10',
        'c4b9b72a-e2a2-4960-9b5a-e8721d69c565.mp4', '2023-01-23 23:30:03', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (3, 'test', '6c212352-a140-4831-8802-fcf6a5928e44.png', '无', '00:00:13',
        '6696adf3-fd1e-4461-92e3-4e5c03f6d560.mp4', '2023-01-25 16:42:46', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (4, 'test', 'fc59f9c0-0da7-4214-8ca2-3c5d33727908.png', '无', '00:00:16',
        '1538aeb4-22f1-4f2f-95fa-984acb7ec5f0.mp4', '2023-01-25 16:44:07', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (5, 'test', 'a96698d7-4a74-47d6-a391-ebaa394b7598.png', '无', '00:00:14',
        'bfaf2b6e-6795-46b4-b1bd-2ca0e6e9de2d.mp4', '2023-01-25 16:44:39', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (6, 'test', 'f6c9c649-aeb0-4432-b5e3-71a781958c6c.png', '无', '00:00:10',
        '476f84eb-a5fb-43bd-a7c0-02e7c05426d5.mp4', '2023-01-25 16:45:14', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (7, 'test', '149d6c58-c52d-4780-a11b-23b0a5dd3271.png', '无', '00:00:07',
        '77bc5dac-b7d8-4c86-a1d7-b161b1592de2.mp4', '2023-01-25 16:45:45', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (8, 'test', '614f8190-2488-460e-a55c-fa6ccec56ffd.png', '无', '00:00:06',
        '82675b9a-e33b-4bdc-975f-bb15669d35b0.mp4', '2023-01-25 18:27:47', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (9, 'test', '9bc918ac-df58-4eb9-9a09-b6d6b54cacf6.png', '无', '00:00:08',
        '7d9143c9-14c7-4259-a120-d95ebc980115.mp4', '2023-01-25 18:28:16', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (10, 'iPad Pro M2性能简测，达芬奇大战台式机？', '6bca23b9-87b7-473a-bfc3-b746eb99b5b1.png',
        'iPad Pro也许现在终于有一点 “生产力” 了，那么搭载M2的iPad Pro表现到底会怎样呢？', '00:09:38',
        '0f278f48-861d-4bb5-b9b3-83c52b1d2db4.mp4', '2023-01-25 19:17:13', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (11, '周末愉快', '73bf61ce-db2e-4524-b220-0ab5c1854a3d.png', '', '00:00:06',
        '1419df6b-0e7a-482a-8576-933145247c99.mp4', '2023-01-26 13:48:04', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (12, '无', '3afcd891-528c-4a0f-8fc1-206b78b74a73.png', '#猫狗双全 我最好的玩伴', '00:00:14',
        'f52c8e3c-5605-4b01-b64f-07a4d0eace3b.mp4', '2023-01-29 23:06:41', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);
INSERT INTO `videos`
VALUES (13, '3d游戏开发实战', 'dfa1d687-efb5-48da-a327-8ec5ae3db8e4.png', '3d游戏', '01:16:47',
        '018297b8-097d-446f-a330-81bbd0f67bd1.mp4', '2023-03-01 00:21:23', 00000000000000000000, 00000000000000000000,
        00000000000000000000, 00000000000000000000, 0);

-- ----------------------------
-- Table structure for videos_categories
-- ----------------------------
DROP TABLE IF EXISTS `videos_categories`;
CREATE TABLE `videos_categories`
(
    `id`              int                                                           NOT NULL AUTO_INCREMENT COMMENT '视频id',
    `img`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类图标',
    `categories_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
    `description`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '描述',
    `create_time`     datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time`     datetime                                                      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videos_categories
-- ----------------------------

-- ----------------------------
-- Table structure for videos_info
-- ----------------------------
DROP TABLE IF EXISTS `videos_info`;
CREATE TABLE `videos_info`
(
    `video_id`      bigint NOT NULL COMMENT '视频id',
    `user_id`       bigint NOT NULL COMMENT '用户id',
    `tag_id`        bigint NULL DEFAULT NULL COMMENT '标签id',
    `categories_id` bigint NULL DEFAULT NULL COMMENT '分类id',
    PRIMARY KEY (`video_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videos_info
-- ----------------------------

-- ----------------------------
-- Table structure for videos_tag
-- ----------------------------
DROP TABLE IF EXISTS `videos_tag`;
CREATE TABLE `videos_tag`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '标签id',
    `tag_name`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名字',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videos_tag
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
