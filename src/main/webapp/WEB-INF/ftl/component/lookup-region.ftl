<#--地区选择组件-->
<script type="text/javascript">
    $(function () {
        //初始化区域树
        initRegionTree();

        $(".regionBtn").click(function () {
            $.bringBackRegion(
                    {
                        pageSearchComponentId:${pageSearchComponentId},
                        regionId: $("#tree_regionId_${pageSearchComponentId}").val(),
                        regionName: $("#tree_regionName_${pageSearchComponentId}").val()
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
                    $("#tree_regionId_${pageSearchComponentId}").val(treeNode.regionId);
                    $("#tree_regionName_${pageSearchComponentId}").val(treeNode.name);
                }
            }
        };

        $.fn.zTree.init($("#regionTree_${pageSearchComponentId}"), setting);
    }
</script>

<form>
    <input type="hidden" id="tree_regionId_${pageSearchComponentId}"/>
    <input type="hidden" id="tree_regionName_${pageSearchComponentId}"/>
</form>

<div class="pageContent">
    <div class="pageFormContent lookupTree" layoutH="58">
        <div>
            <ul id="regionTree_${pageSearchComponentId}" class="ztree"></ul>
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