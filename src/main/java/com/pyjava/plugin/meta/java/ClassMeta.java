package com.pyjava.plugin.meta.java;

import com.pyjava.plugin.enumeration.Annotation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 类信息
 *
 * @author zhaojj9
 * @since 1.0.0
 */
@Data
public class ClassMeta {
    /**
     * 包名
     */
    private String packageName;
    /**
     * 引入包名集合
     */
    private List<String> imports = new ArrayList<>();

    /**
     * 注释字符串
     */
    private List<String> docs = new ArrayList<>();
    /**
     * 注解
     */
    private List<String> annotations = new ArrayList<>();
    /**
     * 类名
     */
    private String className;
    /**
     * 域
     */
    private List<FieldMeta> fields = new ArrayList<>();

    /**
     * 主键类型
     */
    private String pkType;
    private String entityType;

    /**
     * 一个简便的添加注释的方法
     * @param annotation 注解
     */
    public void addAnnotation(Annotation annotation) {
        this.annotations.add(annotation.getAnno());
        this.imports.add(annotation.getPackageName());
    }

}
