<#--单位与部门选择组件-->
<script type="text/javascript">
    $(function () {

        var companySetting = {
            async: {
                enable: true,
                url: "/comp/findAllCompany",
                //设置id名称格式规范：id参数名=server接受的参数名
                autoParam: ["companyId=companyId"]
            }
        };

        $.fn.zTree.init($("#companyTree"), companySetting);

        var departmentSetting={
            async:{
                enable:true,
                url:"/comp/findDepartmentTreeNode",
                autoParam:["departmentId=departmentId"],
                otherParam:{"companyId":31}
            }
        };

        $.fn.zTree.init($("#departmentTree"),departmentSetting);
    });


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