package com.pyjava.plugin.enumeration;

import static com.pyjava.plugin.config.Constant.*;

/**
 * @author zhaojj9
 * @since 1.0.2
 */
public enum Error {

    /**
     * jdbc驱动类未找到
     */
    DriverNotFound(DRIVER_NOT_FOUND),
    /**
     * jdbc链接未找到
     */
    UrlNotFound(URL_NOT_FOUND),
    /**
     * 用户名未找到
     */
    UsernameNotFound(USERNAME_NOT_FOUND),

    /**
     * 密码未找到
     */
    PasswordNotFound(PASSWORD_NOT_FOUND),
    /**
     * 未知数据库
     */
    UnknownDatabase(UNKNOWN_DATABASE),
    /**
     * 表名字符串未找到
     */
    TablesNotFound(TABLES_NOT_FOUND),
    /**
     * model生成目标地址未找到
     */
    ModelPathNotFound(MODEL_PATH_NOT_FOUND),
    /**
     * dao生成目标地址未找到
     */
    DaoPathNotFound(DAO_PATH_NOT_FOUND),
    /**
     * xml生成目标地址未找到
     */
    XmlPathNotFound(XML_PATH_NOT_FOUND),
    /**
     * service生成目标地址未找到
     */
    ServicePathNotFound(SERVICE_PATH_NOT_FOUND),
    ;

    private String message;

    Error(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
