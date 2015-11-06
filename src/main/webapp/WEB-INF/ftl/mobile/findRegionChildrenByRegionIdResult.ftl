{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"list":[
<#if regionList??>
    <#list regionList as data>
    {
    "orgId":"${(data.regionId)!''}",
    "orgName":"${(data.name)!''}",
    "parentId":"${(data.getParentRegion().regionId)!''}",
    "orgLevel":"${(data.regionLevel)!''}"
    }
        <#if data_has_next>,</#if>
    </#list>
</#if>
]
}