/**
 * Created by deniro on 2015/9/24.
 */
;
(function ($) {

    /**
     * 封装选择器插件
     */
    $.extend($.expr[":"], {
        /**
         * 两者之间的选择器
         *
         * $("div:between(2,5)")
         *
         * @param a 当前遍历到的DOM元素
         * @param i 当前遍历到的DOM元素的索引值，从0开始
         * @param m 由正则引擎解析器进一步解析后的产物；m[3]即括号中的值-2,5
         * @returns {boolean}
         */
        between: function (a, i, m) {
            var tmp = m[3].split(",");
            return tmp[0] - 0 < i && i < tmp[1] - 0;//tmp[0] - 0 将字符串转化成数字
        }
    });

    /**
     * 封装全局函数的插件
     */
    $.extend({
        /**
         * 去除左侧空格
         * @param text
         * @returns {string}
         */
        ltrim: function (text) {
            //(text||"")部分防止传递进来的text字符串变量未定义，如果为定义则返回""
            return (text || "").replace(/^\s+/g, "");
        },
        /**
         * 去除右侧空格
         * @param text
         * @returns {string}
         */
        rtrim: function (text) {
            return (text || "").replace(/\s+$/g, "");
        }
    }),

    /**
     * 封装对象方法的插件
     */
        $.fn.extend({
            /**
             * 从元素中去除HTML
             * @returns {*|HTMLElement}
             */
            "stripHtml": function () {
                var regexp = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
                this.each(function () {
                    $(this).html($(this).html().replace(regexp, ''));
                });
                return $(this);
            },

            /**
             * 限制Textarea域中的字符个数（也可限制text和password域的字符个数）
             * @param max 限制的最大字符个数
             */
            "maxLength": function (max) {
                this.each(function () {
                        var type = this.tagName.toLowerCase();
                        var inputType = this.type ? this.type.toLowerCase() : null;
                        if (type == "input" && inputType == "text" || inputType == "password") {
                            this.maxLength = max;//应用标准的maxLength
                        } else if (type == "textarea") {
                            this.onkeypress = function (e) {
                                var ob = e || event;
                                var keyCode = ob.keyCode;
                                var hasSelection = document.selection ? document.selection.createRange().text.length > 0 :
                                this.selectionStart != this.selectionEnd;
                                return !(this.value.length >= max && (keyCode > 50 || keyCode == 32 || keyCode == 0 || keyCode == 13) && !ob.ctrlkey && !ob.altKey && !hasSelection);
                            };

                            this.onkeyup = function () {
                                if (this.value.length > max) {
                                    this.value = this.value.substring(0, max);//直接截断
                                }
                            };
                        }
                    }
                )
            },

            /**
             * 设置div在屏幕中央
             * @returns {center}
             */
            "center": function () {
                this.css("position", "absolute");
                this.css("top", ($(window).height() - this.height()) / 2 + $(window).scrollTop() + "px");
                this.css("left", ($(window).width() - this.width()) / 2 + $(window).scrollLeft() + "px");
                return this;
            }

            ,

            /**
             * 返回头部滑动动画
             * @param speed 滑动速度
             * @returns {scrollTo}
             */
            "scrollTo": function (speed) {
                var targetOffset = $(this).offset().top;
                $('html,body').stop().animate({scrollTop: targetOffset}, speed);
                return this;
            }

            ,

            /**
             * 表格隔行变色
             *
             * $("#xx").alterBgColor();使用默认样式
             * $("#xx").alterBgColor({odd: "odd",even: "even",selected: "selected"})使用自定义样式
             * @param options
             */
            "alterBgColor": function (options) {
                options = $.extend({
                    odd: "odd", //偶数行样式
                    even: "even",//奇数行样式
                    selected: "selected"//选中行样式
                }, options);

                $('tbody>tr:odd', this).addClass(options.odd);//加this参数表示在匹配的元素内（当前表格内）查找
                $('tbody>tr:even', this).addClass(options.even);

                //单击某行后，此行高亮显示，并且单击框被选中
                $('tbody>tr', this).click(function () {
                    var hasSelected = $(this).hasClass(options.selected);//判断当前是否选中
                    //等价于$(this).removeClass('selected');或者$(this).addClass('selected');
                    $(this)[hasSelected ? 'removeClass' : 'addClass'](options.selected)
                        .find(':checkbox').attr('checked', !hasSelected);//查找checkbox
                });

                //默认已选中的行高亮显示
                $('tbody>tr:has(:checked)', this).addClass(options.selected);
                return this;
            }

            ,

            /**
             * 获取或设置元素的color值
             * @param value
             * @returns {*}
             */
            "color": function (value) {
                //css方法本身已有undefined判断
                //if (value == undefined) {
                //    return this.css("color");
                //} else {
                //    //this：指向的是jQuery对象
                //    //如果不需要返回字符串之类的特定值，应当使其具有可链接性
                //    return this.css("color", value);
                //}

                return this.css("color", value);
            }
        })
})(jQuery);
