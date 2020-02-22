<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${xml.namespace}">

    <resultMap id="BaseResultMap" type="${xml.type}">
        <id column="${xml.id["column"]}" jdbcType="${xml.id["jdbcType"]}" property="${xml.id["javaName"]}" />
        <#list xml.resultMaps as resultMap>
        <result column="${resultMap["column"]}" jdbcType="${resultMap["jdbcType"]}" property="${resultMap["javaName"]}"/>
        </#list>
    </resultMap>

    <sql id="Base_Column_List" >
        ${xml.id["column"]}, <#list xml.resultMaps as resultMap>${resultMap["column"]}<#if resultMap_has_next>, </#if></#list>
    </sql>

    <insert id="insert" parameterType="${xml.type}">
        <#if xml.id["generatorKeys"]=="true">
        <selectKey resultType="${xml.id["javaType"]}" keyProperty="${xml.id["javaName"]}" order="AFTER">
            SELECT LAST_INSERT_ID()
        </selectKey>
        </#if>
        insert into ${xml.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="${xml.id["javaName"]} != null" >
                ${xml.id["column"]},
            </if>
            <#list xml.resultMaps as resultMap>
            <if test="${resultMap["javaName"]} != null" >
                ${resultMap["column"]},
            </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides="," >
            <if test="${xml.id["javaName"]} != null" >
                ${r"#"}{${xml.id["javaName"]},jdbcType=${xml.id["jdbcType"]}},
            </if>
            <#list xml.resultMaps as resultMap>
            <if test="${resultMap["javaName"]} != null" >
               ${r"#"}{${resultMap["javaName"]},jdbcType=${resultMap["jdbcType"]}},
            </if>
            </#list>
        </trim>
    </insert>

    <delete id="deleteById" parameterType="${xml.id["javaType"]}">
        delete from ${xml.tableName}
        where ${xml.id["column"]} = ${r"#"}{${xml.id["javaName"]},jdbcType=${xml.id["jdbcType"]}}
    </delete>

    <update id="update" parameterType="${xml.type}">
        update ${xml.tableName}
        <set>
            <#list xml.resultMaps as resultMap>
            <if test="${resultMap["javaName"]} != null" >
                ${resultMap["column"]} = ${r"#"}{${resultMap["javaName"]},jdbcType=${resultMap["jdbcType"]}},
            </if>
            </#list>
        </set>
        where ${xml.id["column"]} = ${r"#"}{${xml.id["javaName"]},jdbcType=${xml.id["jdbcType"]}}
    </update>

    <select id="findById" parameterType="${xml.id["javaType"]}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${xml.tableName}
        where ${xml.id["column"]} = ${r"#"}{${xml.id["javaName"]},jdbcType=${xml.id["jdbcType"]}}
    </select>

</mapper>