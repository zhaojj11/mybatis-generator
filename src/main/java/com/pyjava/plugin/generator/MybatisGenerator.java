package com.pyjava.plugin.generator;

import com.pyjava.plugin.config.GlobalConfiguration;
import com.pyjava.plugin.enumeration.JavaType;
import com.pyjava.plugin.enumeration.JdbcType;
import com.pyjava.plugin.meta.database.ColumnMeta;
import com.pyjava.plugin.meta.database.TableMeta;
import com.pyjava.plugin.meta.java.ClassMeta;
import com.pyjava.plugin.meta.java.FieldMeta;
import com.pyjava.plugin.meta.xml.XmlInfo;
import com.pyjava.plugin.util.DbUtil;
import com.pyjava.plugin.util.StringUtil;
import com.pyjava.plugin.util.TypeUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static com.pyjava.plugin.config.Constant.*;

/**
 * @author zhaojj9
 * @since 1.0.2
 */
@Slf4j
public class MybatisGenerator implements Generator {

    private boolean debug;
    private MavenProject project;
    private File configurationFile;
    private GlobalConfiguration configuration = new GlobalConfiguration();

    private Writer out = null;

    public MybatisGenerator(boolean debug, MavenProject project, File configurationFile) {
        this.debug = debug;
        this.project = project;
        this.configurationFile = configurationFile;
    }

    /**
     * 初始化方法
     */
    @Override
    public void init() throws MojoExecutionException {
        // 加载
        log.info("mybatis-generator:开始");
        log.info("mybatis-generator:读取配置文件");

        Properties properties = new Properties();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(configurationFile));
        } catch (FileNotFoundException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new MojoExecutionException("mybatis-generator:" + configurationFile.getAbsolutePath() + " 文件不存在!");
        }
        // 使用properties对象加载输入流
        try {
            properties.load(bufferedReader);
        } catch (IOException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new MojoExecutionException("mybatis-generator:配置对象加载流错误!");
        }

        // 获取全局配置信息

        configuration.load(properties);

        try {
            Connection connection = DbUtil.getConnection(configuration);
        } catch (ClassNotFoundException | SQLException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new MojoExecutionException("mybatis-generator:数据库连接错误!");
        }

        DbUtil.getTable(configuration);
        HashSet<String> t = new HashSet<>();
        configuration.getTables().forEach(element -> {
            t.add(element.getTableName());
        });
        log.info("mybatis-generator:需要生成以下表:" + t.toString());
    }

    /**
     * 处理方法
     */
    @Override
    public void service() throws MojoExecutionException {
        List<TableMeta> tables = configuration.getTables();
        for (TableMeta tableMeta : tables) {
            generateModel(tableMeta);
            generateMapper(tableMeta);
            generateXml(tableMeta);
            generateService(tableMeta);
            generateServiceImpl(tableMeta);
        }
    }

    /**
     * 销毁方法
     */
    @Override
    public void destroy() {
        DbUtil.close();
    }

    private void generateModel(TableMeta tableMeta) throws MojoExecutionException {
        String className = TypeUtil.getClassName(tableMeta, "");
        log.info("mybatis-generator:开始为" + tableMeta.getTableName() + "生成Model");
        ClassMeta classMeta = new ClassMeta();
        classMeta.setPackageName(configuration.getModelPath());
        classMeta.getImports().add("lombok.Data");
        classMeta.getDocs().add(tableMeta.getTableName() + " " + tableMeta.getComment());
        classMeta.getDocs().add("");
        classMeta.getDocs().add("@date " + new Date());
        classMeta.getDocs().add("@author " + configuration.getAuthor());

        classMeta.getAnnotations().add("Data");
        classMeta.setClassName(className);

        boolean findDateField = false;
        for (ColumnMeta column : tableMeta.getColumns()) {
            FieldMeta fieldMeta = new FieldMeta();
            fieldMeta.getDocs().add(column.getColumnName() + ":" + column.getColumnComment());
            fieldMeta.setTypename(TypeUtil.getJavaType(column));
            fieldMeta.setFieldName(StringUtil.getSmallCamelString(column.getColumnName()));
            classMeta.getFields().add(fieldMeta);
            if (TypeUtil.getJdbcType(column).equals(JdbcType.DATE)) {
                findDateField = true;
            }
        }
        if (findDateField) {
            classMeta.getImports().add(JavaType.Date.getPackageName() + "." + JavaType.Date.getType());

        }

        Configuration freemarkerConfiguration = new Configuration();
        try {
            freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            Map<String, Object> dataMap = new HashMap<String, Object>(2);
            dataMap.put("class", classMeta);
            Template template = freemarkerConfiguration.getTemplate("model.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + JAVA_PATH + StringUtil.getPathFromPoint(configuration.getModelPath()) + "/";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = classMeta.getClassName() + ".java";
            log.info("mybatis-generator:文件路径为" + outPath + filename);
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
        log.info("mybatis-generator:开始为" + tableMeta.getTableName() + "生成Mapper");
        String className = TypeUtil.getClassName(tableMeta, "");
        ClassMeta mapper = new ClassMeta();
        mapper.setPackageName(configuration.getDaoPath());
        mapper.getImports().add(configuration.getModelPath() + "." + className);
        mapper.getDocs().add(className + " 映射器");
        mapper.getDocs().add("");
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
            // 获取模版路径
            mapperConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            // 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("class", mapper);
            Template template = mapperConfiguration.getTemplate("mapper.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + JAVA_PATH + StringUtil.getPathFromPoint(configuration.getDaoPath()) + "/";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = mapper.getClassName() + "Mapper.java";
            log.info("mybatis-generator:文件路径为" + outPath + filename);
            File docFile = new File(outPath + filename);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
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

    private void generateXml(TableMeta tableMeta) throws MojoExecutionException {
        log.info("mybatis-generator:开始为" + tableMeta.getTableName() + "生成Xml");
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
                } else {
                    xmlInfo.getId().put("generatorKeys", "false");
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

        Configuration xmlConfiguration = new Configuration();
        try {
            // 获取模版路径
            xmlConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            // 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("xml", xmlInfo);
            Template template = xmlConfiguration.getTemplate("xml.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + RESOURCE_PATH + StringUtil.getPathFromPoint(configuration.getXmlPath()) + "/";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = className + "Mapper.xml";
            log.info("mybatis-generator:文件路径为" + outPath + filename);
            File docFile = new File(outPath + filename);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
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

    private void generateService(TableMeta tableMeta) throws MojoExecutionException {
        log.info("mybatis-generator:开始为" + tableMeta.getTableName() + "生成Service接口");
        // 生成service接口
        String className = TypeUtil.getClassName(tableMeta, "");
        ClassMeta service = new ClassMeta();
        service.setPackageName(configuration.getServicePath());
        service.getImports().add(configuration.getModelPath() + "." + className);
        service.getDocs().add(className + " 服务层");
        service.getDocs().add("");
        service.getDocs().add("@date " + new Date());
        service.getDocs().add("@author " + configuration.getAuthor());
        service.setClassName(className);
        service.setEntityType(className);

        for (ColumnMeta column : tableMeta.getColumns()) {
            if ("PRI".equals(column.getColumnKey())) {
                service.setPkType(TypeUtil.getJavaType(column));
            }
        }

        Configuration mapperConfiguration = new Configuration();
        try {
            // 获取模版路径
            mapperConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            // 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("class", service);
            Template template = mapperConfiguration.getTemplate("service.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + JAVA_PATH + StringUtil.getPathFromPoint(configuration.getServicePath()) + "/";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = service.getClassName() + "Service.java";
            log.info("mybatis-generator:文件路径为" + outPath + filename);
            File docFile = new File(outPath + filename);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
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

    private void generateServiceImpl(TableMeta tableMeta) throws MojoExecutionException {
        log.info("mybatis-generator:开始为" + tableMeta.getTableName() + "生成Service实现类");
        // 生成service实现类
        String className = TypeUtil.getClassName(tableMeta, "");
        ClassMeta service = new ClassMeta();
        service.setPackageName(configuration.getServicePath() + ".impl");
        service.getImports().add(configuration.getModelPath() + "." + className);
        service.getDocs().add(className + " 服务层");
        service.getDocs().add("");
        service.getDocs().add("@date " + new Date());
        service.getDocs().add("@author " + configuration.getAuthor());
        service.setClassName(className);
        service.setEntityType(className);

        for (ColumnMeta column : tableMeta.getColumns()) {
            if ("PRI".equals(column.getColumnKey())) {
                service.setPkType(TypeUtil.getJavaType(column));
            }
        }

        Configuration mapperConfiguration = new Configuration();
        try {
            // 获取模版路径
            mapperConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
            // 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("class", service);
            dataMap.put("mapperImport", configuration.getDaoPath() + "." + className + "Mapper");
            Template template = mapperConfiguration.getTemplate("serviceImpl.ftl");
            String outPath = this.project.getBasedir() + COMMON_PATH + JAVA_PATH + StringUtil.getPathFromPoint(configuration.getServicePath()) + "/impl/";
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = service.getClassName() + "ServiceImpl.java";
            log.info("mybatis-generator:文件路径为" + outPath + filename);
            File docFile = new File(outPath + filename);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
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
