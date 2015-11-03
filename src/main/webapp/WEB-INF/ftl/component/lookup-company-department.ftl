<#--单位与部门选择组件-->
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
            }
        };

        $.fn.zTree.init($("#departmentTree"), departmentSetting);
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

            callback:{
                //点击某个单位，初始化部门树
                onClick: function (event,treeId,treeNode,clickFlag) {
//                    console.log(treeNode);
                    initDepartmentTree(treeNode.companyId);
                }
            }
        };

        $.fn.zTree.init($("#companyTree"), companySetting);

    }
</script>


<div class="pageContent">
    <div class="pageFormContent lookupCompanyDepartmentTree" layoutH="58">
        <div>
            <span>选择单位</span>
            <ul id="companyTree" class="ztree"></ul>
        </div>

        <div>
            <span>选择部门</span>
            <ul id="departmentTree" class="ztree"></ul>
        </div>
    </div>
</div>

<div class="formBar">
    <ul>
        <li>
            <div class="button">
                <div class="buttonContent">
                    <button class="close" type="button">关闭</button>
                </div>
            </div>
        </li>
    </ul>
</div>