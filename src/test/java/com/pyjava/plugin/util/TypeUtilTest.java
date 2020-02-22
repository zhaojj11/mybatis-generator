package com.pyjava.plugin.util;

import com.pyjava.plugin.enumeration.JavaType;
import com.pyjava.plugin.enumeration.JdbcType;
import com.pyjava.plugin.meta.database.ColumnMeta;
import com.pyjava.plugin.meta.database.TableMeta;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TypeUtilTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void getJdbcType() throws MojoExecutionException {

        ColumnMeta columnMeta = new ColumnMeta();

        columnMeta.setColumnName("col_int");
        columnMeta.setColumnType("int");
        assert JdbcType.INTEGER == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_bigint");
        columnMeta.setColumnType("bigint");
        assert JdbcType.BIGINT == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_double");
        columnMeta.setColumnType("double");
        assert JdbcType.DOUBLE == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_float");
        columnMeta.setColumnType("float");
        assert JdbcType.FLOAT == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_varchar");
        columnMeta.setColumnType("varchar");
        assert JdbcType.VARCHAR == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_char");
        columnMeta.setColumnType("char");
        assert JdbcType.CHAR == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_text");
        columnMeta.setColumnType("text");
        assert JdbcType.CLOB == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_date");
        columnMeta.setColumnType("date");
        assert JdbcType.DATE == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_datetime");
        columnMeta.setColumnType("datetime");
        assert JdbcType.DATE == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_timestamp");
        columnMeta.setColumnType("timestamp");
        assert JdbcType.DATE == TypeUtil.getJdbcType(columnMeta);

        columnMeta.setColumnName("col_date");
        columnMeta.setColumnType("test");
        exception.expect(MojoExecutionException.class);
        TypeUtil.getJdbcType(columnMeta);
    }

    @Test
    public void getJavaType() throws MojoExecutionException {

        ColumnMeta columnMeta = new ColumnMeta();
        String type = "";

        columnMeta.setColumnName("col_int");
        columnMeta.setColumnType("int");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.Integer.getType());

        columnMeta.setColumnName("col_bigint");
        columnMeta.setColumnType("bigint");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.Long.getType());

        columnMeta.setColumnName("col_double");
        columnMeta.setColumnType("double");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.Double.getType());

        columnMeta.setColumnName("col_float");
        columnMeta.setColumnType("float");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.Float.getType());

        columnMeta.setColumnName("col_varchar");
        columnMeta.setColumnType("varchar");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.String.getType());

        columnMeta.setColumnName("col_char");
        columnMeta.setColumnType("char");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.String.getType());

        columnMeta.setColumnName("col_text");
        columnMeta.setColumnType("text");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.String.getType());

        columnMeta.setColumnName("col_date");
        columnMeta.setColumnType("date");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.Date.getType());

        columnMeta.setColumnName("col_datetime");
        columnMeta.setColumnType("datetime");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.Date.getType());

        columnMeta.setColumnName("col_timestamp");
        columnMeta.setColumnType("timestamp");
        type = TypeUtil.getJavaType(columnMeta);
        assert type.equals(JavaType.Date.getType());

        columnMeta.setColumnName("col_date");
        columnMeta.setColumnType("test");
        exception.expect(MojoExecutionException.class);
        TypeUtil.getJavaType(columnMeta);

    }

    @Test
    public void getClassName() {
        TableMeta tableMeta = new TableMeta();
        String className = "";

        tableMeta.setTableName("test");
        className = TypeUtil.getClassName(tableMeta, "");
        assert className.equals("Test");

        tableMeta.setTableName("asd_test");
        className = TypeUtil.getClassName(tableMeta, "asd");
        assert className.equals("Test");


        tableMeta.setTableName("asd_test");
        className = TypeUtil.getClassName(tableMeta, "as");
        assert className.equals("DTest");

        tableMeta.setTableName("asd_test");
        className = TypeUtil.getClassName(tableMeta, "qwe");
        assert className.equals("AsdTest");
    }
}