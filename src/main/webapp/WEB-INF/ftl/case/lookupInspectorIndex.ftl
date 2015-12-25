<#--【巡查员列表选择】页-->
<script type="text/javascript">
    $(function () {
        //初始化部门树
        //initDepartmentTree();

        $(".companyRegionBtn").click(function () {
            $.bringBackCompanyAndRegion(
                    {
                        id:${caseId},
                        regionId: $("#lookup_regionId_${caseId}").val(),
                        regionName: $("#lookup_regionName_${caseId}").val()
                    });
        });

    });

    /**
     * 初始化巡查员树
     * @param id 部门ID
     */
    function initInspectorTree(id) {
        var setting = {
            async: {
                enable: true,
                url: "/comp/recursiveFindRegionTreeNode",
                autoParam: ["regionId=regionId"],
                otherParam: {"companyId": id}
            },
            callback: {
                //点击某个巡查员，保存选择的巡查员信息
                onClick: function (event, treeId, treeNode, clickFlag) {
                    $("#lookup_inspectorId_${caseId}").val(treeNode.regionId);
                    $("#lookup_inspectorName_${caseId}").val(treeNode.name);
                }
            }
        };

        $.fn.zTree.init($("#regionTree_${caseId}"), setting);
    }

    //初始化部门树
    function initDepartmentTree() {
        var setting = {
            async: {
                enable: true,
                url: "/comp/findAllCompany",
                //设置id名称格式规范：id参数名=server接受的参数名
                autoParam: ["companyId=companyId"]
            },

            callback: {
                //点击某个部门，初始化人员树
                onClick: function (event, treeId, treeNode, clickFlag) {
//                    console.log(treeNode);
                    initInspectorTree(treeNode.companyId);

                    //初始化巡查员信息
                    $("#lookup_inspectorId_${caseId}").val("");
                    $("#lookup_inspectorName_${caseId}").val("");
                }
            }
        };

        $.fn.zTree.init($("#departmentTree_${caseId}"), setting);
    }
</script>

<form>
    <input type="hidden" id="lookup_inspectorId_${caseId}"/>
    <input type="hidden" id="lookup_inspectorName_${caseId}"/>
</form>

<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <div>
            <span>选择部门</span>
            <ul id="departmentTree_${caseId}" class="ztree"></ul>
        </div>

        <div>
            <span>选择巡查员</span>
            <ul id="inspectorTree_${caseId}" class="ztree"></ul>
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