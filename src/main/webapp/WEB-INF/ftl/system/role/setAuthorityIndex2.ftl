<#--角色权限设置页-->
<script type="text/javascript">
    $(function () {
        //初始化权限树
        initAuthorityTree();

        $("#setAuthority${roleId}").click(function () {
            var zTree = $.fn.zTree.getZTreeObj("authorityTree_${roleId}");
            var nodes = zTree.getCheckedNodes(true);
            var length = nodes.length;
            var ids = [];
            for (var i = 0; i < length; i++) {
                var node = nodes[i];
                if (node && node.backPrivilegeId) {
                    ids.push(node.backPrivilegeId);
                }
            }
            //console.log("ids：" + ids.join(","));
            $("#moduleIds${roleId}").val(ids.join(","));

            $("#setAuthorityForm${roleId}").submit();

        });
    });

    //初始化权限树
    function initAuthorityTree() {

        var setting = {
            check: {
                enable: true
            }
        };

        $.get("role/findAllModuleNodes2?roleId=${roleId}", function (result) {
            $.fn.zTree.init($("#authorityTree_${roleId}"), setting, result);
        });
    }

</script>

<div class="pageContent">
    <div class="pageFormContent authorityTree" layoutH="58">
        <div>
            <ul id="authorityTree_${roleId}" class="ztree"></ul>
        </div>

    </div>
</div>

<form method="post" id="setAuthorityForm${roleId}"
      onsubmit="return validateCallback(this,navTabAjaxDone)"
      action="role/setAuthority">
    <input type="hidden" name="moduleIds" id="moduleIds${roleId}">
    <input type="hidden" name="roleId" value="${roleId}">
</form>

<div class="formBar dialogBtnRow">
    <ul>
        <li>
            <div class="button">
                <div class="buttonContent">
                    <button id="setAuthority${roleId}" type="button">确定</button>
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