/*
简易tab切换类1.0版
说明：
本tab切换暂时仅仅适用于格式：
<ul class="TabCss">
<li><span></span><span>1</span></li>
<li><span></span><span>2</span></li>
<li><span></span><span>3</span></li>
.
.
.
.
</ul>
或者
<ul class="TabCss">
<li><span></span><span><a>1<a></span></li>
<li><span></span><span><a>2<a></span></li>
<li><span></span><span><a>3<a></span></li>
.
.
.
.
</ul>
li中的第一个span只用于显示图片背景，不能放内容,内容放于第二个span中，也可以移加入a标签，
加入a标签后会有一些鼠标经过和点击后的样式效果
注意：格式中的span要求严格，有且只能有两个，否则切换类不工作滴；a标签也只能有一个哦~~不然会出现意想不到的样式！！
本类可以同一页面同时存在多个tab，通过CSS类选择器（".TabCss"）调用
即   easyTab(".TabCss").click();   就可以轻松完成同一页面所有tab标签的绑定，无需重复;
通过 easyTab(".TabCss").click(callback);可以轻松获得点击tab标签的ID
调用方式easyTab(".TabCss").click(function(data){alert(data);});
注意本类在加载完后会自动在ul最末位填上一个Li，这是因为图片不规则，为基数，木有办法；
请在获取点击ID是最后一个li没有ID，需要过滤的！！
最后声明，本类只是用于易宝项目;最后tab标签最多99个~~应该够用了
*/
(function (window) {
    var easyTab = function (x) { return new easyTab.fn.init(x); }
    easyTab.fn = easyTab.prototype = {
        init: function (x) {
            this.data.selector = x;
            this.addLi();
            this.firstShow();
            return this;
        },
        data: {},
        //绑定click事件
        click: function (callback) {
            var _this = this;
            if (!_this.IsSelector()) { return false; }
            _this.setsign();
            $(_this.data.selector).find(">li").click(function () {
                if ($(this).parents("ul").find(">li").get().length <= 2)
                { return false; } else {
                    _this.initialState(this);
                    _this.changeState(this);
                    _this.cssOfa(this);
                    if (jQuery.isFunction(_this.callback(this, callback))) {
                        _this.callback(this, callback);
                    }
                }
            })
        },
        callback: function (obj, callback) {
            var _this = this;
            if (_this.islast(obj)) {
                var sign = $(obj).get(0).sign;
                var i = $(obj).parents("ul").find(">li").length - 1;
                if (sign.length > 10) {
                    sign = sign.substring(0, sign.length - 2) + i;
                }
                else {
                    sign = sign.substring(0, sign.length - 1) + i;
                }
                var id = $(obj).prev().get(0).id;
                var context = $(obj).prev().find(">span:last").text();
                callback({ "id": id, "text": context });
            }
            else {
                var id = $(obj).get(0).id;
                var context = $(obj).find(">span:last").text();
                callback({ "id": id, "text": context });
            }
        },
        //给每个li元素添加标识
        setsign: function () {
            var _this = this;
            var selector = _this.data.selector;
            var k = 1
            $(selector).each(function () {
                var selectorSign = this.className + "00" + k;
                this.ulSign = selectorSign;
                var i = 1;
                $(this).find(">li").each(function () {
                    this.sign = selectorSign + i;
                    i = i + 1;
                })
                k = k + 1;
            })
        },
        //元素如果不存在，避免报错
        IsSelector: function (x) {
            var i = (x == undefined) ? this.data.selector : x;
            return $(i).get().length > 0 ? true : false;
        },
        //变成初始从状态
        initialState: function (obj) {
            $(obj).parents("ul").find(">li").each(function () {
                $(this).find(">span:first").addClass("rightBlueOfCenter");
                $(this).find(">span:last").addClass("bluebg");
            })
            $(obj).parents("ul").find(">li:first").find(">span:first").removeClass().addClass("firstBlue");
            $(obj).parents("ul").find(">li:last").find(">span:first").removeClass().addClass("lastBlue");
            $(obj).parents("ul").find(">li:last").find(">span:last").removeClass();
        },
        changeState: function (obj) {
            var _this = this;
            if (_this.isThefirst(obj)) {
                $(obj).find(">span:first").removeClass().addClass("firstWhite");
                $(obj).find(">span:last").removeClass().addClass("whitebg");
                var sign = $(obj).get(0).sign;
                sign = sign.substring(0, sign.length - 1) + 2;

                $(obj).next().find(">span:first").removeClass().addClass("rightWhiteOfCenter");
            }
            else if (_this.isThelast(obj)) {
                $(obj).parents("ul").find(">li:last").find(">span:first").removeClass().addClass("lastWhite");
                var sign = $(obj).get(0).sign;
                var i = $(obj).parents("ul").find(">li").length - 1;
                if (sign.length > 10) {
                    sign = sign.substring(0, sign.length - 2) + i;
                }
                else {
                    sign = sign.substring(0, sign.length - 1) + i;
                }
                if ($(obj).get(0).sign == $(obj).parents("ul").find(">li:last").get(0).sign) {
                    $(obj).prev().find(">span:first").removeClass().addClass("leftWhiteOfCenter");
                    $(obj).prev().find(">span:last").removeClass().addClass("whitebg");
                }
                else {
                    $(obj).find(">span:first").removeClass().addClass("leftWhiteOfCenter");
                    $(obj).find(">span:last").removeClass().addClass("whitebg");
                }
            }
            else {
                $(obj).find(">span:first").removeClass().addClass("leftWhiteOfCenter");
                $(obj).find(">span:last").removeClass().addClass("whitebg");
                var sign = $(obj).get(0).sign;
                if (sign.length > 10) {
                    var i = parseInt(sign.substring(sign.length - 2, sign.length)) + 1;
                    sign = sign.substring(0, sign.length - 2) + i;
                } else {
                    var i = parseInt(sign.substring(sign.length - 1, sign.length)) + 1;
                    sign = sign.substring(0, sign.length - 1) + i;
                }

                $(obj).next().find(">span:first").removeClass().addClass("rightWhiteOfCenter");
            }
        },
        isThefirst: function (obj) {
            var sign = $(obj).parents("ul").find(">li:first").get(0).sign;
            if ($(obj).get(0).sign == sign) { return true; } else { return false; }
        },
        isThelast: function (obj) {
            var sign = $(obj).parents("ul").find(">li:last").get(0).sign;
            var i = $(obj).parents("ul").find(">li").get().length;
            //有两个或两个以上Tab标签时
            var sign1 = $(obj).parents("ul").find(">li").get(i - 2).sign;
            if ($(obj).get(0).sign == sign || $(obj).get(0).sign == sign1) { return true; } else { return false; }
        },
        //判断是不是最后的Li元素
        islast: function (obj) {
            var sign = $(obj).parents("ul").find(">li:last").get(0).sign;
            if ($(obj).get(0).sign == sign) { return true; } else { return false; }
        },
        cssOfa: function (obj) {
            if ($(obj).parents("ul").find(">li").find("a").get().length > 0) {
                $(obj).parents("ul").find(">li").find("a").each(function () {
                    this.style.color = "Black";
                });
                if ($(obj).find("a").get().length > 0)
                { $(obj).find("a").get(0).style.color = "White"; }
                else { $(obj).parents("ul").find("a:last").get(0).style.color = "Black"; }
            }
        },
        addLi: function () {
            var Str = "<li><span></span><span></span></li>";
            var _this = this;
            $(_this.data.selector).append(Str);
        },
        firstShow: function () {
            var _this = this;
            if (!_this.IsSelector()) { return false; }
            $(_this.data.selector).each(function () {
                if ($(this).find(">li").get().length > 2) {
                    $(this).find(">li").each(function () {
                        $(this).find(">span:first").addClass("rightBlueOfCenter");
                        $(this).find(">span:last").addClass("bluebg");
                    })
                    $(this).find(">li:first").find(">span:first").removeClass().addClass("firstWhite");
                    $(this).find(">li:first").find(">span:last").removeClass().addClass("whitebg");
                    if ($(this).find(">li:first").find(">span:last").find("a").get().length > 0) {
                        $(this).find(">li:first").find(">span:last").find("a").get(0).style.color = "White";
                    }
                    $($(this).find(">li").get(1)).find(">span:first").removeClass().addClass("rightWhiteOfCenter");
                    $(this).find(">li:last").find(">span:first").removeClass().addClass("lastBlue");
                    $(this).find(">li:last").find(">span:last").removeClass();
                }
                else if ($(this).find(">li").get().length == 2) {
                    $(this).find(">li:first").find(">span:first").removeClass().addClass("firstWhite");
                    $(this).find(">li:first").find(">span:last").removeClass().addClass("whitebg");
                    if ($(this).find(">li:first").find(">span:last").find("a").get().length > 0) {
                        $(this).find(">li:first").find(">span:last").find("a").get(0).style.color = "White";
                    }
                    $(this).find(">li:last").find(">span:first").removeClass().addClass("lastWhite");
                    $(this).find(">li:last").find(">span:last").removeClass();
                }
            })
        }
    }
    easyTab.fn.init.prototype = easyTab.fn;
    window.easyTab = easyTab;
})(window)



