<div class="pageContent">
    <table class="list" width="100%" layoutH="76">
        <thead>
        <tr>
            <th width="120">操作者</th>
            <th width="120">发送者</th>
            <th width="120">接收者</th>
            <th width="120">操作类型</th>
            <th width="120">操作时间</th>
        </tr>
        </thead>
        <tbody>
        <#if flowRecords??>
            <#list flowRecords as record>
            <tr>
                <td>${record.operatorName}</td>
                <td>${record.fromName}</td>
                <td>${record.toName}</td>
                <td>${record.operationType}</td>
                <td>${record.createTime}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>