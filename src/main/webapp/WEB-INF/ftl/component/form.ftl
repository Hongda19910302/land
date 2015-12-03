<div class="pageContent">

<#if compForm??>

<#--组件ID-->
    <#assign componentId="form_"+compForm.id>

    <form method="post" action="${compForm.actionUrl}" id="${componentId}" class="pageForm"
          onsubmit="return validateCallback(this)">

        <div class="pageFormContent nowrap" layoutH="${compForm.buttonBarHeight}">

            <#list compForm.compFormItems as item>
                <dl>
                    <dt>${item.title}：</dt>
                    <dd>
                        <#switch item.inputType>

                            <#case "TEXT"><#--文本输入框-->
                                <input type="text" name="${item.inputName}"
                                       class="${item.inputClass}"
                                       style="${item.inputCssStyle}"/>
                                <#break>

                            <#case "REGION"><#--区域选择框-->
                                <div class="lookupRegionBtn">
                                    <input name="${item.inputName}Id"
                                           id="${item.inputName}Id_${componentId}" value=""
                                           type="hidden"/>
                                    <span><input class="lookupInput"
                                                 name="${item.inputName}Name"
                                                 id="${item.inputName}Name_${componentId}"
                                                 type="text" readonly
                                                 style="${item.inputCssStyle}"></span>
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

                            <#case "MAP">
                                <div class="formMap">
                                    <span>经度</span>
                                    <input type="text" name="${item
                                    .inputName}Longitude"
                                                              class="${item.inputClass}"
                                                              style="${item.inputCssStyle}"/>
                                    <span>纬度</span>
                                    <input type="text" name="${item
                                    .inputName}Latitude"
                                                              class="${item.inputClass}"
                                                              style="${item.inputCssStyle}"/>
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
            </#list>


        </div>

    <#--按钮栏-->
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button
                                    type="submit">提交
                            </button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button
                                    type="button" class="close">取消
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>

</#if>
</div>