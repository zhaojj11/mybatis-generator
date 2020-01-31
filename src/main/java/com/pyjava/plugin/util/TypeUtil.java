package com.pyjava.plugin.util;

import com.pyjava.plugin.meta.database.ColumnMeta;
import com.pyjava.plugin.enumeration.JdbcType;
import com.pyjava.plugin.meta.database.TableMeta;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

import static com.pyjava.plugin.config.Constant.*;

/**
 * 类型工具
 *
 * @author zhaojj9
 * @since 1.0.0
 */
public class TypeUtil {
    /**
     * 根据字段元数据获取对应的jdbc类型
     *
     * @param columnMeta 字段元数据
     * @return jdbcType
     * @throws MojoExecutionException mojo处理异常
     */
    public static JdbcType getJdbcType(ColumnMeta columnMeta) throws MojoExecutionException {
        String type = columnMeta.getColumnType().toLowerCase();
        JdbcType jdbcType = null;

        if (StringUtils.startsWith(type, INT)) {
            // INTEGER
            jdbcType = JdbcType.INTEGER;
        } else if (StringUtils.startsWith(type, BIGINT)) {
            // BIGINT
            jdbcType = JdbcType.BIGINT;
        } else if (StringUtils.startsWith(type, DOUBLE)) {
            // DOUBLE
            jdbcType = JdbcType.DOUBLE;
        } else if (StringUtils.startsWith(type, FLOAT)) {
            // FLOAT
            jdbcType = JdbcType.FLOAT;
        } else if (StringUtils.startsWith(type, VARCHAR)) {
            // VARCHAR
            jdbcType = JdbcType.VARCHAR;
        } else if (StringUtils.startsWith(type, CHAR)) {
            // CHAR
            jdbcType = JdbcType.CHAR;
        } else if (StringUtils.startsWith(type, TEXT)) {
            // CLOB
            jdbcType = JdbcType.CLOB;
        } else if (StringUtils.startsWith(type, DATE)) {
            // DATE
            jdbcType = JdbcType.DATE;
        } else if (StringUtils.startsWith(type, DATETIME)) {
            // TIMESTAMP
            jdbcType = JdbcType.DATE;
        } else if (StringUtils.startsWith(type, TIMESTAMP)) {
            // TIMESTAMP
            jdbcType = JdbcType.DATE;
        } else {
            throw new MojoExecutionException("==类型[" + type + "]解析尚不支持==");
        }
        return jdbcType;
    }

    /**
     * 根据字段元数据获取对应的java类型
     *
     * @param columnMeta 字段元数据
     * @return javaType
     * @throws MojoExecutionException mojo处理异常
     */
    public static String getJavaType(ColumnMeta columnMeta) throws MojoExecutionException {
        return getJdbcType(columnMeta).getJavaType().getType();
    }

    /**
     * 获取类名
     * 去除前缀后，将剩余字符串按照下划线分割，进行首字母大写拼接成字符串
     * @param tableMeta 表元数据
     * @param prefix 前缀
     * @return className
     */
    public static String getClassName(TableMeta tableMeta, String prefix) {
        if (tableMeta.getTableName() == null) {
            return "";
        }
        // "" 表前缀
        StringBuilder className = new StringBuilder();
        if (StringUtils.startsWith(tableMeta.getTableName(), prefix)) {
            String newName = StringUtils.substring(tableMeta.getTableName(), prefix.length());
            String[] names = StringUtils.split(newName.toLowerCase(), "_");
            for (String name : names) {
                className.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
            }
        } else {
            String[] names = StringUtils.split(tableMeta.getTableName().toLowerCase(), "_");
            for (String name : names) {
                className.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
            }
        }
        return className.toString();
    }
}
