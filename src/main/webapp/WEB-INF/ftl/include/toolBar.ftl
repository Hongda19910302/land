<#--工具栏-->
<script type="text/javascript">
    $(function () {

        //未实现的功能添加提示信息
        $(".toolBar a").click(function (e) {
            alertMsg.info('敬请期待O(∩_∩)O~');
            e.preventDefault();
        });
    });
</script>

<div class="panelBar toolBarPanel">
    <ul class="toolBar">
        <li><a class="add" href="#"><span>添加</span></a>
        </li>
        <li><a class="delete" href="#"><span>删除</span></a></li>
        <li><a class="edit" href="#"><span>修改</span></a>
        </li>
        <li class="line">line</li>
        <li><a class="icon" href="#"><span>导出EXCEL</span></a></li>
    </ul>
</div>