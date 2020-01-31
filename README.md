# mybatis-generator

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
[![license-MIT](https://img.shields.io/github/license/sandorZHAO/mybatis-generator)](https://github.com/sandorZHAO/mybatis-generator/LICENSE)
![license-MIT](https://img.shields.io/github/last-commit/sandorZHAO/mybatis-generator)
[![Build Status](https://travis-ci.com/sandorZHAO/mybatis-generator.svg?branch=master)](https://travis-ci.com/sandorZHAO/mybatis-generator)
[![codecov](https://codecov.io/gh/sandorZHAO/mybatis-generator/branch/master/graph/badge.svg)](https://codecov.io/gh/sandorZHAO/mybatis-generator)

该插件可作为mybatis项目的代码生成器, 对lombok提供了部分支持。

可以生成mybatis所需要的**MODEL**, **MAPPER** and **XML**文件（暂不支持provider）。

[README In ENGLISH](README.en.md)

## 目录

- [背景](#背景)
- [安装](#安装)
- [使用说明](#使用说明)
- [维护者](#维护者)
- [贡献方式](#贡献方式)
- [相关仓库](#相关仓库)
- [使用许可](#使用许可)

## 背景

mybatis原生的生产器有许多功能上的缺失，以及冗余

1. lombok的支持
2. service层简单方法的生成
3. 测试方法的生成
等等

## 安装

1. 通过本地install的方式

2. 通过pom依赖引入

## 使用说明

建议使用 **IDEA** 进行开发

1. 创建基础项目

    1. 创建Spring Initializr项目
    2. 勾选如下插件
        - Lombok
        - Spring Web
        - Mybatis Framework
        - MySQL Driver
        
2. 添加pom依赖

    ```xml
   <project>
       <build>
           <plugins>
               <plugin>
                  <groupId>com.pyjava.plugin</groupId>
                  <artifactId>mybatis-generator</artifactId>
                  <version>1.0.0</version>
                  <configuration>
                      <debug>true</debug>
                  </configuration>
                  <dependencies>
                      <dependency>
                          <groupId>mysql</groupId>
                          <artifactId>mysql-connector-java</artifactId>
                          <version>5.1.6</version>
                      </dependency>
                  </dependencies>
               </plugin>
           </plugins>
       </build>
   </project>
    ```
   
3. 添加yml配置信息
    
    ```yml
    spring:
      http:
        encoding:
          charset: utf-8
          enabled: true
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&connectTimeout=3000&socketTimeout=1000&&serverTimezone=UTC
        username: root
        password: zjj123
        # HKCP
        hikari:
          auto-commit: true
          connection-test-query: SELECT 1
          connection-timeout: 30000
          idle-timeout: 30000
          max-lifetime: 1800000
          maximum-pool-size: 15
          minimum-idle: 5
          pool-name: DatebookHikariCP
          type: com.zaxxer.hikari.HikariDataSource
    
    #Mybatis
    mybatis:
      config-location: classpath:mybatis/mybatis-config.xml
      mapper-locations: classpath:mybatis/*/*.xml
      type-aliases-package: com.pyjava.demo.domain
    ```
   
4. 添加插件配置信息

    ```properties
    # 数据库驱动名
    driver=com.mysql.jdbc.Driver
    # 数据库连接url
    url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&connectTimeout=3000&socketTimeout=1000&&serverTimezone=UTC
    # 数据库用户名
    username=root
    # 数据库密码
    password=zjj123
    # 待生成表名
    tables=user,user_flow,user_score,score_flow,asd,zxc,test1
    # xml生成位置,是resources的子目录
    xml.path=mybatis/user/
    # model的包名
    model.package=com.pyjava.demo.domain
    # dao文件包名
    dao.package=com.pyjava.demo.dao
    # service包名
    service.package=com.pyjava.demo.service
    # 作者信息
    author=zhaojj9
    ```
   
5. 在test数据库中生成数据表

    ```sql
    SET NAMES utf8mb4;
    SET FOREIGN_KEY_CHECKS = 0;
    
    -- ----------------------------
    -- Table structure for score_flow
    -- ----------------------------
    DROP TABLE IF EXISTS `score_flow`;
    CREATE TABLE `score_flow`  (
      `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
      `score` bigint(19) UNSIGNED NOT NULL COMMENT '用户积分流水',
      `user_id` int(11) UNSIGNED NOT NULL COMMENT '用户主键id',
      `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Table structure for test
    -- ----------------------------
    DROP TABLE IF EXISTS `test`;
    CREATE TABLE `test`  (
      `col_1` bigint(20) NOT NULL AUTO_INCREMENT,
      `col_2` binary(0) NULL DEFAULT NULL,
      `col_3` bit(1) NULL DEFAULT NULL,
      `col_4` blob NULL,
      `col_5` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
      `col_6` date NULL DEFAULT NULL,
      `col_7` datetime(0) NULL DEFAULT NULL,
      `col_8` timestamp(0) NULL DEFAULT NULL,
      `col_9` float NULL DEFAULT NULL,
      `col_10` double NULL DEFAULT NULL,
      `col_11` int(11) NULL DEFAULT NULL,
      `col_12` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
      PRIMARY KEY (`col_1`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Table structure for test1
    -- ----------------------------
    DROP TABLE IF EXISTS `test1`;
    CREATE TABLE `test1`  (
      `col_char` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'char',
      `col_char_default` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'char default',
      `col_varchar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'varchar',
      `col_int` int(11) NOT NULL COMMENT 'int',
      `col_bigint` bigint(20) NULL DEFAULT NULL COMMENT 'bigint',
      `col_float` float NULL DEFAULT NULL COMMENT 'float',
      `col_double` double NULL DEFAULT NULL COMMENT 'double',
      `col_date` date NULL DEFAULT NULL COMMENT 'date',
      `col_datetime` datetime(0) NULL DEFAULT NULL COMMENT 'datetime',
      `col_timestamp` timestamp(0) NULL DEFAULT NULL COMMENT 'timestamp',
      `col_clob` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'clob',
      PRIMARY KEY (`col_int`) USING BTREE,
      UNIQUE INDEX `col_char`(`col_char`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Table structure for user
    -- ----------------------------
    DROP TABLE IF EXISTS `user`;
    CREATE TABLE `user`  (
      `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
      `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Table structure for user_score
    -- ----------------------------
    DROP TABLE IF EXISTS `user_score`;
    CREATE TABLE `user_score`  (
      `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
      `user_score` bigint(19) UNSIGNED NOT NULL COMMENT '用户积分',
      `user_id` int(11) UNSIGNED NOT NULL COMMENT '用户主键id',
      `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
    
    SET FOREIGN_KEY_CHECKS = 1;
    ```

6. 运行插件
    
    在maven中找到 **Plugins** 下的 *mybatis-generator:mybatis-generator* 插件并运行


## 维护者

[@sandorZHAO](https://github.com/sandorZHAO)

## 贡献方式

PRs accepted.


## 相关仓库

[standard-readme](https://github.com/RichardLitt/standard-readme) - 标准 Readme 规范

## 使用许可

[MIT](LICENSE) © 2019 sandorZHAO