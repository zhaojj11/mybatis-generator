package com.pyjava.plugin.enumeration;

/**
 * @author zhaojj9
 * @description java 类型枚举类
 * @date 2019-12-17 10:31
 */
public enum JavaType {
    /**
     * java 数据类型
     */
    String("string", "String", "java.lang"),
    BigDecimal("bigDecimal", "BigDecimal", "java.math"),
    Boolean("boolean", "Boolean", "java.lang"),
    Byte("byte", "Byte", "java.lang"),
    Short("short", "Short", "java.lang"),
    Integer("integer", "Integer", "java.lang"),
    Long("long", "Long", "java.lang"),
    Float("float", "Float", "java.lang"),
    Double("double", "Double", "java.lang"),
    ByteArray("bytearray", "Byte[]", "java.lang"),
    Date("date", "Date", "java.util");
    private String name;
    private String type;
    private String packageName;

    JavaType(String name, String type, String packageName) {
        this.name = name;
        this.type = type;
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPackageName() {
        return packageName;
    }

}
