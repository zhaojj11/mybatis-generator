package com.pyjava.plugin.meta.xml;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojj9
 * @description
 * @date 2019-12-16 16:24
 */
@Data
public class XmlInfo {
    private String namespace;
    private String type;
    private Map<String, String> id = new HashMap<>();
    /**
     * column,javaName,javaType, jdbcType
     */
    private List<Map<String, String>> resultMaps = new ArrayList<>();
    private String tableName;
}
