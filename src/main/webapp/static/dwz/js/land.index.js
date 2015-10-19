/**
 * Created by deniro on 2015/10/13.
 */
$(function () {

    bindingExitBtn();
    //renderAllMenu();

});

/**
 * 获取可查看的所有菜单模块并渲染
 */
function renderAllMenu() {
    $.get("/menu/findAll", function (data, textStatus) {
//                console.log(data);
        var html = new Array();
        $.each(data, function (index, top) {
            //渲染主菜单模块
            html.push('<div class="accordionHeader">');
            html.push('<h2><span>Folder</span>' + top.name + '</h2>');
            html.push('</div>');

            //渲染子菜单模块
            html.push('<div class="accordionContent">');
            html.push('<ul class="tree treeFolder">');
            $.each(top.child, function (index, child) {
                html.push('<li><a href="' + child.url + '" target="navTab" rel="child_menu_' + child.backPrivilegeId + '">' + child.name + '</a>');
                html.push('</li>');
            });
            html.push('</ul>');
            html.push('</div>');
        });
//                console.log(html.join(""));
        $(".accordion").html(html.join(""));

        //渲染菜单UI
        //console.log(navTab.getCurrentPanel());
        $('div.accordion' ,navTab.getCurrentPanel()).each(function(){
            var $this = $(this);
            $this.accordion({fillSpace:$this.attr("fillSpace"),alwaysOpen:true,active:0});
        });
    });
}

/**
 * 绑定退出（登出）按钮
 */
function bindingExitBtn() {
    $("#exit").click(function () {
        location.href = "/user/exit";
    });
}


