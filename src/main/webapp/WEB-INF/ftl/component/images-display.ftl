<#--图片展示组件-->

<#--文件路径前缀-->
<#assign filePathPrefix="temp/"/>

<div class="galleria">
<#list ftpUploadFiles as file>
    <#switch file.fileSource>
        <#case "FTP"><#-- 来自于FTP地址 -->
            <img src="${file.filePath}">
            <#break >
        <#case "TEMP"><#-- 来自于临时文件夹地址 -->
            <img src="${filePathPrefix}${file.fileName}">
            <#break >
    </#switch>
</#list>
</div>

<script type="text/javascript">
    <#--渲染图片组件-->
    Galleria.run(".galleria", {
        theme: 'classic',
        height: 530,
        width: 790
    });

    $(function () {

        //绑定【删除全部】已上传的文件
        $("#delAllBtn${key}").click(function () {
            $.get("case/delUploadedFiles", {
                key: "${key}"
            }, function (data) {
                if (data && data.statusCode == 200) {
                    alertMsg.correct("已上传的文件都已安全删除！");
                    $.pdialog.closeCurrent();//关闭当前活动层
                } else {
                    alertMsg.error("删除失败！");
                }
            });
        });

        //绑定【删除当前】已上传的文件
        $("#delCurrentBtn${key}").click(function () {
            var galleriaObj = $(".galleria").data("galleria");

            //获取当前文件名
            var $img = $(galleriaObj.getActiveImage());
            var src = $img.attr("src");

            var fileName;

        <#switch file.fileSource>
            <#case "FTP"><#-- 来自于FTP地址 -->
                fileName = src.substr("${filePathPrefix}".length);
                <#break >
            <#case "TEMP"><#-- 来自于临时文件夹地址 -->
                fileName = src.substr("${ftpHttpUrl}".length);
                <#break >
        </#switch>
            console.log("fileName:" + fileName);

            //删除当前文件
            $.get("case/delUploadedFiles", {
                key: "${key}",
                fileName: fileName,
                fileSource: "${file.fileSource}",
                filePath:src,
                id: "${id}"
            }, function (data) {
                if (data && data.statusCode == 200) {
                    alertMsg.correct("已选择文件已安全删除！");


                    var length = galleriaObj.getDataLength();//当前图片数
                    if (length == 1) {//即删除全部
                        $("#delAllBtn${key}").click();
                    } else {
                        var currentIndex = galleriaObj.getIndex();//当前图片索引
                        console.log("currentIndex:" + currentIndex);

                        if (currentIndex == 0) {//如果是删除的是第一张图片，则展示第二张
                            galleriaObj.next();
                        } else {
                            galleriaObj.show(0);//重新展示第一张图片
                        }
                        galleriaObj.splice(currentIndex, 1);//删除当前图片
                    }


                } else {
                    alertMsg.error("删除失败！");
                }
            });


        });

    });
</script>

<#--按钮栏-->
<#if ftpUploadFiles&&ftpUploadFiles?size gt 0>

<form id="imagesDisplay${key}" action="case/delUploadedFiles">
    <input type="hidden" name="key" value="${key}"/>

    <div class="formBar">
        <ul>
            <li>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button
                                type="button" id="delCurrentBtn${key}">删除当前
                        </button>
                    </div>
                </div>
            </li>
            <li>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button
                                type="button" id="delAllBtn${key}">删除全部
                        </button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</form>

</#if>