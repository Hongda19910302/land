<#--分页条-->
<div class="panelBar pageBar">
    <div class="pages">
        <span>显示</span>
        <select class="combox perPageNumCombox" name="numPerPage"
                onchange="navTabPageBreak({numPerPage:this.value})">
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
            <option value="200">200</option>
        </select>
        <span>条，共 ${page.totalCount}  条</span>
    </div>
    <div class="pagination" targetType="navTab" totalCount="${page.totalCount}"
         numPerPage="${page.pageSize}" currentPage="${page.start / page.pageSize + 1}"
</div>