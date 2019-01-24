

/**iphone jsbridge js引入*/
if (window.navigator.userAgent.toLowerCase().search("wcgapple") != -1) {
    // 这段代码是固定的，必须要放到js中
    window.setupWebViewJavascriptBridge = function (callback) {
        if (window.WebViewJavascriptBridge) {
            return callback(WebViewJavascriptBridge);
        }
        if (window.WVJBCallbacks) {
            return window.WVJBCallbacks.push(callback);
        }
        window.WVJBCallbacks = [callback];
        var WVJBIframe = document.createElement('iframe');
        WVJBIframe.style.display = 'none';
        WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
        document.documentElement.appendChild(WVJBIframe);
        setTimeout(function () {
            document.documentElement.removeChild(WVJBIframe)
        }, 0)
    }
}

//安卓初始化
if (navigator.userAgent.toLowerCase().match("wcgandroid")) {
    // 解决注入时机问题：使用注入成功的回调。 同时在注册成功后执行初始化调用(JS Call Native)
    function connectWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge)
        } else {
            document.addEventListener(
                'WebViewJavascriptBridgeReady'
                , function () {
                    callback(WebViewJavascriptBridge)
                },
                false
            );
        }
    }
}

