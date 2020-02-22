package com.pyjava.plugin.config;

import com.pyjava.plugin.meta.database.TableMeta;
import com.pyjava.plugin.util.StringUtil;
import lombok.Data;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.*;

import static com.pyjava.plugin.config.Constant.*;

/**
 * @author zhaojj9
 * @description 全局配置类
 * @date 2019-12-13 16:42
 */
@Data
public class GlobalConfiguration {

    /**
     * jdbc驱动类字符串
     */
    private String driver;
    /**
     * 数据库名
     */
    private String database;
    /**
     * jdbc链接
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 待生成表名字符串
     */
    private HashSet<String> tableNames;
    /**
     * 待生成表明的list集合
     */
    private List<TableMeta> tables = new ArrayList<>();

    /**
     * 作者信息
     */
    private String author = "";

    /**
     * xml文件生成路径
     */
    private String xmlPath;
    /**
     * model文件生成路径
     */
    private String modelPath;
    /**
     * dao文件生成路径
     */
    private String daoPath;
    /**
     * service文件生成路径
     */
    private String servicePath;


    /**
     * 加载配置
     * <p>
     * 1. author 作者 若配置文件中未包含作者信息,则author=""
     * 2. driver 驱动 若配置信息中未包含驱动信息,抛出异常
     * 3. url 链接 若配置信息中未包含链接信息,抛出异常
     * 4. username 链接用户名 若配置信息中未包含链接用户名信息,抛出异常
     * 5. password 链接密码 若配置信息中未包含链接密码信息,抛出异常
     * 6. database 若链接信息中不能识别出数据库信息,抛出异常
     * 7. tableNames 若配置配置信息中未包含表名集合,抛出异常
     * 8. modelPath 若配置配置信息中未包含模型输出路径,抛出异常
     * 9. daoPath 若配置配置信息中未包含dao输出路径,抛出异常
     * 10. xmlPath 若配置配置信息中未包含xml输出路径,抛出异常
     * 11. servicePath 若配置配置信息中未包含service输出路径,抛出异常
     * </p>
     *
     * @param properties 配置对象
     * @throws MojoExecutionException 配置未加载成功<p>
     */
    public void load(Properties properties) throws MojoExecutionException {
        // 获取key对应的value值
        if (properties.getProperty(AUTHOR) != null) {
            this.author = properties.getProperty(AUTHOR);
        }

        if (properties.getProperty(DRIVER) != null) {
            this.driver = properties.getProperty(DRIVER);
        } else {
            throw new MojoExecutionException(DRIVER + "参数不可为空");
        }

        if (properties.getProperty(URL) != null) {
            this.url = properties.getProperty(URL);
        } else {
            throw new MojoExecutionException(URL + "参数不可为空");
        }

        if (properties.getProperty(USERNAME) != null) {
            this.username = properties.getProperty(USERNAME);
        } else {
            throw new MojoExecutionException(USERNAME + "参数不可为空");
        }

        if (properties.getProperty(PASSWORD) != null) {
            this.password = properties.getProperty(PASSWORD);
        } else {
            throw new MojoExecutionException(PASSWORD + "参数不可为空");
        }

        if (this.url != null) {
            this.database = StringUtil.getDatabaseName(this.url);
        } else {
            throw new MojoExecutionException("数据库名无法识别");
        }

        if (properties.getProperty(TABLES) != null) {
            this.tableNames = new HashSet<>(Arrays.asList(properties.getProperty(TABLES).split(",")));
        } else {
            throw new MojoExecutionException(TABLES + "参数不可为空");
        }

        if (properties.getProperty(XML_PATH) != null) {
            this.xmlPath = properties.getProperty(XML_PATH);
        } else {
            throw new MojoExecutionException(XML_PATH + "参数不可为空");
        }

        if (properties.getProperty(MODEL_PATH) != null) {
            this.modelPath = properties.getProperty(MODEL_PATH);
        } else {
            throw new MojoExecutionException(MODEL_PATH + "参数不可为空");
        }

        if (properties.getProperty(DAO_PATH) != null) {
            this.daoPath = properties.getProperty(DAO_PATH);
        } else {
            throw new MojoExecutionException(DAO_PATH + "参数不可为空");
        }

        if (properties.getProperty(SERVICE_PATH) != null) {
            this.servicePath = properties.getProperty(SERVICE_PATH);
        } else {
            throw new MojoExecutionException(SERVICE_PATH + "参数不可为空");
        }
    }
}
