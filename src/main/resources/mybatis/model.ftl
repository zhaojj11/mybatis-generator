<#-- 生成java model -->
<#-- 生成model的package -->
package ${class.packageName};

<#-- 生成import -->
<#list class.imports as import>
import ${import};
</#list>

<#-- 生成java类doc -->
/**
<#list class.docs as doc>
 * ${doc}
</#list>
 */
<#-- 生成java类注解 -->
<#list class.annotations as annotation>
@${annotation}
</#list>
<#-- 生成java类注解 -->
public class ${class.className} {

<#-- 生成java类 成员变量 -->
<#list class.fields as field>
    <#-- 生成java类 成员变量 注释 -->
    /**
    <#list field.docs as doc>
     * ${doc}
    </#list>
     */
    <#-- 生成java类 成员变量 -->
    private ${field.typename} ${field.fieldName};

</#list>
}