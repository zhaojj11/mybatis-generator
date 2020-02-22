package com.pyjava.plugin.util;

import com.pyjava.plugin.config.GlobalConfiguration;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DbUtilTest {


    @Test
    public void connectAndClose() {
//        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&connectTimeout=3000&socketTimeout=1000&&serverTimezone=UTC";
//        String Driver = "com.mysql.jdbc.Driver";
//        String username = "root";
//        String password = "zjj123";
//        GlobalConfiguration configuration = new GlobalConfiguration();
//        configuration.setUrl(url);
//        configuration.setDriver(Driver);
//        configuration.setUsername(username);
//        configuration.setPassword(password);
//        try {
//            DbUtil.getConnection(configuration);
//            DbUtil.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void getTable() {
//        String url = "jdbc:mysql://localhost:3306/generator_database?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&connectTimeout=3000&socketTimeout=1000&&serverTimezone=UTC";
//        String Driver = "com.mysql.jdbc.Driver";
//        String username = "root";
//        String password = "zjj123";
//        GlobalConfiguration configuration = new GlobalConfiguration();
//        configuration.setUrl(url);
//        configuration.setDriver(Driver);
//        configuration.setUsername(username);
//        configuration.setPassword(password);
//        configuration.setDatabase(StringUtil.getDatabaseName(url));
//
//        HashSet<String> tables = new HashSet<>();
//        tables.add("test1");
//        configuration.setTableNames(tables);
//        DbUtil.getTable(configuration);

    }
}