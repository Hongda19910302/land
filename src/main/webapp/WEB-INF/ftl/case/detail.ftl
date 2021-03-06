<script type="text/javascript">
    $(function () {
        $("#case_print_btn").click(function () {
            $.print($(".caseDetail"));
        });
    });
</script>

<div class="pageContent">
    <div class="tabs" currentIndex="0" eventType="click">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:"><span>基础信息</span></a></li>
                    <li><a href="case/findInspects?caseId=${tCase.caseId}"
                           class="j-ajax"><span>巡查记录</span></a></li>
                    <li><a href="/comp/lookupGeographicCoordinates?mode=DISPLAY&lng=${tCase.lng}&lat=${tCase.lat}" class="j-ajax"><span>案件位置</span></a></li>
                    <li><a href="/case/findFlowRecords?caseId=${tCase.caseId}"
                    class="j-ajax"><span>案件流转</span></a></li>
                    <#--<li><a href="javascript:"><span>案件批示</span></a></li>-->
                </ul>
            </div>
        </div>

        <div class="tabsContent" style="height:510px;">
            <div>
                <table class="list caseDetail" width="100%" layoutH="76">
                    <tbody>
                    <tr>
                        <th width="120">案件号</th>
                        <td>${tCase.caseNum}</td>
                        <th width="120">创建时间</th>
                        <td>${tCase.createTime}</td>
                    </tr>
                    <tr>
                        <th>创建人</th>
                        <td>${tCase.creatorName}</td>
                        <th>巡查员</th>
                        <td>${tCase.inspectorName}</td>
                    </tr>
                    <tr>
                        <th>案件状态</th>
                        <td colspan="3">${tCase.statusInDisplay}</td>
                    </tr>
                    <tr>
                        <th>当事人</th>
                        <td colspan="3">${tCase.parties}</td>
                    </tr>
                    <tr>
                        <th>身份证号码</th>
                        <td colspan="3">${tCase.idCardNum}</td>
                    </tr>
                    <tr>
                        <th>所在地区</th>
                        <td colspan="3">${tCase.regionName}</td>
                    </tr>
                    <tr>
                        <th>违法地点</th>
                        <td colspan="3">${tCase.illegalArea}</td>
                    </tr>
                    <tr>
                        <th>东至</th>
                        <td colspan="3">${tCase.eastTo}</td>
                    </tr>
                    <tr>
                        <th>西至</th>
                        <td colspan="3">${tCase.westTo}</td>
                    </tr>
                    <tr>
                        <th>南至</th>
                        <td colspan="3">${tCase.southTo}</td>
                    </tr>
                    <tr>
                        <th>北至</th>
                        <td colspan="3">${tCase.northTo}</td>
                    </tr>
                    <tr>
                        <th>案件位置</th>
                        <td colspan="3">${tCase.lng}&nbsp;x&nbsp;${tCase.lat}</td>
                    </tr>
                    <tr>
                        <th>违法面积</th>
                        <td colspan="3">${tCase.illegalAreaSpace}&nbsp;平方米</td>
                    </tr>
                    <tr>
                        <th>违建类型</th>
                        <td colspan="3">${tCase.illegalTypeInDisplay}</td>
                    </tr>
                    <tr>
                        <th>用地性质</th>
                        <td colspan="3">${tCase.landUsageInDisplay}</td>
                    </tr>
                    <tr>
                        <th>巡查结果</th>
                        <td colspan="3">${tCase.statusInDisplay}</td>
                    </tr>
                    <tr>
                        <th>案件来源</th>
                        <td colspan="3">${tCase.caseSourceInDisplay}</td>
                    </tr>
                    <tr>
                        <th>备注</th>
                        <td colspan="3">${tCase.remark}</td>
                    </tr>
                    <tr class="no-print">
                        <th>单据</th>
                        <td colspan="3">
                            <a class="btnLook"
                               href="/case/lookupUploadedFiles?key=caseDocuments&id=${tCase.caseId}&pattern=DISPLAY"
                               target="dialog"
                               rel="lookupUploadedFiles"
                               mask="true" minable="false" height="600"
                               width="800"
                               resizable="false"
                               maxable="false"
                               title="已上传的单据"></a>
                        </td>
                    </tr>
                    <tr class="no-print">
                        <th>照片</th>
                        <td colspan="3">
                            <a class="btnLook"
                               href="/case/lookupUploadedFiles?key=illegalPhotos&id=${tCase.caseId}&pattern=DISPLAY"
                               target="dialog"
                               rel="lookupUploadedFiles"
                               mask="true" minable="false" height="600"
                               width="800"
                               resizable="false"
                               maxable="false"
                               title="已上传的照片"></a>
                        </td>
                    </tr>
                    </tbody>

                </table>

            </div>
            <div></div>
            <div></div>
            <div></div>
            <#--<div></div>-->
        </div>

        <div class="tabsFooter">
            <div class="tabsFooterContent">
            </div>
        </div>

    <#--按钮栏-->
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button id="case_print_btn"
                                    type="button"
                                    >打印
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>