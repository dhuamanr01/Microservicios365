/*
 Navicat MySQL Data Transfer

 Source Server         : lnvwinqa_5.6.35_3310
 Source Server Type    : MySQL
 Source Server Version : 50635
 Source Host           : 192.168.1.76:3310
 Source Schema         : ms365_usuario

 Target Server Type    : MySQL
 Target Server Version : 50635
 File Encoding         : 65001

 Date: 08/09/2021 13:54:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_servicio
-- ----------------------------
DROP TABLE IF EXISTS `auth_servicio`;
CREATE TABLE `auth_servicio`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  `method` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  `orden` int(9) NOT NULL,
  `url` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 461 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of auth_servicio
-- ----------------------------
INSERT INTO `auth_servicio` VALUES (100, 'POST', 1, '/login/**');
INSERT INTO `auth_servicio` VALUES (200, 'GET', 1, '/api/usuario/list');
INSERT INTO `auth_servicio` VALUES (201, 'GET', 2, '/api/usuario/page/**');
INSERT INTO `auth_servicio` VALUES (202, 'GET', 3, '/api/usuario/findbyid/**');
INSERT INTO `auth_servicio` VALUES (220, 'POST', 2, '/api/usuario');
INSERT INTO `auth_servicio` VALUES (240, 'PUT', 1, '/api/usuario');
INSERT INTO `auth_servicio` VALUES (260, 'DELETE', 1, '/api/usuario/**');
INSERT INTO `auth_servicio` VALUES (300, 'GET', 5, '/api/proyecto/**');
INSERT INTO `auth_servicio` VALUES (320, 'POST', 4, '/api/proyecto');
INSERT INTO `auth_servicio` VALUES (340, 'PUT', 3, '/api/proyecto');
INSERT INTO `auth_servicio` VALUES (360, 'DELETE', 3, '/api/proyecto/**');
INSERT INTO `auth_servicio` VALUES (400, 'GET', 4, '/api/proyecto/archivos/**');
INSERT INTO `auth_servicio` VALUES (420, 'POST', 3, '/api/proyecto/archivos/**');
INSERT INTO `auth_servicio` VALUES (440, 'PUT', 2, '/api/proyecto/archivos/**');
INSERT INTO `auth_servicio` VALUES (460, 'DELETE', 2, '/api/proyecto/archivos/**');

-- ----------------------------
-- Table structure for auth_servicio_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_servicio_role`;
CREATE TABLE `auth_servicio_role`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  `servicio_id` int(9) UNSIGNED NOT NULL,
  `role_id` int(9) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `servicio_id`(`servicio_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `auth_servicio_role_ibfk_1` FOREIGN KEY (`servicio_id`) REFERENCES `auth_servicio` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `auth_servicio_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of auth_servicio_role
-- ----------------------------
INSERT INTO `auth_servicio_role` VALUES (1, 100, NULL);
INSERT INTO `auth_servicio_role` VALUES (2, 200, 1);
INSERT INTO `auth_servicio_role` VALUES (3, 201, 1);
INSERT INTO `auth_servicio_role` VALUES (4, 202, 1);
INSERT INTO `auth_servicio_role` VALUES (5, 220, 1);
INSERT INTO `auth_servicio_role` VALUES (6, 240, 1);
INSERT INTO `auth_servicio_role` VALUES (7, 260, 1);
INSERT INTO `auth_servicio_role` VALUES (8, 200, 2);
INSERT INTO `auth_servicio_role` VALUES (9, 201, 2);
INSERT INTO `auth_servicio_role` VALUES (10, 202, 2);
INSERT INTO `auth_servicio_role` VALUES (11, 220, 2);
INSERT INTO `auth_servicio_role` VALUES (12, 240, 2);
INSERT INTO `auth_servicio_role` VALUES (13, 260, 2);
INSERT INTO `auth_servicio_role` VALUES (14, 300, 1);
INSERT INTO `auth_servicio_role` VALUES (15, 320, 1);
INSERT INTO `auth_servicio_role` VALUES (16, 340, 1);
INSERT INTO `auth_servicio_role` VALUES (17, 360, 1);
INSERT INTO `auth_servicio_role` VALUES (18, 300, 2);
INSERT INTO `auth_servicio_role` VALUES (19, 320, 2);
INSERT INTO `auth_servicio_role` VALUES (20, 340, 2);
INSERT INTO `auth_servicio_role` VALUES (21, 360, 2);
INSERT INTO `auth_servicio_role` VALUES (22, 400, 1);
INSERT INTO `auth_servicio_role` VALUES (23, 420, 1);
INSERT INTO `auth_servicio_role` VALUES (24, 440, 1);
INSERT INTO `auth_servicio_role` VALUES (25, 460, 1);
INSERT INTO `auth_servicio_role` VALUES (26, 400, 2);
INSERT INTO `auth_servicio_role` VALUES (27, 420, 2);
INSERT INTO `auth_servicio_role` VALUES (28, 440, 2);
INSERT INTO `auth_servicio_role` VALUES (29, 460, 2);

-- ----------------------------
-- Table structure for clientes
-- ----------------------------
DROP TABLE IF EXISTS `clientes`;
CREATE TABLE `clientes`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombres` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `direccion` text CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `telefono1` varchar(12) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `telefono2` varchar(12) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `email` varchar(60) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `pagina_web` varchar(60) CHARACTER SET utf8 COLLATE utf8_spanish_ci NULL DEFAULT NULL,
  `usuario_id` int(9) UNSIGNED NOT NULL,
  `tipo_documento_id` int(9) UNSIGNED NOT NULL,
  `numero_doc` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `fecha_registro` date NOT NULL,
  `fecha_modificacion` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `usuario_id`(`usuario_id`) USING BTREE,
  INDEX `tipo_documento_id`(`tipo_documento_id`) USING BTREE,
  CONSTRAINT `clientes_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `clientes_ibfk_2` FOREIGN KEY (`tipo_documento_id`) REFERENCES `tipos_documentos` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clientes
-- ----------------------------
INSERT INTO `clientes` VALUES (1, 'Servi Vega E.I.R.L.', NULL, 'Mz H Lt 9 Pj 2 De Junio', '043343714', '043316589', 'servivega2002@gmail.com', 'http://www.servivegaeirl.com', 6, 2, '20100003351', '2020-03-04', '2020-03-04');
INSERT INTO `clientes` VALUES (2, 'Metal Mecanica Mariategui S.A.C.', NULL, 'Jr Moquegua Nro 482 Zi Miraflores', '043352099', NULL, 'metalmariategui@hotmail.com', NULL, 7, 2, '20100003352', '2020-03-04', '2020-03-04');
INSERT INTO `clientes` VALUES (3, 'Comet S.R.L', NULL, 'Av. Brasil A-30 Urb. Los Álamos Nuevo Chimbote - Ancash - Perú', '043318308', NULL, 'info@comet.com.pe', 'http://www.comet.com.pe', 8, 2, '20100003353', '2020-03-04', '2020-03-04');
INSERT INTO `clientes` VALUES (4, 'Ingeniería, Fabricación y Montaje SAC', '', 'Calle Chiclayo N° 157 Dpto. 401, Miraflores-Chimbote', '043316688', '', 'informes@ifm.com.pe', 'http://www.ifm.com.pe', 8, 2, '20100003354', '2020-03-04', '2020-06-15');
INSERT INTO `clientes` VALUES (6, 'TASA', NULL, 'Av. Los Pescadores s/n, Zona Industrial 27 de Octubre, Chimbote.', '043352160', '016111400', 'comunicaciones@tasa.com.pe', 'https://www.tasa.com.pe', 4, 2, '20100003355', '2020-03-04', '2020-03-04');
INSERT INTO `clientes` VALUES (7, 'Corporación Pesquera Inca S.A.C.', '', 'Av. Industrial 1240, Chimbote', '043365878', '012134000', 'sales@copeinca.com.pe', 'http://www.copeinca.com', 1, 2, '20100003356', '2020-04-06', '2020-04-06');
INSERT INTO `clientes` VALUES (8, 'Bodega DANIEL', '', 'jr huanuco 230', '316589', '253689', 'dhuamanr24@gmail.com', 'google.com', 3, 2, '10724839237', '2020-04-06', '2020-04-06');
INSERT INTO `clientes` VALUES (9, 'LUGUENSI E.I.R.L', '', 'CHIMBOTE', '253689', '254178', 'micorreo', '', 2, 2, '12345689102', '2020-04-06', '2020-04-06');
INSERT INTO `clientes` VALUES (10, 'Servicios Generales Rishiro', '', 'H.U.P Villa Mercedes - Mz. C Lt. 18 Nuevo Chimbote', '043310445', '', 'equenhuav@gmail.com', 'google.com', 11, 1, '58692314', '2020-04-11', '2020-04-11');
INSERT INTO `clientes` VALUES (14, 'Hayduk coishco', '', 'Av Sta Marina 628', '457812', '124578', 'hayduk@gmail.com', '', 7, 1, '72483923', '2020-06-15', NULL);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NULL DEFAULT NULL,
  `auth` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'ADMIN', '2020-03-04', '2020-03-04', 'ROLE_ADMIN');
INSERT INTO `roles` VALUES (2, 'USER', '2020-03-04', '2020-03-04', 'ROLE_USER');

-- ----------------------------
-- Table structure for tipos_documentos
-- ----------------------------
DROP TABLE IF EXISTS `tipos_documentos`;
CREATE TABLE `tipos_documentos`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(20) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tipos_documentos
-- ----------------------------
INSERT INTO `tipos_documentos` VALUES (1, 'DNI', '2020-03-04', '2020-03-04');
INSERT INTO `tipos_documentos` VALUES (2, 'RUC', '2020-03-04', '2020-03-04');

-- ----------------------------
-- Table structure for usuarios
-- ----------------------------
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `rol_id` int(9) UNSIGNED NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `rol_id`(`rol_id`) USING BTREE,
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_spanish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of usuarios
-- ----------------------------
INSERT INTO `usuarios` VALUES (1, 'mportilla2020', '12345', 1, 1, '2020-02-23', '2020-02-23');
INSERT INTO `usuarios` VALUES (2, 'copeinca2020', '1234', 1, 2, '2020-02-23', NULL);
INSERT INTO `usuarios` VALUES (3, 'jpsima2020', '01234', 1, 2, '2020-02-23', '2020-04-06');
INSERT INTO `usuarios` VALUES (4, 'mqtasa2020', '1234', 1, 2, '2020-02-23', '2020-06-18');
INSERT INTO `usuarios` VALUES (6, 'comet0120', '1234', 0, 2, '2020-04-02', '2020-04-02');
INSERT INTO `usuarios` VALUES (7, 'pesinca2018', '1234', 1, 2, '2020-04-06', '2020-04-06');
INSERT INTO `usuarios` VALUES (8, 'hayduk19', '1234', 1, 2, '2020-04-06', '2020-04-06');
INSERT INTO `usuarios` VALUES (11, 'equenhua', '1234', 1, 1, '2020-04-11', '2020-07-19');
INSERT INTO `usuarios` VALUES (14, 'mfernando', '1234', 1, 2, '2020-06-19', '2020-06-19');
INSERT INTO `usuarios` VALUES (15, 'usuario1', '01234', 1, 2, '2020-07-07', '2020-07-07');
INSERT INTO `usuarios` VALUES (22, 'usuario190720', '1234', 1, 2, '2020-07-19', '2020-07-19');
INSERT INTO `usuarios` VALUES (23, 'Test0X', '555', 0, 2, '2021-09-02', '2021-09-02');
INSERT INTO `usuarios` VALUES (24, 'mfernandox1', '999', 1, 1, '2021-09-08', NULL);
INSERT INTO `usuarios` VALUES (25, 'mfernandox2', '999', 1, 1, '2021-09-08', NULL);

SET FOREIGN_KEY_CHECKS = 1;
