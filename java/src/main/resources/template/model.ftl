package ${rootPackage};

import lombok.Data;

@Data
public class ${model.name?cap_first}${genericStr} <#if (model.baseClassName)??> extends ${model.baseClassName}</#if> {
<#if model.hasPublicProperties == true>
    /**
    * id
    */
    private Long id;
</#if>
<#list model.properties as c>

    <#if (c.comment)??>
    /**
    * ${c.comment}
    */
    </#if>
    private ${c.type} ${c.name};
</#list>
}
