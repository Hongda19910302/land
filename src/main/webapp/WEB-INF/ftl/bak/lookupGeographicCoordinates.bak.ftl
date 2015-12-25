<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查找地理坐标</title>
    <script type="text/javascript">

        var map;
        var mapLocalSearch;
        var zoom = 12;//缩放级别
        var mapSearchResults = [];//查询结果集
        var mapSearchResultIdPrefix = "address_";//查询结果对象的ID前缀
        var defaultLongitude = 119.30324;//默认经度（五一广场）
        var defalutLatitude = 26.07852;//默认纬度

        $(function () {
            initMap();
            initMapSearchInput();

            //绑定搜索功能
            $("#mapSearchBtn").click(function () {
                mapLocalSearch.search($("#mapKeyWord").val());
            });

            //绑定分页条（首页、上一页、下一页、末页、跳转）事件
            $("#mapFirstBtn").click(function () {
                mapLocalSearch.firstPage();
            });
            $("#mapPreviousBtn").click(function () {
                console.log("getPageIndex：" + mapLocalSearch.getPageIndex());
                mapLocalSearch.previousPage();
            });
            $("#mapNextBtn").click(function () {
                mapLocalSearch.nextPage();
            });
            $("#mapLastBtn").click(function () {
                mapLocalSearch.lastPage();
            });
            $("#mapJumpToBtn").click(function () {
                var gotoPageNo = parseInt($('#mapJumpToInput').val());

                //当输入的页号超出正常页号范围时，自动修正为边界值
                if (gotoPageNo < 1) {
                    gotoPageNo = 1;
                } else if (gotoPageNo > mapLocalSearch.getCountPage()) {
                    gotoPageNo = mapLocalSearch.getCountPage();
                }
                mapLocalSearch.gotoPage(gotoPageNo);
                $('#mapJumpToInput').val(gotoPageNo);
            });

            //绑定页码输入后回车跳转到相应的页
            $("#mapJumpToInput").keyup(function (e) {//回车提交
                var text = $(this).val();
                $(this).val(text.replace(/\D|^0/g, ''));//限制只能输入数字
                if (e.which == 13) {
                    $("#mapJumpToBtn").click();
                }
            });

            //初始化经纬度坐标，使用当前默认值
            $("#longitudeInput").val(defaultLongitude);
            $("#latitudeInput").val(defalutLatitude);
        });

        //本地搜索
        function mapLocalSearchResult(result) {
            mapClearAll();
            mapPrompt(result);

            //根据返回类型解析搜索结果
            switch (parseInt(result.getResultType())) {
                case 1://解析点数据结果
                    mapPois(result.getPois());
                    break;
                case 2://解析推荐城市
                    break;
                case 3://解析行政区划边界
                    break;
                case 4://解析建议词信息
                    break;
                case 5://解析公交信息
                    break;
            }

            addMapCopyright();
        }

        //解析点数据结果
        function mapPois(obj) {
            if (obj) {//显示搜索列表
                var zoomArr = [];//坐标数组，设置最佳比例尺时会用到

                var $ul = $("<ul></ul>");
                $ul.attr("class", "mapSearchResultList");
                for (var i = 0; i < obj.length; i++) {
                    var name = obj[i].name;//名称

                    var address = obj[i].address;//地址
                    var lnglatArr = obj[i].lonlat.split(" ");//坐标
                    var lnglat = new TLngLat(lnglatArr[0], lnglatArr[1]);

                    var winHtml = "地址：" + address;
                    var marker = new TMarker(lnglat);//创建标注对象
                    map.addOverLay(marker);//在地图上添加标注点
                    TEvent.bind(marker, "click", marker, function () {//注册标注点的点击事件
                        var info = this.openInfoWinHtml(winHtml);
                        info.setTitle(name);
                    });
                    zoomArr.push(lnglat);

                    //在页面上显示搜索的列表
                    var $li = $("<li></li>");
                    var $a = $("<a></a>");
                    var id = mapSearchResultIdPrefix + i;
                    $a.attr("id", id);
                    $a.attr("href", "javascript://");
                    $a.html(name);
                    $li.append($a);
                    $ul.append($li);

                    //保存结果集
                    var result = {
                        id: id,
                        name: name,
                        marker: marker,
                        winHtml: winHtml,
                        lnglat: lnglat
                    };
                    mapSearchResults.push(result);
                }

                map.setViewport(zoomArr);//显示地图的最佳级别

                //添加分页数据
                var $pageLi = $("<li></li>");
                var currentPageNo = mapLocalSearch.getPageIndex();//当前页号
                var totalPageCount = mapLocalSearch.getCountPage();//总页数
                $pageLi.append("共<span class='mapPromptStrong'>" + mapLocalSearch.getCountNumber()
                + "</span>条记录，分<span class='mapPromptStrong'>" + totalPageCount + "</span>页，当前第<span class='mapPromptStrong'>" + currentPageNo + "</span>页");
                $ul.append($pageLi);

                $("#mapPaginationInfo").append($ul);

                //如果是在第一页，则上一页按钮不可用；如果是在最后一页，则下一页按钮不可用
                if (currentPageNo == 1) {//上一页按钮不可用
                    $("#mapPreviousBtn").removeClass("button");
                    $("#mapPreviousBtn").addClass("buttonDisabled");
                } else {
                    $("#mapPreviousBtn").removeClass("buttonDisabled");
                    $("#mapPreviousBtn").addClass("button");
                }
                if (currentPageNo == totalPageCount) {//下一页按钮不可用
                    $("#mapNextBtn").removeClass("button");
                    $("#mapNextBtn").addClass("buttonDisabled");
                } else {
                    $("#mapNextBtn").removeClass("buttonDisabled");
                    $("#mapNextBtn").addClass("button");
                }

                $("#mapSearchDiv").show();
                $("#mapResultDiv").show();

                //绑定查询结果详情显示事件
//                console.log("mapSearchResults:"+mapSearchResults);
                for (var i = 0; i < mapSearchResults.length; i++) {
                    $("#" + mapSearchResults[i].id).on("click", function () {
                        var index = $(this).attr("id").replace
                        (mapSearchResultIdPrefix, "");
                        var r = mapSearchResults[index];
                        mapShowPosition(r.marker, r.name, r.winHtml);
                        map.centerAndZoom(r.lnglat, zoom);//设置显示地图的中心点和级别

                        //点击某个查询结果时，将这个结果的经纬度坐标写入经纬度框
                        $("#latitudeInput").val(r.lnglat.getLat());
                        $("#longitudeInput").val(r.lnglat.getLng());

                        addMapCopyright();
                    });
                }
            }
        }

        //显示信息框
        function mapShowPosition(marker, name, winHtml) {
            var info = marker.openInfoWinHtml(winHtml);
            info.setTitle(name);
        }

        //解析提示词
        function mapPrompt(obj) {
            var prompts = obj.getPrompt();
            if (prompts) {
                var promptHtml = "";
                for (var i = 0; i < prompts.length; i++) {
                    var prompt = prompts[i];
                    var promptType = prompt.type;
                    var promptAdmins = prompt.admins;
                    var meanprompt = prompt.DidYouMean;
                    if (promptType == 1) {
                        promptHtml += "<p class='mapPrompt'>您是否要在<span class='mapPromptStrong'>" +
                        promptAdmins[0]
                                .name + "</span>搜索更多包含<span class='mapPromptStrong'>" + obj
                                .getKeyword() + "</span>的相关内容？<p>";
                    } else if (promptType == 2) {
                        promptHtml += "<p class='mapPrompt'>在<span " +
                        "class='mapPromptStrong'>" +
                        promptAdmins[0]
                                .name + "</span>没有搜索到与<span class='mapPromptStrong'>" + obj
                                .getKeyword() + "</span>的相关的结果。<p>";
                        if (meanprompt) {
                            promptHtml += "<p class='mapPrompt'>您是否要找：<span class='mapPromptStrong'>" + meanprompt + "</span><p>";
                        }
                    } else if (promptType == 3) {
                        promptHtml += "<p  class='mapPrompt' style='margin-bottom:3px;" +
                        "'>有以下相关结果，您是否要找：</p>";
                        for (i = 0; i < promptAdmins.length; i++) {
                            promptHtml += "<p>" + promptAdmins[i].name + "</p>";
                        }
                    }

                    if (promptHtml != "") {
                        $("#mapPromptDiv").show();
                        $("#mapPromptDiv").html(promptHtml);
                    }
                }
            }
        }

        //清空地图及搜索列表
        function mapClearAll() {
            map.clearOverLays();
            $("#mapPaginationInfo").html("");

            var $mapSearchDiv = $("#mapSearchDiv");
            $mapSearchDiv.html("");
            $mapSearchDiv.hide();

            var $mapPromptDiv = $("#mapPromptDiv");
            $mapPromptDiv.html("");
            $mapPromptDiv.hide();

            mapSearchResults = [];
        }

        //光标定位在搜索框上时，将提示文字去掉；当光标移开时，若为填写任何内容，则提示文字恢复
        function initMapSearchInput() {
            $("#mapKeyWord").focus(function () {
                $(this).addClass("focus");
                if ($(this).val() == this.defaultValue) {
                    $(this).val("");
                }
            }).blur(function () {
                $(this).removeClass("focus");
                if ($(this).val() == '') {
                    $(this).val(this.defaultValue);
                }
            }).keyup(function (e) {//回车提交
                if (e.which == 13) {
                    $("#mapSearchBtn").click();
                }
            });
        }

        //初始化地图
        function initMap() {
            map = new TMap("mapDiv");//初始化地图对象
            map.centerAndZoom(new TLngLat(defaultLongitude, defalutLatitude), zoom);
            //设置显示地图的中心点和级别
            map.enableHandleMouseScroll();//允许鼠标滚轮缩放地图

            /**
             * 添加缩放平移控件
             */
            var control = new TNavigationControl({//创建缩放平移控件对象
                type: "TMAP_NAVIGATION_CONTROL_LARGE",//缩放平移的显示类型
                anchor: "TMAP_ANCHOR_TOP_LEFT",//缩放平移控件显示的位置
                offset: [0, 0],//缩放平移控件的偏移量
                showZoomInfo: true//是否显示级别提示信息
            });
            map.addControl(control);

            //添加比例尺
            var scale = new TScaleControl();//创建比例尺控件对象
            map.addControl(scale);

            //添加鹰眼控件
            var overviewMap = new TOverviewMapControl({
                anchor: "TMAP_ANCHOR_BOTTOM_RIGHT",//设置鹰眼位置
                size: new TSize(180, 120),//鹰眼显示的大小
                isOpen: true//是否打开
            });
            map.addControl(overviewMap);

            //添加地图版本控件
            addMapCopyright();

            //添加地图类型控件
            var mapTypeControl = new TMapTypeControl();//创建地图类型控件对象
            mapTypeControl.setLeft(10);
            mapTypeControl.setTop(20);
            map.addControl(mapTypeControl);

            //创建搜索对象
            mapLocalSearch = new TLocalSearch(map, {
                pageCapacity: 10,//每页显示的记录数
                onSearchComplete: mapLocalSearchResult//接收数据的回调函数
            });

            //创建地图拾取器
            var pickup = new TCoordinatePickup(map, {
                callback: function (lnglat) {//将选择的坐标写入经纬度坐标框
                    //console.log("lnglat:"+lnglat);
                    $("#latitudeInput").val(lnglat.getLat());
                    $("#longitudeInput").val(lnglat.getLng());
                }
            });
            pickup.addEvent();
        }

        //添加地图版本控件
        function addMapCopyright() {
            var copyrightControl = new TCopyrightControl();
            copyrightControl.setLeft(460);//设置版权位置
            copyrightControl.setBottom(20);
            var bs = map.getBounds();//返回地图可视区域
            copyrightControl.addCopyright({
                id: 1, content: "<div style='font-size:14px;" +
                "background-color:#f4f8f8;" +
                "padding:2px 2px; font-weight:bold" +
                "'>移动巡查执法平台</div>", bounds: bs
            });//添加版本内容，也可在此添加事件
            map.addControl(copyrightControl);
        }

    </script>
</head>
<body>

<#--地图-->
<div id="mapDiv" class="mapDiv"></div>

<#--选定的经纬度坐标-->
<div class="mapCoordinate">
    <ul>
        <li><span class='mapPromptStrong'>经度：</span><input class="textInput" type="text"
                                                           id="longitudeInput"
                                                           readonly="true">
        </li>
        <li><span class='mapPromptStrong'>纬度：</span><input class="textInput" type="text"
                                                           id="latitudeInput"
                                                           readonly="true">
        </li>
        <li><a id="mapCoordinateBtn" class="button" href="javascript:"><span>选定</span></a>
        </li>
    </ul>

</div>

<#--搜索面板-->
<div class="mapSearch">
    <ul class="mapSearchKey">
        <li class="mapPromptStrong">搜索内容：</li>
        <li><input type="text" id="mapKeyWord" value="万宝"/></li>
        <li><a id="mapSearchBtn" class="button" href="javascript:"><span>搜索</span></a></li>
    </ul>
<#--提示词面板-->
    <div id="mapPromptDiv" class="prompt"></div>

    <div id="mapResultDiv" class="mapResult">
        <div id="mapSearchDiv"></div>
        <div class="mapPageDiv">
            <div id="mapPaginationInfo" class="mapPaginationInfo"></div>
            <ul class="mapPagination">
                <li>
                    <a id="mapFirstBtn" class="button" href="javascript:
                    "><span>首页</span></a>
                </li>
                <li>
                    <a id="mapPreviousBtn" class="button"><span>《</span></a>
                </li>
                <li>
                    <a id="mapNextBtn" class="button" href="javascript:
                    "><span>》</span></a>
                </li>
                <li>
                    <a id="mapLastBtn" class="button"
                    "><span>末页</span></a>
                </li>
                <li>
                    <input id="mapJumpToInput" class="textInput mapJumpToInput" type="text"
                           size="4"
                           value="2">

                </li>
                <li>
                    <a id="mapJumpToBtn" class="button" href="javascript:
                    "><span>跳转</span></a>
                </li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>