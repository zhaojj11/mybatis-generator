package com.pyjava.plugin.enumeration;

import com.pyjava.plugin.meta.java.ClassMeta;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 注解枚举类
 *
 * @author zhaojj9
 * @since 1.0.0
 */
@ToString
public enum Annotation {

    /**
     * lombok Data注解
     */
    DATA("lombok.Data", "Data"),

    /**
     * lombok ToString注解
     */
    TOSTRING("lombok.ToString", "ToString"),
    ;

    /**
     * 包名
     */
    private String packageName;
    /**
     * 注解名
     */
    private String anno;
    /**
     * 参数
     */
    private Map<String, String> params;


    /**
     * 通过包名，注解名生成一个注解信息类，参数默认为空
     * @param packageName 包名
     * @param anno 注解名
     */
    Annotation(String packageName, String anno) {
        this.packageName = packageName;
        this.anno = anno;
        this.params = new HashMap<>();
    }

    /**
     * 获取 注解包名packageName
     * @return packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * 获取 注解名
     * @return anno
     */
    public String getAnno() {
        return anno;
    }

    /**
     * 获取注解参数
     * @return params
     */
    public Map<String, String> getParams() {
        return params;
    }
}
