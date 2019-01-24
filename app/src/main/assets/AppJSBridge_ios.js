(function () {

    if (window.JSNative) {
        return;
    }

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

        function setupWebViewJavascriptBridge(callback) {
            if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
            if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
            window.WVJBCallbacks = [callback];
            var WVJBIframe = document.createElement('iframe');
            WVJBIframe.style.display = 'none';
            WVJBIframe.src = 'https://__bridge_loaded__';
            document.documentElement.appendChild(WVJBIframe);
            setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
        }


    function iosRegister(funcName, callback) {
        window.setupWebViewJavascriptBridge(function (bridge) {
            bridge.registerHandler(funcName, callback);
        })
    }

    function iosCallback(funName, routeMeta, callback) {
        if (window["setupWebViewJavascriptBridge"]) {
            window["setupWebViewJavascriptBridge"](function (bridge) {
                bridge.callHandler(
                    funName
                    , routeMeta
                    , function responseCallback(responseData) {
                        if (responseData != null && responseData != "" && responseData != undefined && typeof callback === "function") {
                            callback(responseData);
                        }
                    });
            })
        }
    };

    var JSNative = window.JSNative = {
        NativePageRegister: iosRegister,
        NativePageController: iosCallback,
    };

    //通知网页注入成功，必须放在最后
    var doc = document;
    _createQueueReadyIframe(doc);
    var readyEvent = doc.createEvent('Events');
    readyEvent.initEvent('WebViewJavascriptBridgeReady');
    readyEvent.bridge = WebViewJavascriptBridge;
    doc.dispatchEvent(readyEvent);

})();