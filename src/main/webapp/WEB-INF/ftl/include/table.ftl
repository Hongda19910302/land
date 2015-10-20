<#--表格主键-->
<#assign tableKey="">

<#--表格容器-->
<table class="table" width="100%" layoutH="138">
    <thead>
    <tr>
    <#list moduleTableCfg as cfg>
        <#--找到主键并赋值-->
        <#if cfg.iskey=="true">
            <#assign tableKey=cfg.fieldName>

        <#--渲染表格头标题-->
        <#else>
            <th width="${cfg.width}" style="${cfg.style!""}">${cfg.displayName}</th>
        </#if>
    </#list>
    </tr>
    </thead>
    <tbody>

    <#--渲染表格内容-->
    <#list page.data as data>
    <tr rel="${data[tableKey]}">

        <#list moduleTableCfg as cfg>

        <#--非主键展示-->
            <#if cfg.iskey=="false">

                <td
                <#--添加样式-->
                    <#if cfg.style??>
                            style="${cfg.style}"
                    </#if>
                        >

                <#--字段值-->
                <#assign fieldValue=data[cfg.fieldName]>

                <#--数据转换后展示-->
                <#if cfg.transformDataSetType??>
                    <#list cfg.transformDataSet?keys as key>
                        <#if key=="${fieldValue}">
                        ${cfg.transformDataSet[key]}
                        </#if>
                    </#list>

                <#--普通展示-->
                <#else>
                ${fieldValue}
                </#if>

                </td>
            </#if>
        </#list>

    </tr>
    </#list>
    </tbody>
</table>