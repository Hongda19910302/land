<#--行政区域管理-->

<script type="text/javascript">
    $(function () {
        var setting = {
            async: {
                enable: true,
                url: "/comp/findAllRegionTreeNode",
                autoParam: ["regionId=regionId"]
            },
            callback: {
                //点击某个区域，保存选择的区域信息
                onClick: function (event, treeId, treeNode, clickFlag) {
                    <#--$("#tree_regionId_${componentId}").val(treeNode.regionId);-->
                    <#--$("#tree_regionName_${componentId}").val(treeNode.name);-->
                }
            }
        };

        $.fn.zTree.init($("#regionIndexTree"), setting);
    });
</script>
<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
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