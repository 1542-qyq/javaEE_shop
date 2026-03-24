/*
 Navicat Premium Dump SQL

 Source Server         : 1542
 Source Server Type    : MySQL
 Source Server Version : 80044 (8.0.44)
 Source Host           : localhost:3306
 Source Schema         : final_ch9

 Target Server Type    : MySQL
 Target Server Version : 80044 (8.0.44)
 File Encoding         : 65001

 Date: 18/12/2025 15:08:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ausertable
-- ----------------------------
DROP TABLE IF EXISTS `ausertable`;
CREATE TABLE `ausertable`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `aname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员用户名',
  `apwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ausertable
-- ----------------------------
INSERT INTO `ausertable` VALUES (1, 'Haixia', '123456');
INSERT INTO `ausertable` VALUES (2, 'qiu', '123456');

-- ----------------------------
-- Table structure for busertable
-- ----------------------------
DROP TABLE IF EXISTS `busertable`;
CREATE TABLE `busertable`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bemail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NULL DEFAULT NULL,
  `bpwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NULL DEFAULT NULL,
  `userName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of busertable
-- ----------------------------
INSERT INTO `busertable` VALUES (2, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for carttable
-- ----------------------------
DROP TABLE IF EXISTS `carttable`;
CREATE TABLE `carttable`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `busertable_id` int NULL DEFAULT NULL,
  `goodstable_id` int NULL DEFAULT NULL,
  `shoppingnum` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of carttable
-- ----------------------------

-- ----------------------------
-- Table structure for goodstable
-- ----------------------------
DROP TABLE IF EXISTS `goodstable`;
CREATE TABLE `goodstable`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `gname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NULL DEFAULT NULL,
  `gprice` decimal(10, 2) NULL DEFAULT NULL,
  `gstore` int NULL DEFAULT NULL,
  `gpicture` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NULL DEFAULT NULL,
  `goodstype_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goodstable
-- ----------------------------

-- ----------------------------
-- Table structure for goodstypetable
-- ----------------------------
DROP TABLE IF EXISTS `goodstypetable`;
CREATE TABLE `goodstypetable`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `typename` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodstypetable
-- ----------------------------
INSERT INTO `goodstypetable` VALUES (18, '家电');
INSERT INTO `goodstypetable` VALUES (19, '水果');
INSERT INTO `goodstypetable` VALUES (20, '文具');
INSERT INTO `goodstypetable` VALUES (21, '服装');
INSERT INTO `goodstypetable` VALUES (22, '鲜花');

-- ----------------------------
-- Table structure for orderbasetable
-- ----------------------------
DROP TABLE IF EXISTS `orderbasetable`;
CREATE TABLE `orderbasetable`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `busertable_id` int NULL DEFAULT NULL,
  `amount` decimal(10, 2) NULL DEFAULT NULL,
  `order_date` datetime NULL DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orderbasetable
-- ----------------------------

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderbasetable_id` int NOT NULL,
  `goodstable_id` int NOT NULL,
  `shoppingnum` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `odsn`(`orderbasetable_id` ASC) USING BTREE,
  INDEX `gno3`(`goodstable_id` ASC) USING BTREE,
  CONSTRAINT `orderdetail_ibfk_1` FOREIGN KEY (`goodstable_id`) REFERENCES `goodstable` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orderdetail_ibfk_2` FOREIGN KEY (`orderbasetable_id`) REFERENCES `orderbasetable` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orderdetail
-- ----------------------------
INSERT INTO `orderdetail` VALUES (7, 6, 47, 50);
INSERT INTO `orderdetail` VALUES (8, 6, 36, 30);

SET FOREIGN_KEY_CHECKS = 1;
