<#--部门管理页-->
<#assign id="departmentIndex">

<script type="text/javascript">
    $(function () {
        //初始化单位树
        initCompanyTree();

        $("#addCompanyDepartment${id}").click(function () {

            var treeObj = $.fn.zTree.getZTreeObj("companyTree_${id}");
            var nodes = treeObj.getSelectedNodes();
            if (!nodes || nodes.length == 0) {
                alertMsg.warn("请先选择某个单位！");
                return;
            }

            var currentNode = nodes[0];//当前选择的节点
            var currentCompanyId = currentNode.companyId;//当前选择的单位ID

            var operateType = "ADD_TOP";//操作类型
            var dialogName = "";//对话框名称
            switch (operateType) {
                case "ADD_TOP":
                    dialogName = "新建顶级部门";
                    break;
            }


            $.pdialog.open("department/addOrEditIndex?componentId=5&currentCompanyId=" +
                    currentCompanyId + "&operateType=" + operateType,
                    "addOrEditDepartmentIndex",
                    dialogName, {
                        //高度
                        height: 500,
                        //宽度
                        width: 800,
                        //是否使用遮罩
                        mask: true,
                        //是否可拖拉
                        drawable: true,
                        //是否有【最大化】功能
                        maxable: true
                    });
        });
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

        $.fn.zTree.init($("#departmentTree_${id}"), departmentSetting);
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
                    $("#companyId_${id}").val(treeNode.companyId);
                    $("#companyName_${id}").val(treeNode.name);

                    //初始化部门信息
                    $("#departmentId_${id}").val("");
                    $("#departmentName_${id}").val("");
                }
            }
        };

        $.fn.zTree.init($("#companyTree_${id}"), companySetting);
    }
</script>

<div class="pageContent">
    <div class="pageFormContent departmentManagementTree" layoutH="238">
        <div>
            <span>选择单位</span>
            <ul id="companyTree_${id}" class="ztree"></ul>
        </div>

        <div>
            <span>选择部门</span>
            <ul id="departmentTree_${id}" class="ztree"></ul>
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
                    <button id="addCompanyDepartment${id}"
                            type="button">新增顶级部门
                    </button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button type="button">新增同级部门</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button type="button">新增子部门</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button type="button">编辑</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button type="button">删除</button>
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