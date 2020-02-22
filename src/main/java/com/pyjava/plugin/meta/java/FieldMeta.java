package com.pyjava.plugin.meta.java;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 域信息
 *
 * @author zhaojj9
 * @since 1.0.0
 */
@Data
public class FieldMeta {
    /**
     * 注释
     */
    private List<String> docs = new ArrayList<>();
    /**
     * 类型名
     */
    private String typename;
    /**
     * 域名称
     */
    private String fieldName;
}
