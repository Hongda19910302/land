<div class="panelBar">
    <ul class="toolBar">
        <li><a id="edit_${componentId}" class="edit"><span>详情</span></a>
        </li>
    </ul>
</div>

<script type="text/javascript">
    $(function () {
        /**
         *
         * 工具按钮脚本
         */
        var $toolBarBtn = $("#edit_${componentId}");
        $toolBarBtn.live("click", function (event) {
            console.log($("#table_tbody_${componentId}").length);
            var $tbody = $("#table_tbody_${componentId}");
            if ($tbody.length == 0) {//没有数据，提示
                alertMsg.warn("没有数据，无需处理！");
                event.preventDefault();
                return;
            }

            var $select = $tbody.find(".selected");
            if ($select.length == 0) {//未选择某条记录，提示
                alertMsg.warn("请选择某条记录！")
                event.preventDefault();
                return;
            }

            //console.log($select.attr("rel"));
            var selectedId = $select.attr("rel");//已选择的记录ID
            var url = "version/items?componentType=PAGE_SEARCH&componentId" +
                    "=9&versionId=" + selectedId;
            navTab.openTab(${componentId}+selectedId, url, {
                title: "版本项" +
                "(" + selectedId + ")", fresh: false,
                data: {}
            });

            /**
             * 弹出对话框
             */
            $.pdialog.open(url, ${componentId}+selectedId, "版本项" +
            "(" + selectedId + ")", {
                //高度
                height: 300,
                //宽度
                width: 580,
                //最小高度
                minH: 40,
                //最小宽度
                minW: 50,
                //是否最大化打开
                max: false,
                //是否使用遮罩
                mask: true,
                //是否可缩放
                resizable: true,
                //是否可拖拉
                drawable: true,
                //是否有【最大化】功能
                maxable: true,
                //是否有【最小化】功能
                minable: false,
                //再次点击时是否刷新数据
                fresh: true
            });
        });
    });
</script>