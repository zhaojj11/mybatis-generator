package com.pyjava.plugin.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author zhaojj9
 * @since 1.0.0
 */
public class StringUtil {
    /**
     * 将unix命名风格转换为首字母小写驼峰字符串
     * 即将test_test转换为testTest字符串
     * @param str 待处理字符串
     * @return 驼峰转换后的字符串
     */
    public static String getSmallCamelString(String str) {
        StringBuilder sb = new StringBuilder();
        String[] names = StringUtils.split(str.toLowerCase(), "_");
        // 第一个单词全小写
        sb.append(names[0]);
        for (int i = 1, len = names.length; i < len; i++) {
            sb.append(names[i].substring(0, 1).toUpperCase()).append(names[i].substring(1));
        }
        return sb.toString();
    }

    /**
     * 将unix命名风格转换为驼峰字符串
     * 即将test_test转换为TestTest字符串
     * @param str 待处理字符串
     * @return 驼峰转换后的字符串
     */
    public static String getBigCamelString(String str) {
        StringBuilder sb = new StringBuilder();
        String[] names = StringUtils.split(str.toLowerCase(), "_");
        for (int i = 0, len = names.length; i < len; i++) {
            sb.append(names[i].substring(0, 1).toUpperCase()).append(names[i].substring(1));
        }
        return sb.toString();
    }

    /**
     * 获取通过连接url获取数据库名
     *
     * @param url 连接url
     * @return 数据库名
     */
    public static String getDatabaseName(String url) {
        if (url.contains("?")) {
            String reg = "/.*?\\?";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String a = matcher.group();
                String[] b = a.split("/");
                return b[b.length - 1].substring(0, b[b.length - 1].length() - 1);
            }
            return null;
        } else {
            String[] a = url.split("/");
            return a[a.length - 1];
        }
    }

    /**
     * 通过点分割包名获取/路径字符串
     * @param path 点包名
     * @return 路径字符串
     */
    public static String getPathFromPoint(String path) {
        String[] pathName = path.split("\\.");
        StringBuilder sb = new StringBuilder();
        sb.append(pathName[0]);
        for (int i = 1, pathNameLength = pathName.length; i < pathNameLength; i++) {
            sb.append("/").append(pathName[i]);
        }
        return sb.toString();
    }
}
