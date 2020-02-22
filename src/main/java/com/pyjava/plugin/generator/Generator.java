package com.pyjava.plugin.generator;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author zhaojj9
 * @since 1.0.2
 */
public interface Generator {
    /**
     * 初始化方法
     */
    void init() throws MojoExecutionException;

    /**
     * 处理方法
     */
    void service() throws MojoExecutionException;

    /**
     * 销毁方法
     */
    void destroy();
}
