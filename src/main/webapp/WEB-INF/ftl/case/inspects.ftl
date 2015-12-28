<div class="pageContent">
    <table class="list flowRecords" width="100%" layoutH="76">
        <thead>
        <tr>
            <th width="120">巡查序号</th>
            <th width="120">巡查时间</th>
            <th width="120">巡查结果</th>
            <th width="120">备注</th>
            <th width="60">单据文书</th>
            <th width="60">巡查照片</th>
        </tr>
        </thead>
        <tbody>
        <#if inspects??>
            <#list inspects as inspect>
            <tr>
                <td>${inspect.inspectNo}</td>
                <td>${inspect.createTime}</td>
                <td>${inspect.inspectResultName}</td>
                <td>${inspect.remark}</td>
                <td>
                    <a class="btnLook"
                       href="/case/lookupInspectUploadedFiles?key=caseDocuments&id=${inspect.inspectId}&pattern=DISPLAY"
                       target="dialog"
                       rel="lookupInspectUploadedFiles"
                       mask="true" minable="false" height="600"
                       width="800"
                       resizable="false"
                       maxable="false"
                       title="已上传的单据"></a>
                </td>
                <td>
                    <a class="btnLook"
                       href="/case/lookupInspectUploadedFiles?key=illegalPhotos&id=${inspect.inspectId}&pattern=DISPLAY"
                       target="dialog"
                       rel="lookupInspectUploadedFiles"
                       mask="true" minable="false" height="600"
                       width="800"
                       resizable="false"
                       maxable="false"
                       title="已上传的照片"></a>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

