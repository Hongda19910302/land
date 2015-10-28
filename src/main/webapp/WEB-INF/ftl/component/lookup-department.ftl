<#--单位与部门查找带回组件-->
<script type="text/javascript">
    $(function () {
        var nodes = [
            {name: "父节点1", children: [
                {name: "子节点1"},
                {name: "子节点2"}
            ]}
        ];

        var ztreeSetting={
            async:{
                enable:true,
                url:"/comp/findDepartmentTreeNode",
                autoParam:["id=departmentId"],
                otherParam:{"companyId":31}
            }
        };

        $.fn.zTree.init($("#companyDepartmentTree"),ztreeSetting);
    });
</script>


<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <ul id="companyDepartmentTree" class="ztree"></ul>
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