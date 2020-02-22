<#-- 生成java mapper -->
<#-- 生成model的package -->
package ${class.packageName};

<#-- 生成import -->
<#list class.imports as import>
import ${import};
</#list>
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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
@Mapper
@Component
<#-- 生成java类注解 -->
public interface ${class.className}Mapper {

    /**
     * 新增
     * @param ${class.className?uncap_first} 实体
     * @return 影响记录数
     */
    int insert(${class.entityType} ${class.className?uncap_first});

    /**
     * 根据id删除
     * @param id 主键
     * @return 影响记录数
     */
    int deleteById(${class.pkType} id);

    /**
     * 根据id更新
     * @param ${class.className?uncap_first} 主键
     * @return 影响记录数
     */
    int update(${class.entityType} ${class.className?uncap_first});

    /**
     * 根据id获取
     * @param id 主键
     * @return 影响记录数
     */
    ${class.entityType} findById(${class.pkType} id);
}