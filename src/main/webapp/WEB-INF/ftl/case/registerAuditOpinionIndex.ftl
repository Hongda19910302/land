<#--【立案审核】填写意见页-->
<script type="text/javascript">
    $(function () {
        //审核通过
        $("#registerAuditYes${caseId}").click(function () {

            var inspectorName=$("#inspectorName${caseId}").val();
            if(inspectorName.length==0){
                alertMsg.warn("您还未选择巡查员！");
                return false;
            }

            $("#registerAuditOpinionIndex${caseId}").attr("action",
                    "case/registerAudit?auditResultCode=0&caseId=${caseId}");
            $("#registerAuditOpinionIndex${caseId}").submit();
        });

        //审核不通过
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
        <input type="hidden" name="inspectorId">
        <div class="pageFormContent" layoutH="56">
            <dl>
                <dt>巡查员：</dt>
                <dd><input type="text" id="inspectorName${caseId}" name="inspectorName"
                           readonly="true"></dd>
            </dl>
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