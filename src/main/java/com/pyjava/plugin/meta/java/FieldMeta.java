package com.pyjava.plugin.meta.java;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojj9
 * @description 域信息
 * @date 2019-12-16 13:52
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
