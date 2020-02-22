package com.pyjava.plugin.meta.xml;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml 信息
 *
 * @author zhaojj9
 * @since 1.0.0
 */
@Data
public class XmlInfo {
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 类型名
     */
    private String type;
    /**
     * 标识id
     */
    private Map<String, String> id = new HashMap<>();
    /**
     * 结果集
     * 对应键为column,javaName,javaType, jdbcType
     */
    private List<Map<String, String>> resultMaps = new ArrayList<>();
    /**
     * 表名
     */
    private String tableName;
}
