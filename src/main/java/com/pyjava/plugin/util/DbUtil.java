package com.pyjava.plugin.util;

import com.pyjava.plugin.config.GlobalConfiguration;
import com.pyjava.plugin.meta.database.ColumnMeta;
import com.pyjava.plugin.meta.database.TableMeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据库工具
 *
 * @author zhaojj9
 * @since 1.0.0
 */
public class DbUtil {
    private static Connection connection;

    /**
     * 通过{@link GlobalConfiguration}获取连接
     *
     * @param configuration 全局配置信息
     * @return 获取的正常连接
     * @throws SQLException           getConnection时错误抛出异常
     * @throws ClassNotFoundException forName类没有找到抛出异常
     */
    public static Connection getConnection(GlobalConfiguration configuration) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            return connection;
        }

        Class.forName(configuration.getDriver());
        connection = DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
        return connection;
    }

    /**
     * 关闭连接
     */
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取表信息
     * 先查询表名，表注释
     * 再根据对应表查询所有字段
     * @param configuration 全局配置信息
     */
    public static void getTable(GlobalConfiguration configuration) {
        String databaseName = configuration.getDatabase();
        String sql =
                "SELECT TABLE_NAME ,TABLE_COMMENT " +
                        "FROM information_schema.tables " +
                        "WHERE table_schema = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection(configuration);
            ps = connection.prepareStatement(sql);
            ps.setString(1, databaseName);
            rs = ps.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String tableComment = rs.getString("TABLE_COMMENT");
                if (configuration.getTableNames().contains(tableName)) {
                    TableMeta tm = new TableMeta();
                    tm.setSchemaName(databaseName);
                    tm.setTableName(tableName);
                    tm.setComment(tableComment);
                    configuration.getTables().add(tm);
                }
            }
            getTables(configuration);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询所有表的信息
     *
     * @param configuration
     */
    private static void getTables(GlobalConfiguration configuration) {
        List<TableMeta> list = configuration.getTables();
        if (list != null) {
            list.forEach(element -> {
                getTable(configuration, element);
            });
        }
        DbUtil.close();
    }

    /**
     * @param configuration
     * @param tableMeta
     */
    private static void getTable(GlobalConfiguration configuration, TableMeta tableMeta) {
        String databaseName = StringUtil.getDatabaseName(configuration.getUrl());

        List<ColumnMeta> list = new ArrayList<ColumnMeta>();
        String sql = "SELECT COLUMN_NAME ,ORDINAL_POSITION ,IS_NULLABLE ,COLUMN_DEFAULT ,COLUMN_TYPE ,COLUMN_KEY ,EXTRA ,COLUMN_COMMENT " +
                "FROM information_schema.columns " +
                "WHERE table_schema = ? AND table_name = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection(configuration);
            ps = connection.prepareCall(sql);
            ps.setString(1, databaseName);
            ps.setString(2, tableMeta.getTableName());
            rs = ps.executeQuery();
            while (rs.next()) {
                ColumnMeta fm = new ColumnMeta();

                fm.setColumnName(rs.getString("COLUMN_NAME"));
                fm.setOrdinalPosition(rs.getString("ORDINAL_POSITION"));
                fm.setIsNullable(rs.getString("IS_NULLABLE"));
                fm.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
                fm.setColumnType(rs.getString("COLUMN_TYPE"));
                fm.setColumnKey(rs.getString("COLUMN_KEY"));
                fm.setExtra(rs.getString("EXTRA"));
                fm.setColumnComment(rs.getString("COLUMN_COMMENT"));
                list.add(fm);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        tableMeta.setColumns(list);
    }

}
