<#--脚本-->
<script type="text/javascript">
    $(function () {
        //查询条件初始化
    <#list moduleSearchCfg as cfg>
        $("#${cfg.fieldName}_${moduleId}").val("${queryParam[cfg.fieldName]!""}");
    </#list>

        //绑定重置按钮
        $("#resetBtn_${moduleId}").click(function () {
            $("#searchForm_${moduleId}").clearForm();
        });

    });
</script>