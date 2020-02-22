package com.pyjava.plugin;

import com.pyjava.plugin.config.GlobalConfiguration;
import com.pyjava.plugin.meta.java.ClassMeta;
import com.pyjava.plugin.meta.java.FieldMeta;
import com.pyjava.plugin.meta.database.ColumnMeta;
import com.pyjava.plugin.meta.database.TableMeta;
import com.pyjava.plugin.meta.xml.XmlInfo;
import com.pyjava.plugin.util.DbUtil;
import com.pyjava.plugin.util.StringUtil;
import com.pyjava.plugin.util.TypeUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static com.pyjava.plugin.config.Constant.*;

/**
 * @author zhaojj9
 * @description 作为mybatis生成器的插件入口实现类
 * @date 2019-12-10 22:29
 */
@Slf4j
@Mojo(name = "mybatis-generator", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.TEST)
public class MybatisGenerator extends AbstractMojo {

    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "generator.mybatis.outputDirectory", defaultValue = "${project.build.directory}/generated-sources/generator", required = true)
    private File outputDirectory;

    @Parameter(property = "generator.mybatis.configurationFile", defaultValue = "${project.basedir}/src/main/resources/generator.properties", required = true)
    private File configurationFile;

    @Parameter(property = "generator.skip", defaultValue = "false")
    private boolean skip;

    @Parameter(property = "debug", defaultValue = "false")
    private boolean debug;

    GlobalConfiguration configuration = new GlobalConfiguration();

    Writer out = null;

    /**
     * 需要拿到的数据
     * 1. jdbc(url,name,password)
     * 2. 需要操作的表名
     * 3. 是否需要继承类
     * 4. 是否需要继承接口
     * 5. 是否需要添加注解
     * <p>
     * mapper 接口
     * save/saveBatch, deleteBy[PK]/deleteBy[PK]s, updateBy[Id], findBy[PK]/findBy[PKs], existsById
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            log.info("跳过generator生成文件，如需要生成文件请删除skip标签");
        }

        log.info("==开始==");


        log.info("读取配置文件");
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(configurationFile));
        } catch (FileNotFoundException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new MojoExecutionException(configurationFile.getAbsolutePath() + " 文件不存在!");
        }
        // 使用properties对象加载输入流
        try {
            properties.load(bufferedReader);
        } catch (IOException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new MojoExecutionException("配置对象加载流错误!");
        }

        // 获取全局配置信息

        configuration.load(properties);


        try {
            Connection connection = DbUtil.getConnection(configuration);
        } catch (ClassNotFoundException | SQLException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new MojoExecutionException("数据库连接错误!");
        }

        DbUtil.getTable(configuration);
        HashSet<String> t = new HashSet<>();
        configuration.getTables().forEach(element -> {
            t.add(element.getTableName());
        });
        log.info("需要生成以下表:" + t.toString());

        generate(configuration);

        DbUtil.close();
    }

    /**
     * 通过数据表元数据生成对应文件
     *
     * @param configuration 全部配置信息
     */
    private void generate(GlobalConfiguration configuration) throws MojoExecutionException {
        List<TableMeta> tables = configuration.getTables();
        for (TableMeta tableMeta : tables) {
            generateModel(tableMeta);
            generateMapper(tableMeta);
            generateXml(tableMeta);
        }
    }

    private void generateXml(TableMeta tableMeta) throws MojoExecutionException {
        log.info("开始为" + tableMeta.getTableName() + "生成Xml");
        String className = TypeUtil.getClassName(tableMeta, "");
        XmlInfo xmlInfo = new XmlInfo();
        xmlInfo.setNamespace(configuration.getDaoPath() + "." + className + "Mapper");
        xmlInfo.setType(configuration.getModelPath() + "." + className);
        xmlInfo.setTableName(tableMeta.getTableName());
        for (ColumnMeta column : tableMeta.getColumns()) {
            if ("PRI".equals(column.getColumnKey())) {
                xmlInfo.getId().put("column", column.getColumnName());
                xmlInfo.getId().put("javaName", StringUtil.getSmallCamelString(column.getColumnName()));
                xmlInfo.getId().put("jdbcType", TypeUtil.getJdbcType(column).getTypeName());
                xmlInfo.getId().put("javaType", TypeUtil.getJavaType(column).toLowerCase());
                if ("auto_increment".equals(column.getExtra())) {
                    xmlInfo.getId().put("generatorKeys", "true");
                }
            } else {
                HashMap<String, String> map = new HashMap<>();
                map.put("column", column.getColumnName());
                map.put("javaName", StringUtil.getSmallCamelString(column.getColumnName()));
                map.put("jdbcType", TypeUtil.getJdbcType(column).getTypeName());
                map.put("javaType", TypeUtil.getJavaType(column).toLowerCase());
                xmlInfo.getResultMaps().add(map);
            }
        }

        log.info(xmlInfo.toString());

        Configuration xmlConfiguration = new Configuration();
        try {
            // step2 获取模版路径
            xmlConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("xml", xmlInfo);
            Template template = xmlConfiguration.getTemplate("xml.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + "\\" + RESOURCE_PATH + "\\" + StringUtil.getPathFromPoint(configuration.getXmlPath()) + "\\";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = className + "Mapper.xml";
            log.info(outPath + filename);
            File docFile = new File(outPath + filename);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void generateMapper(TableMeta tableMeta) throws MojoExecutionException {
        log.info("开始为" + tableMeta.getTableName() + "生成Mapper");
        String className = TypeUtil.getClassName(tableMeta, "");
        ClassMeta mapper = new ClassMeta();
        mapper.setPackageName(configuration.getDaoPath());
        mapper.getImports().add(configuration.getModelPath() + "." + className);
        mapper.getDocs().add("@description " + className + " 映射器");
        mapper.getDocs().add("@date " + new Date());
        mapper.getDocs().add("@author " + configuration.getAuthor());
        mapper.setClassName(className);
        mapper.setEntityType(className);

        for (ColumnMeta column : tableMeta.getColumns()) {
            if ("PRI".equals(column.getColumnKey())) {
                mapper.setPkType(TypeUtil.getJavaType(column));
            }
        }

        Configuration mapperConfiguration = new Configuration();
        try {
            // step2 获取模版路径
            mapperConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("class", mapper);
            Template template = mapperConfiguration.getTemplate("mapper.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + "\\" + JAVA_PATH + "\\" + StringUtil.getPathFromPoint(configuration.getDaoPath()) + "\\";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = mapper.getClassName() + "Mapper.java";
            log.info(outPath + filename);
            File docFile = new File(outPath + filename);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void generateModel(TableMeta tableMeta) throws MojoExecutionException {
        String className = TypeUtil.getClassName(tableMeta, "");
        log.info("开始为" + tableMeta.getTableName() + "生成Model");
        log.info("组装 model 类 数据");
        ClassMeta classMeta = new ClassMeta();
        classMeta.setPackageName(configuration.getModelPath());
        classMeta.getImports().add("lombok.Data");
        classMeta.getDocs().add("@description " + tableMeta.getTableName() + " " + tableMeta.getComment());
        classMeta.getDocs().add("@date " + new Date());
        classMeta.getDocs().add("@author " + configuration.getAuthor());

        classMeta.getAnnotations().add("Data");
        classMeta.setClassName(className);
        for (ColumnMeta column : tableMeta.getColumns()) {
            FieldMeta fieldMeta = new FieldMeta();
            fieldMeta.getDocs().add(column.getColumnName() + ":" + column.getColumnComment());
            fieldMeta.setTypename(TypeUtil.getJavaType(column));
            fieldMeta.setFieldName(StringUtil.getSmallCamelString(column.getColumnName()));
            classMeta.getFields().add(fieldMeta);
        }
        log.info("生成 Model 类文件");

        Configuration freemarkerConfiguration = new Configuration();
        try {
            freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("class", classMeta);
            Template template = freemarkerConfiguration.getTemplate("model.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + "\\" + JAVA_PATH + "\\" + StringUtil.getPathFromPoint(configuration.getModelPath()) + "\\";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = classMeta.getClassName() + ".java";
            log.info(outPath + filename);
            File docFile = new File(outPath + filename);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
