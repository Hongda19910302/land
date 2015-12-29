<#--【结案审核】填写意见页-->
<script type="text/javascript">
    $(function () {
        //审核通过
        $("#closeAuditYes${caseId}").click(function () {
            $("#closeAuditOpinionIndex${caseId}").attr("action",
                    "case/closeAudit?result=0&caseId=${caseId}");
            $("#closeAuditOpinionIndex${caseId}").submit();
        });

        //审核不通过
        $("#closeAuditNo${caseId}").click(function () {
            $("#closeAuditOpinionIndex${caseId}").attr("action",
                    "case/closeAudit?result=1&caseId=${caseId}");
            $("#closeAuditOpinionIndex${caseId}").submit();
        });
    });
</script>

<div class="pageContent">
    <form method="post" id="closeAuditOpinionIndex${caseId}" class="pageForm"
          onsubmit="return validateCallback(this,navTabAjaxDone)"
          action="case/closeAudit">
        <div class="pageFormContent" layoutH="56">
            <dl>
                <dt>审核意见：</dt>
                <dd><textarea name="opinion" class="required" cols="46"
                              rows="10"></textarea></dd>
            </dl>
        </div>

    <#--按钮栏-->
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="button" id="closeAuditYes${caseId}">结案
                            </button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="button" id="closeAuditNo${caseId}">驳回
                            </button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="button" class="close">取消
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>