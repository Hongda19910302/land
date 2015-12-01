<#--单位与区域选择组件-->
<script type="text/javascript">
    $(function () {
        //初始化单位树
        initCompanyTree();

        $(".companyRegionBtn").click(function () {
            $.bringBackCompanyAndRegion(
                    {
                        pageSearchComponentId:${pageSearchComponentId},
                        companyId: $("#lookup_companyId_${pageSearchComponentId}").val(),
                        companyName: $("#lookup_companyName_${pageSearchComponentId}").val(),
                        regionId: $("#lookup_regionId_${pageSearchComponentId}").val(),
                        regionName: $("#lookup_regionName_${pageSearchComponentId}").val()
                    });
        });

    });

    /**
     * 初始化区域树
     * @param companyId 单位ID
     */
    function initRegionTree(companyId) {
        var departmentSetting = {
            async: {
                enable: true,
                url: "/comp/recursiveFindRegionTreeNode",
                autoParam: ["regionId=regionId"],
                otherParam: {"companyId": companyId}
            },
            callback: {
                //点击某个区域，保存选择的区域信息
                onClick: function (event, treeId, treeNode, clickFlag) {
                    $("#lookup_regionId_${pageSearchComponentId}").val(treeNode.regionId);
                    $("#lookup_regionName_${pageSearchComponentId}").val(treeNode.name);
                }
            }
        };

        $.fn.zTree.init($("#regionTree_${pageSearchComponentId}"), departmentSetting);
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

            callback: {
                //点击某个单位，初始化区域树
                onClick: function (event, treeId, treeNode, clickFlag) {
//                    console.log(treeNode);
                    initRegionTree(treeNode.companyId);
                    $("#lookup_companyId_${pageSearchComponentId}").val(treeNode.companyId);
                    $("#lookup_companyName_${pageSearchComponentId}").val(treeNode.name);

                    //初始化区域信息
                    $("#lookup_regionId_${pageSearchComponentId}").val("");
                    $("#lookup_regionName_${pageSearchComponentId}").val("");
                }
            }
        };

        $.fn.zTree.init($("#companyTree_${pageSearchComponentId}"), companySetting);
    }
</script>

<form>
    <input type="hidden" id="lookup_companyId_${pageSearchComponentId}"/>
    <input type="hidden" id="lookup_companyName_${pageSearchComponentId}"/>
    <input type="hidden" id="lookup_regionId_${pageSearchComponentId}"/>
    <input type="hidden" id="lookup_regionName_${pageSearchComponentId}"/>
</form>

<div class="pageContent">
    <div class="pageFormContent lookupCompanyDepartmentTree" layoutH="58">
        <div>
            <span>选择单位</span>
            <ul id="companyTree_${pageSearchComponentId}" class="ztree"></ul>
        </div>

        <div>
            <span>选择区域</span>
            <ul id="regionTree_${pageSearchComponentId}" class="ztree"></ul>
        </div>
    </div>
</div>

<div class="formBar dialogBtnRow">
    <ul>
        <li>
            <span>【说明】如果需要选择区域，请点击左边单位列表的某个具体单位</span>
        </li>
        <li>
            <div class="button">
                <div class="buttonContent companyRegionBtn">
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