package com.pyjava.plugin.util;

import com.pyjava.plugin.config.GlobalConfiguration;
import com.pyjava.plugin.meta.database.ColumnMeta;
import com.pyjava.plugin.meta.database.TableMeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhaojj9
 * @description
 * @date 2019-12-13 16:15
 */
public class DbUtil {
    private static Connection connection;

    public static Connection getConnection(GlobalConfiguration configuration) throws SQLException, ClassNotFoundException {
        if (connection != null) {
            return connection;
        }

        Class.forName(configuration.getDriver());
        connection = DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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


    private static void getTables(GlobalConfiguration configuration) {
        List<TableMeta> list = configuration.getTables();
        if (list != null) {
            list.forEach(element -> {
                getTable(configuration, element);
            });
        }
        DbUtil.close();
    }

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
