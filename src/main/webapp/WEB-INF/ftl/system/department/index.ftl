<#--部门管理页-->
<script type="text/javascript">
    $(function () {
        //初始化单位树
        initCompanyTree();
    });

    /**
     * 初始化部门树
     * @param companyId 单位ID
     */
    function initDepartmentTree(companyId) {
        var departmentSetting = {
            async: {
                enable: true,
                url: "/comp/findDepartmentTreeNode",
                autoParam: ["departmentId=departmentId"],
                otherParam: {"companyId": companyId}
            },
            callback: {}
        };

        $.fn.zTree.init($("#departmentTree_${pageSearchComponentId}"), departmentSetting);
    }

    //初始化单位树
    function initCompanyTree() {
        var companySetting = {
            async: {
                enable: true,
                url: "/comp/findAllCompany",
                //设置id名称格式规范：id参数名=server接受的参数名
                autoParam: ["companyId=companyId"]
            },

            callback: {
                //点击某个单位，初始化部门树
                onClick: function (event, treeId, treeNode, clickFlag) {
//                    console.log(treeNode);
                    initDepartmentTree(treeNode.companyId);
                    $("#companyId_${pageSearchComponentId}").val(treeNode.companyId);
                    $("#companyName_${pageSearchComponentId}").val(treeNode.name);

                    //初始化部门信息
                    $("#departmentId_${pageSearchComponentId}").val("");
                    $("#departmentName_${pageSearchComponentId}").val("");
                }
            }
        };

        $.fn.zTree.init($("#companyTree_${pageSearchComponentId}"), companySetting);
    }
</script>

<div class="pageContent">
    <div class="pageFormContent departmentManagementTree" layoutH="238">
        <div>
            <span>选择单位</span>
            <ul id="companyTree_${pageSearchComponentId}" class="ztree"></ul>
        </div>

        <div>
            <span>选择部门</span>
            <ul id="departmentTree_${pageSearchComponentId}" class="ztree"></ul>
        </div>
    </div>
</div>

<div class="formBar dialogBtnRow">
    <ul>
        <li>
            <span>【说明】如果需要选择部门，请点击左边单位列表的某个具体单位</span>
        </li>
        <li>
            <div class="button">
                <div class="buttonContent">
                    <button class="companyDepartmentBtn" type="button">确定</button>
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