package com.pyjava.plugin.meta.database;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * 字段结构
 *
 * @author zhaojj9
 * @since 1.0.0
 */
@Data
@ToString
public class ColumnMeta {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 字段排序
     */
    private String ordinalPosition;
    /**
     * 字段是否可以是NULL
     */
    private String isNullable;
    /**
     * 默认值
     */
    private String columnDefault;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 索引类型(PRI，代表主键，UNI，代表唯一键，MUL，可重复)
     */
    private String columnKey;
    /**
     * auto_increment
     */
    private String extra;
    /**
     * 字段注释
     */
    private String columnComment;
}
