package com.pyjava.plugin.util;

import com.pyjava.plugin.meta.database.ColumnMeta;
import com.pyjava.plugin.enumeration.JdbcType;
import com.pyjava.plugin.meta.database.TableMeta;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

import static com.pyjava.plugin.config.Constant.*;

/**
 * @author zhaojj9
 * @description 类型工具
 * @date 2019-12-17 09:39
 */
public class TypeUtil {
    /**
     * <h1>功能描述</h1>
     * <p>
     * 根据字段元数据获取对应的jdbc类型
     * </p>
     *
     * @param columnMeta 字段元数据
     * @return jdbcType
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
     * <p>
     * 根据字段元数据获取对应的java类型
     * </p>
     *
     * @param columnMeta 字段元数据
     * @return javaType
     */
    public static String getJavaType(ColumnMeta columnMeta) throws MojoExecutionException {
        return getJdbcType(columnMeta).getJavaType().getType();
    }

    public static String getClassName(TableMeta tableMeta, String prefix) {
        if (tableMeta.getTableName() == null) {
            return "";
        }
        // "" 表前缀
        StringBuffer className = new StringBuffer();
        if (StringUtils.startsWith(tableMeta.getTableName(), prefix)) {
            String newName = StringUtils.substring(tableMeta.getTableName(), prefix.length());
            String[] names = StringUtils.split(newName.toLowerCase(), "_");
            for (String name : names) {
                className.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
            }
        } else {
            System.out.println("==不支持的表前缀==");
        }
        return className.toString();
    }
}
