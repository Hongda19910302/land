<#--单位与区域选择组件-->
<script type="text/javascript">
    $(function () {
        //初始化单位树
        initCompanyTree();

        $(".companyDepartmentBtn").click(function () {
            $.bringBackCompanyAndDepartment(
                    {
                        pageSearchComponentId:${pageSearchComponentId},
                        companyId: $("#companyId_${pageSearchComponentId}").val(),
                        companyName: $("#companyName_${pageSearchComponentId}").val(),
                        departmentId: $("#departmentId_${pageSearchComponentId}").val(),
                        departmentName: $("#departmentName_${pageSearchComponentId}").val()
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
            callback: {
                //点击某个部门，保存选择的部门信息
                onClick: function (event, treeId, treeNode, clickFlag) {
                    $("#departmentId_${pageSearchComponentId}").val(treeNode.departmentId);
                    $("#departmentName_${pageSearchComponentId}").val(treeNode.name);
                }
            }
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

<form>
    <input type="hidden" id="companyId_${pageSearchComponentId}"/>
    <input type="hidden" id="companyName_${pageSearchComponentId}"/>
    <input type="hidden" id="departmentId_${pageSearchComponentId}"/>
    <input type="hidden" id="departmentName_${pageSearchComponentId}"/>
</form>

<div class="pageContent">
    <div class="pageFormContent lookupCompanyDepartmentTree" layoutH="58">
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