package com.pyjava.plugin.enumeration;

import com.pyjava.plugin.meta.java.ClassMeta;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojj9
 * @description 注解枚举类
 * @date 2020-01-19 15:30
 */
@ToString
public enum Annotation {

    /**
     * lombok Data注解
     */
    DATA("lombok.Data","Data"),

    /**
     * lombok ToString注解
     */
    TOSTRING("lombok.ToString", "ToString"),
    ;

    private String packageName;
    private String anno;
    private Map<String, String> params;


    Annotation(String packageName, String anno) {
        this.packageName = packageName;
        this.anno = anno;
        this.params = new HashMap<>();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAnno() {
        return anno;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
