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

需要注意的是，*1.0.0-SNAPSHOT*暂不提供通过pom依赖方式直接获取，请先克隆本项目，通过本地方式打包

1. 克隆本项目

    ```bash
    cd /my-path
    git clone https://github.com/sandorZHAO/mybatis-generator
    ```

2. maven install
 
    请自行install本项目

## 使用说明

1. 添加pom依赖

    ```xml
    <plugin>
       <groupId>com.pyjava.plugin</groupId>
       <artifactId>mybatis-generator</artifactId>
       <version>1.0.0-SNAPSHOT</version>
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
    ```
    
2. generator.properties

    ```properties
    driver=com.mysql.jdbc.Driver
    url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&connectTimeout=3000&socketTimeout=1000&&serverTimezone=UTC
    username=root
    password=zjj123
    tables=user,user_flow,user_score,score_flow,asd,zxc
    xml.path=mybatis/user/
    model.package=com.example.generatortest.domain
    dao.package=com.example.generatortest.dao
    service.package=com.example.generatortest.service
    author=zhaojj9
    ```

3. 运行本插件

    建议使用idea

## 维护者

[@sandorZHAO](https://github.com/sandorZHAO)

## 贡献方式

PRs accepted.


## 相关仓库

[standard-readme](https://github.com/RichardLitt/standard-readme) - 标准 Readme 规范

## 使用许可

[MIT](LICENSE) © 2019 sandorZHAO