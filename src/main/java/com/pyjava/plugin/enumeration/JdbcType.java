package com.pyjava.plugin.enumeration;

/**
 * jdbc类型枚举类
 *
 * @author zhaojj9
 * @since 1.0.0
 */
public enum JdbcType {
    /**
     * CHAR
     */
    CHAR("CHAR", JavaType.String),
    /**
     * VARCHAR
     */
    VARCHAR("VARCHAR", JavaType.String),
    /**
     * NUMERIC
     */
    NUMERIC("NUMERIC", JavaType.BigDecimal),
    /**
     * DECIMAL
     */
    DECIMAL("DECIMAL", JavaType.BigDecimal),
    /**
     * BIT
     */
    BIT("BIT", JavaType.Boolean),
    /**
     * TINYINT
     */
    TINYINT("TINYINT", JavaType.Byte),
    /**
     * SMALLINT
     */
    SMALLINT("SMALLINT", JavaType.Short),
    /**
     * INT
     */
    INTEGER("INTEGER", JavaType.Integer),
    /**
     * BIGINT
     */
    BIGINT("BIGINT", JavaType.Long),
    /**
     * REAL
     */
    REAL("REAL", JavaType.Float),
    /**
     * FLOAT
     */
    FLOAT("FLOAT", JavaType.Float),
    /**
     * DOUBLE
     */
    DOUBLE("DOUBLE", JavaType.Double),
    /**
     * BINARY
     */
    BINARY("BINARY", JavaType.ByteArray),
    /**
     * VARBINARY
     */
    VARBINARY("VARBINARY", JavaType.ByteArray),
    /**
     * DATE
     */
    DATE("DATE", JavaType.Date),
    /**
     * TIME
     */
    TIME("TIME", JavaType.Date),
    /**
     * TIMESTAMP
     */
    TIMESTAMP("TIMESTAMP", JavaType.Date),
    /**
     * LONGTEXT
     */
    CLOB("LONGTEXT", JavaType.String),
    /**
     * BLOB
     */
    BLOB("BLOB", JavaType.ByteArray);
    /**
     * jdbc类型名
     */
    private String typeName;
    /**
     * java类型
     */
    private JavaType javaType;

    /**
     * 通过jdbc类型名和java类型生成jdbc类型
     *
     * @param typeName jdbc类型名
     * @param javaType java类型
     */
    JdbcType(String typeName, JavaType javaType) {
        this.javaType = javaType;
        this.typeName = typeName;
    }

    /**
     * 获取类型名
     *
     * @return typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 获取java类型
     *
     * @return javaType
     */
    public JavaType getJavaType() {
        return javaType;
    }

}

