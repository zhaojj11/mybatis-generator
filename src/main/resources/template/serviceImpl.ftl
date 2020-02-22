<#-- 生成java mapper -->
<#-- 生成model的package -->
package ${class.packageName};

<#-- 生成import -->
<#list class.imports as import>
    import ${import};
</#list>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ${mapperImport};

<#-- 生成java类doc -->
/**
<#list class.docs as doc>
    * ${doc}
</#list>
*/
<#-- 生成注解 -->
<#list class.annotations as annotation>
    @${annotation}
</#list>
@Service
@Primary
<#-- 生成java类注解 -->
public class ${class.className}ServiceImpl implements ${class.className}Service {

@Autowired
private ${class.className}Mapper ${class.className?uncap_first}Mapper;

/**
* 新增
* @param ${class.className?uncap_first} 实体
* @return 是否新增成功
*/
@Override
public Boolean insert(${class.entityType} ${class.className?uncap_first}){
return ${class.className?uncap_first}Mapper.insert(${class.className?uncap_first}) == 1;
}

/**
* 根据id删除
* @param id 主键
* @return 是否删除成功
*/
@Override
public Boolean deleteById(${class.pkType} id){
return ${class.className?uncap_first}Mapper.deleteById(id) == 1;
}

/**
* 根据id更新
* @param ${class.className?uncap_first} 实体
* @return 是否更新成功
*/
@Override
public Boolean update(${class.entityType} ${class.className?uncap_first}){
return ${class.className?uncap_first}Mapper.update(${class.className?uncap_first}) == 1;
}

/**
* 根据id获取
* @param id 主键
* @return 实体
*/
@Override
public ${class.entityType} findById(${class.pkType} id){
return ${class.className?uncap_first}Mapper.findById(id) ==;
}
}