<#-- 生成java mapper -->
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
public interface ${class.className}Service {
/**
* 新增
* @param ${class.className?uncap_first} 实体
* @return 是否新增成功
*/
Boolean insert(${class.entityType} ${class.className?uncap_first});

/**
* 根据id删除
* @param id 主键
* @return 是否删除成功
*/
Boolean deleteById(${class.pkType} id);

/**
* 根据id更新
* @param ${class.className?uncap_first} 实体
* @return 是否更新成功
*/
Boolean update(${class.entityType} ${class.className?uncap_first});

/**
* 根据id获取
* @param id 主键
* @return 实体
*/
${class.entityType} findById(${class.pkType} id);
}

