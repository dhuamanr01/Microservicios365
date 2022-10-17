/*
 Navicat Premium Data Transfer

 Source Server         : BDPOSTGRES
 Source Server Type    : PostgreSQL
 Source Server Version : 120007
 Source Host           : localhost:5432
 Source Catalog        : ms365Postgre
 Source Schema         : ms_proyecto

 Target Server Type    : PostgreSQL
 Target Server Version : 120007
 File Encoding         : 65001

 Date: 16/10/2022 22:31:01
*/


-- ----------------------------
-- Sequence structure for archivos_proyectos_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "ms_proyecto"."archivos_proyectos_id_seq";
CREATE SEQUENCE "ms_proyecto"."archivos_proyectos_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for proyectos_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "ms_proyecto"."proyectos_id_seq";
CREATE SEQUENCE "ms_proyecto"."proyectos_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for archivos_proyectos
-- ----------------------------
DROP TABLE IF EXISTS "ms_proyecto"."archivos_proyectos";
CREATE TABLE "ms_proyecto"."archivos_proyectos" (
  "id" int4 NOT NULL DEFAULT nextval('"ms_proyecto".archivos_proyectos_id_seq'::regclass),
  "created_at" timestamp(6),
  "nombre" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "ruta" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "updated_at" timestamp(6),
  "proyecto_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of archivos_proyectos
-- ----------------------------
INSERT INTO "ms_proyecto"."archivos_proyectos" VALUES (1, '2022-09-04 12:39:20.449', 'AUDITORIA DE SISTEMA DE GESTION SST.PDF', 'Auditoria de Sistema de Gestion SST.pdf', NULL, 1);

-- ----------------------------
-- Table structure for proyectos
-- ----------------------------
DROP TABLE IF EXISTS "ms_proyecto"."proyectos";
CREATE TABLE "ms_proyecto"."proyectos" (
  "id" int4 NOT NULL DEFAULT nextval('"ms_proyecto".proyectos_id_seq'::regclass),
  "created_at" timestamp(6),
  "descripcion" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "estado" bool NOT NULL,
  "fecha_expiracion" timestamp(6),
  "proyecto" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "updated_at" timestamp(6),
  "usuario_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of proyectos
-- ----------------------------
INSERT INTO "ms_proyecto"."proyectos" VALUES (1, '2022-09-04 12:38:25.575', 'Auditoria de Sistema de Gestion SST.', 't', '2023-03-03 12:38:25.573', 'Auditoria de Sistema de Gestion SST.', NULL, 1);
INSERT INTO "ms_proyecto"."proyectos" VALUES (2, '2022-09-04 12:39:00.915', 'Higiene Industrial', 't', '2023-04-10 12:39:00.915', 'Higiene Industrial', NULL, 2);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "ms_proyecto"."archivos_proyectos_id_seq"
OWNED BY "ms_proyecto"."archivos_proyectos"."id";
SELECT setval('"ms_proyecto"."archivos_proyectos_id_seq"', 15, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "ms_proyecto"."proyectos_id_seq"
OWNED BY "ms_proyecto"."proyectos"."id";
SELECT setval('"ms_proyecto"."proyectos_id_seq"', 9, true);

-- ----------------------------
-- Indexes structure for table archivos_proyectos
-- ----------------------------
CREATE INDEX "archivos_proyectos_proyecto_id_idx" ON "ms_proyecto"."archivos_proyectos" USING btree (
  "proyecto_id" "pg_catalog"."int4_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table archivos_proyectos
-- ----------------------------
ALTER TABLE "ms_proyecto"."archivos_proyectos" ADD CONSTRAINT "uk_4s0wu8u6o0ufvsniv68k0r2sc" UNIQUE ("nombre");

-- ----------------------------
-- Primary Key structure for table archivos_proyectos
-- ----------------------------
ALTER TABLE "ms_proyecto"."archivos_proyectos" ADD CONSTRAINT "archivos_proyectos_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table proyectos
-- ----------------------------
CREATE INDEX "proyectos_usuario_id_idx" ON "ms_proyecto"."proyectos" USING btree (
  "usuario_id" "pg_catalog"."int4_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table proyectos
-- ----------------------------
ALTER TABLE "ms_proyecto"."proyectos" ADD CONSTRAINT "proyectos_pkey" PRIMARY KEY ("id");
