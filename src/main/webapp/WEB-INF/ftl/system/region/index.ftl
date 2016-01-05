<#--行政区域管理-->

<script type="text/javascript">
    $(function () {
        initRegionIndexTree();


        //打开新增区域对话框
        $("#regionIndexAddBrother").click(function () {

            //判断是否有选择某个区域，如果未选择，则弹出提示框
            var treeObj= $.fn.zTree.getZTreeObj("regionIndexTree");
            var nodes=treeObj.getSelectedNodes();
            if(!nodes||nodes.length==0){
                alertMsg.warn("请先选择某个区域！");
                return;
            }

            $.pdialog.open("region/addOrEditIndex?componentId=3", "regionIndexAddIndex",
                    "新增区域", {
                //高度
                height: 500,
                //宽度
                width: 600,
                //是否使用遮罩
                mask: true,
                //是否可拖拉
                drawable: true,
                //是否有【最大化】功能
                maxable: true
            });

        });
    });

    //初始化区域树
    function initRegionIndexTree() {
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
    }
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
                    <button id="regionIndexAddBrother" type="button">新增同级区域</button>
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