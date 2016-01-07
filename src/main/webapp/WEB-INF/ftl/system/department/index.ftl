<#--部门管理页-->
<#assign id="departmentIndex">

<script type="text/javascript">
    $(function () {
        //初始化单位树
        initCompanyTree();

        //【编辑部门】按钮
        $("#edit${id}").click(function () {
            var currentCompanyId = findSelectedCompanyId();//当前选择的单位ID
            if (currentCompanyId == -1) {
                alertMsg.warn("请先选择单位！");
                return;
            }

            var currentDepartment = findSelectedDepartmentId();//当前选择的部门节点
            if (currentDepartment == -1) {
                alertMsg.warn("请先选择部门！");
                return;
            }

            var currentDepartmentId = currentDepartment.departmentId;//当前选择的部门ID

            openAddOrEditDepartmentDialog("EDIT", currentCompanyId,
                    currentDepartmentId, -1);

        });

        //【新增子级部门】按钮
        $("#addChild${id}").click(function () {
            openAddBrotherOrChildDepartmentDialog("ADD_CHILD");
        });

        //【新增同级部门】按钮
        $("#addBorther${id}").click(function () {
            openAddBrotherOrChildDepartmentDialog("ADD_BROTHER");
        });

        //【新增顶级部门】按钮
        $("#addTop${id}").click(function () {
            var currentCompanyId = findSelectedCompanyId();//当前选择的单位ID
            if (currentCompanyId == -1) {
                alertMsg.warn("请先选择单位！");
                return;
            }
            openAddOrEditDepartmentDialog("ADD_TOP", currentCompanyId, -1, -1);

        });
    });

    //打开新增同级或子部门对话框
    function openAddBrotherOrChildDepartmentDialog(operateType) {
        var currentCompanyId = findSelectedCompanyId();//当前选择的单位ID
        if (currentCompanyId == -1) {
            alertMsg.warn("请先选择单位！");
            return;
        }

        var currentDepartment = findSelectedDepartmentId();//当前选择的部门节点
        if (currentDepartment == -1) {
            alertMsg.warn("请先选择部门！");
            return;
        }
        var currentDepartmentId = currentDepartment.departmentId;//当前选择的部门ID
        var currentDepartmentParentId = currentDepartment.parentId;//当前选择的部门的父部门ID
        if (!currentDepartmentParentId) {
            currentDepartmentParentId = -1;
        }

        openAddOrEditDepartmentDialog(operateType, currentCompanyId,
                currentDepartmentId, currentDepartmentParentId);
    }

    //查找已选择的部门ID，返回选中的节点
    function findSelectedDepartmentId() {
        var treeObj = $.fn.zTree.getZTreeObj("departmentTree_${id}");
        var nodes = treeObj.getSelectedNodes();
        if (!nodes || nodes.length == 0) {
            return -1;
        } else {
            return nodes[0];
        }
    }

    //查找已选择的单位ID，返回单位ID
    function findSelectedCompanyId() {
        var treeObj = $.fn.zTree.getZTreeObj("companyTree_${id}");
        var nodes = treeObj.getSelectedNodes();
        if (!nodes || nodes.length == 0) {
            return -1;
        } else {
            return nodes[0].companyId;
        }
    }

    //打开新建或编辑部门对话框
    function openAddOrEditDepartmentDialog(operateType, currentCompanyId,
                                           currentDepartmentId, currentDepartmentParentId) {
        var dialogName = "";//对话框名称
        switch (operateType) {
            case "ADD_TOP":
                dialogName = "新建顶级部门";
                break;
            case "ADD_BROTHER":
                dialogName = "新增同级部门";
                break;
            case "ADD_CHILD":
                dialogName = "新增子部门";
                break;
            case "EDIT":
                dialogName = "编辑";
                break;
        }

        $.pdialog.open("department/addOrEditIndex?componentId=5&currentCompanyId=" +
                currentCompanyId +
                "&currentDepartmentId=" + currentDepartmentId +
                "&currentDepartmentParentId=" + currentDepartmentParentId + "&operateType=" +
                operateType,
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
    }

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
                    <button id="addTop${id}"
                            type="button">新增顶级部门
                    </button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button id="addBorther${id}" type="button">新增同级部门</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button id="addChild${id}" type="button">新增子部门</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button id="edit${id}" type="button">编辑</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button id="delete${id}" type="button">删除</button>
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