<#--行政区域管理-->

<script type="text/javascript">
    $(function () {
        var setting = {
            async: {
                enable: true,
                url: "/comp/findAllRegionTreeNode",
                autoParam: ["regionId=regionId"]
            },
            edit: {
                enable: true,
                showRemoveBtn: true,
                showRenameBtn: true
            },
            callback: {}
        };

        $.fn.zTree.init($("#regionIndexTree"), setting);
    });
</script>
<div class="pageContent">
    <div class="pageFormContent" layoutH="238">
        <div>
            <ul id="regionIndexTree" class="ztree regionTree"></ul>
        </div>
    </div>
</div>

<div class="formBar">
    <ul>
        <li>
            <div class="button">
                <div class="buttonContent">
                    <button type="button">新增同级区域</button>
                </div>
            </div>
            <div class="button">
                <div class="buttonContent">
                    <button type="button">新增子区域</button>
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