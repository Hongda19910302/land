<div class="pageContent">
    <div class="tabs" currentIndex="0" eventType="click">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li a href="javascript:"><span>基础信息</span></li>
                    <li a href="javascript:"><span>巡查记录</span></li>
                    <li a href="javascript:"><span>案件位置</span></li>
                    <li a href="javascript:"><span>案件流转</span></li>
                    <li a href="javascript:"><span>案件批示</span></li>
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
                        <th>违法现状</th>
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
                    </tbody>

                </table>

            </div>
            <div>内容2</div>
            <div>内容3</div>
            <div>内容4</div>
            <div>内容5</div>
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
                                    >打印预览
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>