package com.pyjava.plugin.util;


import org.junit.Test;

public class StringUtilTest {

    @Test
    public void getCamelString() {
        String test = "test_test";
        assert StringUtil.getSmallCamelString(test).equals("testTest");
    }

    @Test
    public void getFirstUpperCamelString() {
        String test = "test_test";
        assert StringUtil.getBigCamelString(test).equals("TestTest");
    }

    @Test
    public void getDatabaseName() {
        String url1 = "jdbc:mysql://localhost:3306/test123?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&connectTimeout=3000&socketTimeout=1000&&serverTimezone=UTC";
        String url2 = "jdbc:mysql://localhost:3306/test123";

        assert StringUtil.getDatabaseName(url1).equals("test123");
        assert StringUtil.getDatabaseName(url2).equals("test123");

    }

    @Test
    public void getPathFromPoint() {
        String domainPackage = "com.example.generatortest.domain";
        String test = StringUtil.getPathFromPoint(domainPackage);
        assert test.equals("/com/example/generatortest/domain");
    }
}