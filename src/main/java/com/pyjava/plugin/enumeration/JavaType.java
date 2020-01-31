package com.pyjava.plugin.enumeration;

/**
 * java 类型枚举类
 *
 * @author zhaojj9
 * @since 1.0.0
 */
public enum JavaType {
    /**
     * String
     */
    String("string", "String", "java.lang"),
    /**
     * BigDecimal
     */
    BigDecimal("bigDecimal", "BigDecimal", "java.math"),
    /**
     * Boolean
     */
    Boolean("boolean", "Boolean", "java.lang"),
    /**
     * Byte
     */
    Byte("byte", "Byte", "java.lang"),
    /**
     * Short
     */
    Short("short", "Short", "java.lang"),
    /**
     * Integer
     */
    Integer("integer", "Integer", "java.lang"),
    /**
     * Long
     */
    Long("long", "Long", "java.lang"),
    /**
     * Float
     */
    Float("float", "Float", "java.lang"),
    /**
     * Double
     */
    Double("double", "Double", "java.lang"),
    /**
     * ByteArray
     */
    ByteArray("bytearray", "Byte[]", "java.lang"),
    /**
     * Date
     */
    Date("date", "Date", "java.util");
    /**
     * 类型名
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 包名
     */
    private String packageName;

    /**
     * java 类型枚举类构造器，通过java类型名，类型，包名
     *
     * @param name        java类型名
     * @param type        类型
     * @param packageName 包名
     */
    JavaType(String name, String type, String packageName) {
        this.name = name;
        this.type = type;
        this.packageName = packageName;
    }

    /**
     * 获取java类型名
     *
     * @return name 类型名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取java类型
     *
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 获取java类型包名
     *
     * @return packageName 包名
     */
    public String getPackageName() {
        return packageName;
    }

}
