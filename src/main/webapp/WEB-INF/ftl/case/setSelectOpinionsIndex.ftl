<#--设置下拉项页-->
<script type="text/javascript">
    $(function () {
        //初始化树
        initSelectOpinionsTree();

        $("#setSelectOpinions${variableFieldId}").click(function () {
            var zTree = $.fn.zTree.getZTreeObj("selectOpinionsTree_${variableFieldId}");
            var nodes = zTree.getCheckedNodes(true);
            var length = nodes.length;
            var ids = [];
            for (var i = 0; i < length; i++) {
                var node = nodes[i];
                if (node && node.id) {
                    ids.push(node.id);
                }
            }
//            console.log("ids：" + ids.join(","));
            $("#selectedIds${variableFieldId}").val(ids.join(","));

            $("#setSelectOpinionsForm${variableFieldId}").submit();

        });
    });

    //初始化树
    function initSelectOpinionsTree() {

        var setting = {
            check: {
                enable: true
            }
        };

        $.get("variableField/findAllSelectOpinionNodes?variableFieldId=${variableFieldId}", function (result) {
            $.fn.zTree.init($("#selectOpinionsTree_${variableFieldId}"), setting, result);
        });
    }

</script>

<div class="pageContent">
    <div class="pageFormContent selectOpinionsTree" layoutH="58">
        <div>
            <ul id="selectOpinionsTree_${variableFieldId}" class="ztree"></ul>
        </div>

    </div>
</div>

<form method="post" id="setSelectOpinionsForm${variableFieldId}"
      onsubmit="return validateCallback(this,navTabAjaxDone)"
      action="variableField/setSelectOpinions">
    <input type="hidden" name="selectedIds" id="selectedIds${variableFieldId}">
    <input type="hidden" name="variableFieldId" value="${variableFieldId}">
</form>

<div class="formBar dialogBtnRow">
    <ul>
        <li>
            <div class="button">
                <div class="buttonContent">
                    <button id="setSelectOpinions${variableFieldId}" type="button">确定</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button class="close" type="button">关闭</button>
                </div>
            </div>
        </li>
    </ul>
</div>