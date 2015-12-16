<div class="pageContent">

<#if compForm??>

    <form method="post" action="${compForm.actionUrl}" id="${componentId}" class="pageForm"
          onsubmit="return validateCallback(this)">

        <div class="pageFormContent nowrap" layoutH="${compForm.buttonBarHeight}">

            <#list compForm.compFormItems as item>
                <#switch item.inputType>
                    <#case "UPLOAD"><#--添加分隔行-->
                        <div class="divider"></div>

                        <script type="text/javascript">
                            /**
                             * 处理文件上传失败
                             * @param file
                             * @param errorCode
                             * @param errorMsg
                             */
                            function uploadErrorHandler(file, errorCode, errorMsg) {
                                console.log("文件上传失败【" + errorCode + "】：" + errorMsg);
                                switch (errorCode) {
                                    case -280:
                                        break;
                                    default :
                                        alertMsg.error("文件上传组件出现错误！errorCode：" + errorCode);
                                        break;
                                }
                            }

                            $(function () {

                                /**
                                 * 绑定【开始上传】、【取消上传】操作
                                 *
                                 */
                                var $FileInput = $("#${componentId}${item
                                .inputName}FileInput");
                                console.log("$FileInput:" + $FileInput.length);
                                $("#${componentId}${item.inputName}UploadBtn").click(function () {
                                    $FileInput.uploadify('upload', '*');
                                });
                                $("#${componentId}${item.inputName}CancelBtn").click(function () {
                                    $FileInput.uploadify('cancel', '*');
                                });
                            });

                        </script>
                        <#break>
                </#switch>

                <dl>
                    <dt>${item.title}：</dt>
                    <dd>
                        <#switch item.inputType>

                            <#case "TEXT"><#--文本输入框-->
                                <input type="text" name="${item.inputName}"
                                       class="${item.inputClass}"
                                       style="${item.inputCssStyle}"

                                    <#if obj??><#--展示字段值-->
                                       value="${obj[item.inputName]}"
                                    </#if>

                                        />
                                <#break>

                            <#case "VARIABLE"><#--字段为可变内容的下拉选择框-->
                                <select class="combox" name="${item.inputName}">
                                    <#assign selectListDataSet=item.selectListDataSet>
                                    <#if selectListDataSet?exists>
                                        <#list selectListDataSet?keys as key>
                                            <option
                                                    value="${key}">${selectListDataSet[key]}</option>
                                        </#list>
                                    </#if>
                                </select>
                                <#break>
                            <#case "SELECT"><#--下拉选择框-->
                                <select class="combox" name="${item.inputName}">
                                    <#assign selectListDataSet=item.selectListDataSet>
                                    <#if selectListDataSet?exists>
                                        <#list selectListDataSet?keys as key>
                                            <option
                                                    value="${key}">${selectListDataSet[key]}</option>
                                        </#list>
                                    </#if>
                                </select>
                                <#break>

                            <#case "TEXTAREA"><#-- 多行文本框-->
                                <textarea name="${item.inputName}" class="${item
                                .inputClass}" cols="${item.textareaCols}" rows="${item
                                .textareaRows}"
                                        ><#if obj??>${obj[item.inputName]}</#if></textarea>
                                <#break>

                            <#case "UPLOAD"><#-- 文件上传-->

                                <input id="${componentId}${item.inputName}FileInput"
                                       type="file" name="${item
                                .inputName}FileInput"
                                       uploaderOption="{
                    swf:'${resourceRoot}/dwz/uploadify/scripts/uploadify.swf',
                    uploader:'${item.uploadActionUrl}',
                    formData:{},
                    queueID:'${componentId}${item.inputName}FileQueue',
                    buttonText:'${item.uploadButtonText}',
                    fileSizeLimit:'${item.uploadFileSizeLimit}',
                    fileTypeDesc:'${item.uploadFileTypeExts}',
                    fileTypeExts:'${item.uploadFileTypeExts}',
                    auto:${item.uploadIsAuto},
                    multi:${item.uploadIsMulti},
                    width:${item.uploadButtonWidth},
                    upload_error_handler:uploadErrorHandler
                   }"/>

                                <div id="${componentId}${item.inputName}FileQueue"
                                     class="fileQueue ${item.inputClass}"></div>
                                <div class="button">
                                    <div id="${componentId}${item.inputName}UploadBtn"
                                         class="buttonContent">开始上传
                                    </div>
                                </div>
                                <div id="${componentId}${item
                                .inputName}CancelBtn" class="button">
                                    <div class="buttonContent">取消上传</div>
                                </div>
                                <a class="btnLook"
                                   href="/case/lookupUploadedFiles?key=${item
                                   .inputName}"
                                   target="dialog"
                                   rel="lookupUploadedFiles"
                                   mask="true" minable="false" height="600"
                                   width="800"
                                   resizable="false"
                                   maxable="false"
                                   title="已上传的${item.title}"></a>
                                <#break>

                            <#case "REGION"><#--区域选择框-->
                                <div class="lookupRegionBtn">
                                    <input name="${item.inputName}Id"
                                           id="${item.inputName}Id_${componentId}"
                                        <#if currentUserRegion??><#--默认为当前用户所在区域-->
                                           value="${currentUserRegion.regionId}"
                                        </#if>
                                        <#if obj??><#--展示字段值-->
                                           value="${obj[item.inputName+"Id"]}"
                                        </#if>

                                           type="hidden"/>
                                    <span><input class="lookupInput ${item.inputClass}"
                                                 name="${item.inputName}Name"
                                                 id="${item.inputName}Name_${componentId}"
                                                 type="text" readonly="true"
                                                 style="${item.inputCssStyle}"
                                        <#if currentUserRegion??>
                                                 value="${currentUserRegion.name}"
                                        </#if>
                                        <#if obj??><#--展示字段值-->
                                                 value="${obj[item.inputName+"Name"]}"
                                        </#if>
                                            ></span>
                                    <a class="btnLook"
                                       href="/comp/lookupRegion?componentId=${componentId}"
                                       target="dialog"
                                    <#--rel:标识此弹出层ID-->
                                       rel="lookupRegion"
                                    <#--resizable：是否可变大小-->
                                       resizable="false"
                                    <#--minable：是否可最小化-->
                                       minable="false"
                                    <#--maxable：是否可最大化-->
                                       maxable="false"
                                    <#--是否将背景遮盖-->
                                       mask="true"
                                    <#--弹出框宽度-->
                                       width="600"
                                    <#--弹出框高度-->
                                       height="480"
                                    <#--标题-->
                                       title="选择区域"></a>
                                </div>
                                <#break>

                            <#case "MAP"><#--地理坐标选择框-->
                                <div class="formMap">
                                    <span>经度</span>
                                    <input type="text" name="${item
                                    .inputName}Longitude" readonly="true"
                                           class="${item.inputClass}"
                                           style="${item.inputCssStyle}"
                                        <#if obj??><#--展示字段值-->
                                           value="${obj[item
                                           .inputName+"Longitude"]}"
                                        </#if>
                                            />
                                    <span>纬度</span>
                                    <input type="text" name="${item
                                    .inputName}Latitude" readonly="true"
                                           class="${item.inputClass}"
                                           style="${item.inputCssStyle}"
                                        <#if obj??><#--展示字段值-->
                                           value="${obj[item.inputName+"Latitude"]}"
                                        </#if>
                                            />
                                    <a class="btnLook"
                                       href="/comp/lookupGeographicCoordinates?formId=${componentId}&fieldName=${item
                                       .inputName}"
                                       target="dialog"
                                       rel="lookupGeographicCoordinates"
                                       mask="true" minable="false" height="620"
                                       width="1024"
                                       resizable="false"
                                       maxable="false"
                                       title="查找地理坐标"></a>
                                </div>
                                <#break>
                        </#switch>
                    </dd>
                </dl>

                <#switch item.inputType>
                    <#case "UPLOAD"><#--添加分隔行-->
                        <div class="divider"></div>
                        <#break>
                </#switch>

            </#list>
        </div>



    <#--按钮栏-->
        <div class="formBar">
            <ul>
                <#list compForm.compFormBtns as btn>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button id="form_btn_${btn.id}"
                                        type="${btn.type}"
                                    <#if btn.btnClass??>
                                        class="${btn.btnClass}"
                                    </#if>
                                        >${btn.name}
                                </button>
                            </div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </form>

    <#list compForm.compFormBtns as btn>
        <#if btn.actionUrl??>
            <script type="text/javascript">
                $(function () {
                    $("#form_btn_${btn.id}").click(function () {
                        $("#${componentId}").attr("action", "${btn.actionUrl}");
                    });
                });
            </script>

        </#if>
    </#list>


</#if>
</div>