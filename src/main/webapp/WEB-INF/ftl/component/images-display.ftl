<#--图片展示组件-->
<div class="galleria">
<#list paths as path>
    <img src="temp/${path}">
</#list>
</div>

<script type="text/javascript">
    <#--渲染图片组件-->
    Galleria.run(".galleria", {
        theme: 'classic',
        height: 530,
        width: 790
    });

    $(function () {

        //绑定【删除全部】已上传的文件功能
        $("#delAllBtn${key}").click(function () {
            $.get("case/delUploadedFiles", {
                key: "${key}"
            }, function (data) {
                if (data && data.statusCode == 200) {
                    alertMsg.correct("已上传的文件都已安全删除！");
                    $.pdialog.closeCurrent();//关闭当前活动层
                } else {
                    alertMsg.error("系统错误，随后管理员会跟进处理！");
                }
            });


        });
    });
</script>

<#--按钮栏-->
<#if paths&&paths?size gt 0>

<form id="imagesDisplay${key}" action="case/delUploadedFiles">
    <input type="hidden" name="key" value="${key}"/>

    <div class="formBar">
        <ul>
            <li>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button
                                type="submit">删除
                        </button>
                    </div>
                </div>
            </li>
            <li>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button
                                type="button" id="delAllBtn${key}">删除全部
                        </button>
                    </div>
                </div>
            </li>
            <li>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button
                                type="button" class="close">关闭
                        </button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</form>

</#if>