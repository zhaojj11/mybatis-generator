package com.pyjava.plugin.meta.java;

import com.pyjava.plugin.enumeration.Annotation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojj9
 * @description
 * @date 2019-12-13 16:44
 */
@Data
public class ClassMeta {
    private String packageName;
    private List<String> imports = new ArrayList<>();

    private List<String> docs = new ArrayList<>();
    private List<String> annotations = new ArrayList<>();
    private String className;

    private List<FieldMeta> fields = new ArrayList<>();

    private String pkType;
    private String entityType;

    public void addAnnotation(Annotation annotation){
        this.annotations.add(annotation.getAnno());
        this.imports.add(annotation.getPackageName());
    }

}
