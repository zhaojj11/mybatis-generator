# generator

[![Build Status](https://travis-ci.com/sandorZHAO/mybatis-generator.svg?branch=master)](https://travis-ci.com/sandorZHAO/mybatis-generator)
[![codecov](https://codecov.io/gh/sandorZHAO/mybatis-generator/branch/master/graph/badge.svg)](https://codecov.io/gh/sandorZHAO/mybatis-generator)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.pyjava.plugin/mybatis-generator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.pyjava.plugin/mybatis-generator)
[![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/com.pyjava.plugin/mybatis-generator?server=https%3A%2F%2Foss.sonatype.org)]()
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sandorZHAO_mybatis-generator&metric=alert_status)](https://sonarcloud.io/dashboard?id=sandorZHAO_mybatis-generator)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=sandorZHAO_mybatis-generator&metric=security_rating)](https://sonarcloud.io/dashboard?id=sandorZHAO_mybatis-generator)
[![license-MIT](https://img.shields.io/github/license/sandorZHAO/mybatis-generator)](https://github.com/sandorZHAO/mybatis-generator/LICENSE)
[![license-MIT](https://img.shields.io/github/last-commit/sandorZHAO/mybatis-generator)]()
[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

The plugin can be used as the code generator of mybatis project, which provides some support for Lombok.

The **MODEL**, **MAPPER** and **XML** files required by mybatis can be generated (*PROVIDER is not supported temporarily*).

[中文README](README.md)

## Table of Contents

- [Background](#Background)
- [Function](#function)
- [Install](#install)
- [Usage](#usage)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [Related Repo](#Related-Repo)
- [License](#license)

## Background

mybatis's native producer has many features missing and redundant

1. lombok support
2. Generation of simple methods in the service layer
3. Generation of test methods
and many more

## Function

1. Model layer

    - [x] generates support annotation comments
    - [ ] generates some lombok annotations
        - [x] Data
        - [ ] ToString
        - [ ] Other
    - [ ] Model Object can support inheriting parent classes
    - [ ] Model Objects can support ignoring certain attributes

2. Mapper layer

    - [x] generates Mapper Interface comments, including class and method comments
    - [x] generates Interface annotations, including Mapper and Component annotations
    - [ ] generates Interface custom method name

3. XML layer

    - [x] generates resultMap node
    - [x] generates sql node
    - [x] generates basic methods
    - [ ] generates method name
    - [x] Generate xml code repeatedly to cover the original file (default override)

4. Service layer

    - [x] generates basic methods

5. Tests

    - [ ] generates tests for mapper,service

6. Others

    - [ ] generates Paging query



## Install

1. install locally
2. install through POM

## Usage

It is recommended to use **IDEA**, **Mysql 5.x** for development

1. Create basic project

    1. Create spring initializr project
    2. Check the following plugins
        - Lombok
        - Spring Web
        - Mybatis Framework
        - MySQL Driver

2. add pom plugin
   
    ```xml
   <project>
       <build>
           <plugins>
               <plugin>
                  <groupId>com.pyjava.plugin</groupId>
                  <artifactId>mybatis-generator</artifactId>
                  <version>1.1.2</version>
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

3. add application.yml 
    
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

4. add generator.properties

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
    xml.path=user/
    # model的包名
    model.path=com.pyjava.demo.domain
    # dao文件包名
    dao.path=com.pyjava.demo.dao
    # service包名
    service.path=com.pyjava.demo.service
    # 作者信息
    author=zhaojj9
    ```

5. create some tables in test database

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

6. Running plug-ins

    Find the *mybatis-generator:mybatis-generator* plugin under **plugins** in Maven and run it

## Maintainers

[@sandorZHAO](https://github.com/sandorZHAO)

## Contributing

PRs accepted.

## Related Repo

[standard-readme](https://github.com/RichardLitt/standard-readme) - standard-readme

## License

[MIT](LICENSE) © 2019 sandorZHAO
