<div class="pageContent">
    <table class="list flowRecords" width="100%" layoutH="76">
        <thead>
        <tr>
            <th width="120">操作者</th>
            <#--<th width="120">下一节点操作者</th>-->
            <#--<th width="120">下一节点操作者</th>-->
            <th width="120">说明</th>
            <th width="120">操作类型</th>
            <th width="120">操作时间</th>
        </tr>
        </thead>
        <tbody>
        <#if flowRecords??>
            <#list flowRecords as record>
            <tr>
                <td>${record.operatorName}</td>
                <#--<td>${record.fromName}</td>-->
                <#--<td>${record.toName}</td>-->
                <td>${record.operation}</td>
                <td>${record.operationTypeInDisplay}</td>
                <td>${record.createTime}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>