<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查找地理坐标</title>
    <script type="text/javascript">

        $(function () {
            var zoom = 12;
            var map = new TMap("mapDiv");//初始化地图对象
            map.centerAndZoom(new TLngLat(116.40969, 39.89945), zoom);//设置显示地图的中心点和级别
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
//            var copyrightControl = new TCopyrightControl();
//            copyrightControl.setLeft(10);//设置版权位置
//            copyrightControl.setTop(10);
//            var bs = map.getBounds();//返回地图可视区域
//            copyrightControl.addCopyright({
//                id: 1, content: "<div style='font-size:14;background: black;color:white;" +
//                "padding:2px 2px;" +
//                "'>移动巡查执法</div>", bounds: bs
//            });//添加版本内容，也可在此添加事件
//            map.addControl(copyrightControl);

            //添加地图类型控件
            var mapTypeControl=new TMapTypeControl();//创建地图类型控件对象
            mapTypeControl.setLeft(10);
            mapTypeControl.setTop(20);
            map.addControl(mapTypeControl);



        });

    </script>
</head>
<body>

<div id="mapDiv" style="position: absolute;width: 785px;height: 565px;"></div>
</body>
</html>