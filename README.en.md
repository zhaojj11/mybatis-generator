# generator

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
[![license-MIT](https://img.shields.io/github/license/sandorZHAO/mybatis-generator)](https://github.com/sandorZHAO/mybatis-generator/LICENSE)
![license-MIT](https://img.shields.io/github/last-commit/sandorZHAO/mybatis-generator)
[![Build Status](https://travis-ci.com/sandorZHAO/mybatis-generator.svg?branch=master)](https://travis-ci.com/sandorZHAO/mybatis-generator)
[![codecov](https://codecov.io/gh/sandorZHAO/mybatis-generator/branch/master/graph/badge.svg)](https://codecov.io/gh/sandorZHAO/mybatis-generator)


The plugin can be used as the code generator of mybatis project, which provides some support for Lombok.

The **MODEL**, **MAPPER** and **XML** files required by mybatis can be generated (*PROVIDER is not supported temporarily*).

## Table of Contents

- [Background](#background)
- [Install](#install)
- [Usage](#usage)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [Related Repo](#Related-Repo)
- [License](#license)

## Background

## Install

It should be noted that * 1.0.0-snapshot * does not provide direct access through POM dependency. Please clone this project first and package it locally

1. clone this project

    ```bash
    cd /my-path
    git clone https://github.com/sandorZHAO/mybatis-generator
    ```

2. maven install
 
    Please package it locally

## Usage

1. add pom plugin
   
    ```xml
    <plugin>
       <groupId>com.pyjava.plugin</groupId>
       <artifactId>mybatis-generator</artifactId>
       <version>1.0.0-SNAPSHOT</version>
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

3. run the mybatis-generator plugin

    Idea is recommended

## Maintainers

[@sandorZHAO](https://github.com/sandorZHAO)

## Contributing

PRs accepted.

## Related Repo

[standard-readme](https://github.com/RichardLitt/standard-readme) - standard-readme

## License

[MIT](LICENSE) © 2019 sandorZHAO
