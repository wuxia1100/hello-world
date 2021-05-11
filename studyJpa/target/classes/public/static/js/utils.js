String.prototype.trim = function () {
    return this.replace(/^\s+/g, '').replace(/\s+$/g, '');
};

if (!String.prototype.repeat) {
    String.prototype.repeat = function(count) {
        "use strict";
        if (this == null) {
            throw new TypeError("can\"t convert " + this + " to object");
        }
        var str = "" + this;
        count = +count;
        if (count != count) {
            count = 0;
        }
        if (count < 0) {
            throw new RangeError("repeat count must be non-negative");
        }
        if (count == Infinity) {
            throw new RangeError("repeat count must be less than infinity");
        }
        count = Math.floor(count);
        if (str.length == 0 || count == 0) {
            return "";
        }
        // Ensuring count is a 31-bit integer allows us to heavily optimize the
        // main part. But anyway, most current (August 2014) browsers can"t handle
        // strings 1 << 28 chars or longer, so:
        if (str.length * count >= 1 << 28) {
            throw new RangeError("repeat count must not overflow maximum string size");
        }
        var rpt = "";
        for (;;) {
            if ((count & 1) == 1) {
                rpt += str;
            }
            count >>>= 1;
            if (count == 0) {
                break;
            }
            str += str;
        }
        // Could we try:
        // return Array(count + 1).join(this);
        return rpt;
    }
}

function leftpad(str, len, ch){
    str = "" + str;
    if (!ch && ch !== 0) ch = " ";
    var padlen = len - str.length;
    if(padlen <= 0){
        return str;
    }else{
        return ch.repeat(padlen) + str;
    }
}

function safeHTML(st) {
    // encode - same as 'u:htmlencode' tag
    if (st.length == 0) return "";
    st = st.replace(/</gi, "&lt;");
    st = st.replace(/>/gi, "&gt;");
    st = st.replace(/\"/gi, '&quot;');
    st = st.replace(/\'/gi, "&#39;");
    st = st.replace(/\\/gi, "&#92;");
    return st;
}

function unsafeHTML(st) {
    // decode - opposite of 'u:htmlencode' tag
    if (st.length == 0) return "";
    st = st.replace(/&lt;/gi, "<");
    st = st.replace(/&gt;/gi, ">");
    st = st.replace(/&quot;/gi, '"');
    st = st.replace(/&#39;/gi, "'");
    st = st.replace(/&#92;/gi, "\\");
    return st;
}
/*
 * Date Format 1.2.3
 * (c) 2007-2009 Steven Levithan <stevenlevithan.com>
 * MIT license
 *
 * Includes enhancements by Scott Trenda <scott.trenda.net>
 * and Kris Kowal <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask.
 * Returns a formatted version of the given date.
 * The date defaults to the current date/time.
 * The mask defaults to dateFormat.masks.default.
 */

var dateFormat = function () {
    var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
        timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
        timezoneClip = /[^-+\dA-Z]/g,
        pad = function (val, len) {
            val = String(val);
            len = len || 2;
            while (val.length < len) val = "0" + val;
            return val;
        };

    // Regexes and supporting functions are cached through closure
    return function (date, mask, utc) {
        var dF = dateFormat;

        // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
        if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
            mask = date;
            date = undefined;
        }

        // Passing date through Date applies Date.parse, if necessary
        date = date ? new Date(date) : new Date;
        if (isNaN(date)) throw SyntaxError("invalid date");

        mask = String(dF.masks[mask] || mask || dF.masks["default"]);

        // Allow setting the utc argument via the mask
        if (mask.slice(0, 4) == "UTC:") {
            mask = mask.slice(4);
            utc = true;
        }

        var _ = utc ? "getUTC" : "get",
            d = date[_ + "Date"](),
            D = date[_ + "Day"](),
            m = date[_ + "Month"](),
            y = date[_ + "FullYear"](),
            H = date[_ + "Hours"](),
            M = date[_ + "Minutes"](),
            s = date[_ + "Seconds"](),
            L = date[_ + "Milliseconds"](),
            o = utc ? 0 : date.getTimezoneOffset(),
            flags = {
                d: d,
                dd: pad(d),
                ddd: dF.i18n.dayNames[D],
                dddd: dF.i18n.dayNames[D + 7],
                m: m + 1,
                mm: pad(m + 1),
                mmm: dF.i18n.monthNames[m],
                mmmm: dF.i18n.monthNames[m + 12],
                yy: String(y).slice(2),
                yyyy: y,
                h: H % 12 || 12,
                hh: pad(H % 12 || 12),
                H: H,
                HH: pad(H),
                M: M,
                MM: pad(M),
                s: s,
                ss: pad(s),
                l: pad(L, 3),
                L: pad(L > 99 ? Math.round(L / 10) : L),
                t: H < 12 ? "a" : "p",
                tt: H < 12 ? "am" : "pm",
                T: H < 12 ? "A" : "P",
                TT: H < 12 ? "AM" : "PM",
                Z: utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                o: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                S: ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
            };

        return mask.replace(token, function ($0) {
            return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
        });
    };
}();

// Some common format strings
dateFormat.masks = {
    "default": "ddd mmm dd yyyy HH:MM:ss",
    shortDate: "m/d/yy",
    mediumDate: "mmm d, yyyy",
    longDate: "mmmm d, yyyy",
    fullDate: "dddd, mmmm d, yyyy",
    shortTime: "h:MM TT",
    mediumTime: "h:MM:ss TT",
    longTime: "h:MM:ss TT Z",
    isoDate: "yyyy-mm-dd",
    isoTime: "HH:MM:ss",
    isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
    isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
    dayNames: [
        "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    ],
    monthNames: [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    ]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
    return dateFormat(this, mask, utc);
};

//取得URL中的参数值，strParam - 参数名
function request(strParam) {
    var args = new Object();
    var query = location.search.substring(1);

    var pairs = query.split("&"); // Break at ampersand
    for (var i = 0; i < pairs.length; i++) {
        var pos = pairs[i].indexOf('=');
        if (pos == -1) continue;
        var argname = pairs[i].substring(0, pos);
        var value = pairs[i].substring(pos + 1);
        value = decodeURIComponent(value);
        args[argname] = value;
    }
    return args[strParam];
}

/*------------------- JS精确计算函数 ---------------------------*/
/**
 * 浮点数相加
 * @param arg1
 * @param arg2
 */
function dcmAdd(arg1, arg2) {
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (dcmMul(arg1, m) + dcmMul(arg2, m)) / m;
}

/**
 * 浮点数相减
 * @param arg1
 * @param arg2
 */
function dcmSbt(arg1, arg2) {
    return dcmAdd(arg1, -arg2);
}

/**
 * 浮点数相乘
 * @param arg1
 * @param arg2
 */
function dcmMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

/**
 * 浮点数相除
 * @param arg1
 * @param arg2
 */
function dcmDiv(arg1, arg2) {
    return dcmMul(arg1, 1 / arg2);
}

/* ------------  对话框 ------------------*/

artDialog.defaults = {
    // 消息内容
    content: '<div class="d-loading"><span>请稍等..</span></div>',
    // 标题
    title: '对话框',
    // 自定义按钮
    button: null,
    // 确定按钮回调函数
    ok: null,
    // 取消按钮回调函数
    cancel: null,
    // 对话框初始化后执行的函数
    initialize: null,
    // 对话框关闭前执行的函数
    beforeunload: null,
    // 确定按钮文本
    okValue: '确定',
    // 取消按钮文本
    cancelValue: '取消',
    // 内容宽度
    width: 'auto',
    // 内容高度
    height: 'auto',
    // 内容与边界填充距离
    padding: '20px',
    // 皮肤名(多皮肤共存预留接口)
    skin: null,
    // 自动关闭时间(毫秒)
    time: null,
    // 初始化后是否显示对话框
    visible: true,
    // 让对话框跟随某元素
    follow: null,
    // 是否锁屏
    lock: true,
    // 是否固定定位
    fixed: false,
    // 对话框叠加高度值(重要：此值不能超过浏览器最大限制)
    zIndex: 1980

};

function closeDialog(id) {
    $.dialog.get(id).close();
}
//default process
//$(document).ready(function () {
//    //if current page is a dialog iframe, add element named [parentDialogId] to document
//    var parentDialogId = request("parentDialogId");
//    var $id = $('<input id="parentDialogId" type="hidden"/>').val(parentDialogId);
//    $(document.body).append($id);
//});

function closeSelf() {
    parent.closeDialog($("#parentDialogId", parent.document).val());
}

/*
 * 取得事件源
 */
function getEvent() {
    if (document.all)
        return window.event;
    func = getEvent.caller;
    while (func != null) {
        var arg0 = func.arguments[0];
        if (arg0) {
            if (arg0.constructor == MouseEvent) {
                return arg0;
            }
        }
        func = func.caller;
    }
    return null;
}

/**
 * 屏蔽页面中元素ID中的所有Link
 * @param objId 根元素ID
 */
function ShieldingLink(objId) {
    var obj = document.getElementById(objId);
    if (objId) {
        var links = obj.getElementsByTagName("a");
        for (i = 0; i < links.length; i++) {
            links[i].outerHTML = links[i].innerHTML;
            i--;
        }
    }
}

/**
 * 检查是否是数值
 * @param obj 数值对象
 */
function checkNum(obj) {
    return (checkNumAndDelNonNum(obj, false));
}

/**
 * 检查是否是数值，如果不是，将最后一个非数值字符自动删除
 * @param obj 数值对象
 * @param isDel 是否删除最后一个非数值字符
 * @parm digitNum 保存几位小数
 */
function checkNumAndDelNonNum(obj, isDel, digitNum) {
    var objVal = obj.value;
    var chkRst = /^[+]?\d*((\.\d{0,}$)|\d$|$)/.test(objVal);
    if (isDel) {
        if (!chkRst) {
            if (objVal.length > 0) {
                obj.value = objVal.substring(0, objVal.length - 1);
            }
        } else {
            var pos = obj.value.indexOf(".");
            if (digitNum)
                if (pos > 0) {
                    obj.value = obj.value.substring(0, pos + 1) + obj.value.substring(pos + 1, pos + 1 + digitNum);
                }
        }
    }
    return chkRst;
}

function stop() {
    return false;
}
// Disable F5 key
function keyDownHandler(e) {
    if (e) {
        var eCode = e.which;
        if (eCode == 116) {
            e.preventDefault();
            e.stopPropagation();
        }
    } else { // IE
        var eCode = window.event.keyCode;
        if (eCode == 116) {
            window.event.keyCode = 0;
            // nothing to input
            window.event.returnValue = false;
        }
    }
}

function setSuccessMsg(msg) {
    $('#successMessageContent').text(msg).parent().show().delay(2000).hide(0);
}
function setErrorMsg(msg) {
    $('#errorMessageContent').html(msg)
        .parent().show().delay(5000).hide(0);
}
function closeMsg(o) {
    $(o).parent("div").hide();
}

if ($.validator) {
    $.validator.setDefaults({
        errorClass: "help-block",
        errorElement: "span",
        // errorContainer: "#messageBox",
        highlight: function (element, errorClass, validClass) {
            $(element).parents('.form-group').addClass('has-error');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents('.form-group').removeClass('has-error');
        },
        errorPlacement: function (error, element) {
            var $element = $(element);
            if ($element.is(':radio') || $element.is(':checkbox')) {
                $element.parent().parent().append(error);
            } else if ($element.closest('.input-group').length > 0) {
                $element.closest('.input-group').parent().append(error);
            } else {
                $element.parent().append(error);
            }
        }
    });
    $.validator.addMethod("nameCheck", function (value, element) {
        var reg = /^[A-Za-z0-9~!@#$%^&*()_+`\-=:";',.?|]+$/;
        return this.optional(element) || (reg.test(value));
    }, 'Named format error');
    //手机号格式验证
    $.validator.addMethod("mobile", function (value, element) {
        var reg = /^1\d{10}$/;
        return this.optional(element) || (reg.test(value));
    }, '手机号格式不正确');
    //身份证号格式验证
    $.validator.addMethod("identity", function (value, element) {
        return this.optional(element) || isIdCardNo(value);
    }, "身份证号码格式不正确");
}

//增加身份证验证
function isIdCardNo(num) {
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }
    // check and set value
    for (i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i] * factorArr[i];
        }
    }

    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6, 14);
        if (isDate8(date8) == false) {
            return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
            lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] != intCheckDigit) {
            return false;
        }
    }
    else {        //length is 15
        //check date
        var date6 = idNumber.substring(6, 12);
        if (isDate6(date6) == false) {
            return false;
        }
    }
    return true;
}

function isDate6(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    if (year < 1700 || year > 2500) return false
    return !(month < 1 || month > 12);

}

function isDate8(sDate) {
    if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false
    return !(day < 1 || day > iaMonthDays[month - 1]);

}

function to_menu_page(url) {
    location.replace(ctx + url);
}

function to_page(url, callback, data) {

    $("#wrapper").load(url, data, function (responseText) {
        if (responseText.trim().indexOf('{') === 0) {
            try {
                var response = $.parseJSON(responseText);
                $(this).html("");
                checkAjaxResponse(response);
            } catch (err) {
            }
        } else {
            var pageContentMinHeight = jQuery('#wrapper .row').height()  + jQuery('#wrapper .page-heading').height() + jQuery('footer').height();

            if (pageContentMinHeight < mainContentMinHeight) {
                pageContentMinHeight = mainContentMinHeight;
            }
            jQuery('#wrapper').css({"min-height": pageContentMinHeight});

            if (callback) {
                callback();
            }
        }
    });
    return false;
}

function checkAjaxResponse(data, contextPath, preRedirect, callback) {
    if (data.resultStatus) {
        if (data.resultStatus.code === 302) {
            if (preRedirect) {
                preRedirect(data, data.resultStatus.code);
            }
            if (data.resultStatus.redirect.indexOf('http') == 0) {
                to_page(data.resultStatus.redirect, callback);
            } else if (data.resultStatus.redirect.indexOf('/') == 0) {
                to_page(contextPath + data.resultStatus.redirect, callback);
            } else {
                setErrorMsg('重定向错误');
            }
            return false;
        } else if (data.resultStatus.code === 401) {
            to_page(data.resultStatus.redirect);
            return false;
        } else {
            if (callback) {
                callback();
            }
            return true;
        }
    }
    return true;
}

function ajax_submit($form, contextPath, success, error, complete, preRedirect) {
    $form.ajaxSubmit({
        success: function (data, status, xhr) {
            checkAjaxResponse(data, contextPath, preRedirect, function() {
                    success(data, status, xhr);
                });
        },
        error: error,
        complete: complete
    })
}

$(function(){

    $.ajaxSetup ({
        cache: false //close AJAX cache
    });

    var activeMenuId = $('#wrapper .breadcrumb .active').attr("id");
    $("#accordion ." + activeMenuId).addClass("active")
        .parent().parent().addClass("nav-active")
        .siblings().removeClass("nav-active")
        .find("li.active").removeClass("active")
    ;

})