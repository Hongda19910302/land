<#--【立案审核】填写意见页-->
<script type="text/javascript">
    $(function () {
        $("#registerAuditYes${caseId}").click(function () {
            $("#registerAuditOpinionIndex${caseId}").attr("action",
                    "case/registerAudit?auditResultCode=0&caseId=${caseId}");
            $("#registerAuditOpinionIndex${caseId}").submit();
        });
        $("#registerAuditNo${caseId}").click(function () {
            $("#registerAuditOpinionIndex${caseId}").attr("action",
                    "case/registerAudit?auditResultCode=1&caseId=${caseId}");
            $("#registerAuditOpinionIndex${caseId}").submit();
        });
    });
</script>

<div class="pageContent">
    <form method="post" id="registerAuditOpinionIndex${caseId}" class="pageForm"
          onsubmit="return validateCallback(this,navTabAjaxDone)"
          action="case/registerAudit">
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
                            <button type="submit" id="registerAuditYes${caseId}">立案
                            </button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit" id="registerAuditNo${caseId}">撤销案件
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