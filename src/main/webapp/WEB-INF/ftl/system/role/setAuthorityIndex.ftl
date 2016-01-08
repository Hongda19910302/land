<#--角色权限设置页-->
<script type="text/javascript">
    $(function () {
        //初始化权限树
        initAuthorityTree();
    });

    //初始化权限树
    function initAuthorityTree() {
        var setting = {
            async: {
                enable: true,
                url: "/role/findAllModuleNodes",
                //设置id名称格式规范：id参数名=server接受的参数名
                autoParam: ["backPrivilegeId=backPrivilegeId"]
            },

            callback: {
            }
        };

        $.fn.zTree.init($("#authorityTree_${roleId}"), setting);
    }
</script>

<div class="pageContent">
    <div class="pageFormContent authorityTree" layoutH="58">
        <div>
            <ul id="authorityTree_${roleId}" class="ztree"></ul>
        </div>

    </div>
</div>

<div class="formBar dialogBtnRow">
    <ul>
        <li>
            <div class="button">
                <div class="buttonContent">
                    <button type="button">确定</button>
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