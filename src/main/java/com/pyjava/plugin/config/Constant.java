package com.pyjava.plugin.config;

/**
 * <b>常量类</b>
 * <p>
 * 1. 链接配置信息的键
 * 2. 文件生成目标地址的键
 * 3. 文件相关信息的键
 * 4. 其他相关地址
 * </p>
 *
 * @author zhaojj9
 * @since  1.0.0
 */
public class Constant {

    /**
     * 链接配置信息的键  jdbc驱动类
     */
    public final static String DRIVER = "driver";
    /**
     * 链接配置信息的键  jdbc链接
     */
    public final static String URL = "url";
    /**
     * 链接配置信息的键  用户名
     */
    public final static String USERNAME = "username";
    /**
     * 链接配置信息的键  密码
     */
    public final static String PASSWORD = "password";
    /**
     * 链接配置信息的键  表名字符串
     */
    public final static String TABLES = "tables";


    /**
     * 文件生成目标地址的键  xml生成目标地址
     */
    public final static String XML_PATH = "xml.path";

    /**
     * 文件生成目标地址的键  model生成目标地址
     */
    public final static String MODEL_PATH = "model.package";
    /**
     * 文件生成目标地址的键  dao生成目标地址
     */
    public final static String DAO_PATH = "dao.package";

    /**
     * 文件生成目标地址的键  service生成目标地址
     */
    public final static String SERVICE_PATH = "service.package";


    /**
     * 文件相关信息的键  author生成目标地址
     */
    public final static String AUTHOR = "author";

    /**
     * 其他相关地址  /src/main
     */
    public final static String COMMON_PATH = "/src/main";

    /**
     * 其他相关地址  /java
     */
    public final static String JAVA_PATH = "/java";

    /**
     * 其他相关地址  /resources
     */
    public final static String RESOURCE_PATH = "/resources";

    /**
     * 其他相关地址  模板地址
     */
    public final static String TEMPLATE_PATH = "/template";

    /**
     * 类型常量
     */
    public final static String INT = "int";
    public final static String BIGINT = "bigint";
    public final static String DOUBLE = "double";
    public final static String FLOAT = "float";
    public final static String VARCHAR = "varchar";
    public final static String CHAR = "char";
    public final static String TEXT = "text";
    public final static String DATE = "date";
    public final static String DATETIME = "datetime";
    public final static String TIMESTAMP = "timestamp";
}
