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

        });

    </script>
</head>
<body>

<div id="mapDiv" style="position: absolute;width: 785px;height: 565px;"></div>
</body>
</html>