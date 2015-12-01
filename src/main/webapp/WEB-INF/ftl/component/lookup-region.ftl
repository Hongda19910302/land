<#--地区选择组件-->
<script type="text/javascript">
    $(function () {
        //初始化区域树
        initRegionTree();

        $(".regionBtn").click(function () {
            $.bringBackRegion(
                    {
                        componentId:"${componentId}",
                        regionId: $("#tree_regionId_${componentId}").val(),
                        regionName: $("#tree_regionName_${componentId}").val(),
                        regionId_${componentId}: $("#tree_regionId_${componentId}").val(),
                        regionName_${componentId}: $("#tree_regionName_${componentId}")
            .val()
                    });
        });

    });


    //初始化区域树
    function initRegionTree() {
        var setting = {
            async: {
                enable: true,
                url: "/comp/findRegionTreeNode",
                autoParam: ["regionId=regionId"]
            },
            callback: {
                //点击某个区域，保存选择的区域信息
                onClick: function (event, treeId, treeNode, clickFlag) {
                    $("#tree_regionId_${componentId}").val(treeNode.regionId);
                    $("#tree_regionName_${componentId}").val(treeNode.name);
                }
            }
        };

        $.fn.zTree.init($("#regionTree_${componentId}"), setting);
    }
</script>

<form>
    <input type="hidden" id="tree_regionId_${componentId}"/>
    <input type="hidden" id="tree_regionName_${componentId}"/>
</form>

<div class="pageContent">
    <div class="pageFormContent lookupTree" layoutH="58">
        <div>
            <ul id="regionTree_${componentId}" class="ztree"></ul>
        </div>

    </div>
</div>

<div class="formBar dialogBtnRow">
    <ul>
        <li>
            <span>【说明】双击可以展开下一级区域</span>
        </li>
        <li>
            <div class="button">
                <div class="buttonContent">
                    <button class="regionBtn" type="button">确定</button>
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