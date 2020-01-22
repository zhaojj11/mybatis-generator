package com.pyjava.plugin.enumeration;

import com.pyjava.plugin.enumeration.JavaType;

/**
 * @author zhaojj9
 * @description jdbc类型枚举类
 * @date 2019-12-17 14:01
 */
public enum JdbcType {
    /** jdbc 数据类型 */
    CHAR("CHAR", JavaType.String),
    VARCHAR("VARCHAR", JavaType.String),
    NUMERIC("NUMERIC", JavaType.BigDecimal),
    DECIMAL("DECIMAL", JavaType.BigDecimal),
    BIT("BIT", JavaType.Boolean),
    TINYINT("TINYINT", JavaType.Byte),
    SMALLINT("SMALLINT", JavaType.Short),
    INTEGER("INT", JavaType.Integer),
    BIGINT("BIGINT", JavaType.Long),
    REAL("REAL", JavaType.Float),
    FLOAT("FLOAT", JavaType.Float),
    DOUBLE("DOUBLE", JavaType.Double),
    BINARY("BINARY", JavaType.ByteArray),
    VARBINARY("VARBINARY", JavaType.ByteArray),
    DATE("DATE", JavaType.Date),
    TIME("TIME", JavaType.Date),
    TIMESTAMP("TIMESTAMP", JavaType.Date),
    CLOB("LONGTEXT", JavaType.String),
    BLOB("BLOB", JavaType.ByteArray),
    ;
    private String typeName;
    private JavaType javaType;

    JdbcType(String typeName, JavaType javaType){
        this.javaType = javaType;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }
}

