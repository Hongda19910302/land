<#--【立案审核】填写意见页-->
<div class="pageContent">
    <form method="post" id="${caseId}" class="pageForm"
          onsubmit="return validateCallback(this,navTabAjaxDone)">
        <div class="pageFormContent" layoutH="56">
            <dl>
                <dt>审核意见：</dt>
                <dd><textarea name="${item.inputName}" class="required" cols="46"
                              rows="10"></textarea></dd>
            </dl>
        </div>

    <#--按钮栏-->
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">立案
                            </button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">撤销案件
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