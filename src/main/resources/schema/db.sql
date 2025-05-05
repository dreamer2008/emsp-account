/*

 Source Server         : MyLocal
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : platform

 Date: 04/05/2025 22:39:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `contract_id` varchar(255) DEFAULT NULL,
                            `created_at` datetime(6) DEFAULT NULL,
                            `email` varchar(255) NOT NULL,
                            `last_updated` datetime(6) DEFAULT NULL,
                            `status` enum('ACTIVATED','CREATED','DEACTIVATED') DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UKn7ihswpy07ci568w34q0oi8he` (`email`),
                            UNIQUE KEY `UK6l9jws6ewd7ji6djkp20m4fh8` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cards
-- ----------------------------
DROP TABLE IF EXISTS `cards`;
CREATE TABLE `cards` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `contract_id` varchar(255) DEFAULT NULL,
                         `created_at` datetime(6) DEFAULT NULL,
                         `last_updated` datetime(6) DEFAULT NULL,
                         `rfid` varchar(255) DEFAULT NULL,
                         `status` enum('ACTIVATED','ASSIGNED','CREATED','DEACTIVATED') DEFAULT NULL,
                         `account_id` bigint DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK8kq4ieb3xtrb7gkib9kbgd66b` (`contract_id`),
                         UNIQUE KEY `UKfw0c0uav8f7nii1p5pwmcj1ir` (`rfid`),
                         KEY `FKdjw7dinkpc0f01yk4m57uq2u2` (`account_id`),
                         CONSTRAINT `FKdjw7dinkpc0f01yk4m57uq2u2` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;