!function (window) {

    if (window.NativePageController) {
        return;
    }



    function testFunc(testFromWeb) {
        console.log(testFromWeb)
    }

    var testobj2 = window.testobj2 = {
        testFunc: testFunc
    };

//    window.testobj = {
//        console.log(testFromWeb)
//    }

    var WebViewJavascriptBridge = window.WebViewJavascriptBridge = {

    };

    window.NativePageRegister2 = function (funcName, callback) {

    }



    // 创建队列iframe
//    function _createQueueReadyIframe(doc) {
//        messagingIframe = doc.createElement('iframe');
//        messagingIframe.style.display = 'none';
//        doc.documentElement.appendChild(messagingIframe);
//    }
//
//    var doc = document;
//    _createQueueReadyIframe(doc);
//    var readyEvent = doc.createEvent('Events');
//    readyEvent.initEvent('JSBridgeInitReady');
//    readyEvent.bridge = testobj2;
//    doc.dispatchEvent(readyEvent);

}(window);