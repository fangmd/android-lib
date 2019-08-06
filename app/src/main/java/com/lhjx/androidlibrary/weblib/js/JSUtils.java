package com.lhjx.androidlibrary.weblib.js;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.lhjx.androidlibrary.R;
import com.lhjx.androidlibrary.TagConstants;
import com.lhjx.androidlibrary.utils.ToastUtils;
import com.passon.loglib.LoggerUtils;
import com.passon.webviewlib.jsbridge.BridgeHandler;
import com.passon.webviewlib.jsbridge.BridgeWebView;
import com.passon.webviewlib.jsbridge.CallBackFunction;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Author: Created by fangmingdong on 2018/7/9-上午10:02
 * Description:
 */
public class JSUtils {

    public static void setUpWebView(BridgeWebView webView, final JSCallBack jsCallBack) {
        setTitle(webView, jsCallBack);
        getTokens(webView, jsCallBack);
        share(webView, jsCallBack);
        chat(webView, jsCallBack);
        call(webView, jsCallBack);
        jump(webView, jsCallBack);
        rightButton(webView, jsCallBack);
        navLeftType(webView, jsCallBack);
        goBackFunc(webView, jsCallBack);
        openNewWebView(webView, jsCallBack);
        refresh(webView, jsCallBack);
        eventAction(webView,jsCallBack);
        copy(webView, jsCallBack);

    }

    private static void eventAction(BridgeWebView webView, JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.eventAction, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                jsCallBack.eventAction(data);
            }
        });
    }

    private static void openNewWebView(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.openNewWebView, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    jsCallBack.openNewWebView(object.optString("openUrl"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void goBackFunc(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.goBackFunc, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    jsCallBack.goBackFunc(object.optInt("backIndex"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void navLeftType(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.navLeftType, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LoggerUtils.d("WebView_JS", data);
                jsCallBack.navLeftType(data);
            }
        });
    }


    private static void rightButton(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.rightContent, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!TextUtils.isEmpty(data)) {
                    jsCallBack.rightButton(data);
                }
            }
        });
    }


    private static void jump(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.jump, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject object = new JSONObject(data);
                    jsCallBack.jump(object.optString("jumpUrl"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static void call(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.call, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    jsCallBack.call(jsonObject.optString("tel"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static void chat(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.chart, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                jsCallBack.chat();
            }
        });
        webView.registerHandler(JsConstants.chat, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                jsCallBack.chat();
            }
        });
    }

    private static void share(final BridgeWebView webView, final JSCallBack jsCallBack) {

        webView.registerHandler(JsConstants.share, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!TextUtils.isEmpty(data)) {
                    jsCallBack.share(data);
                }
            }
        });
    }

    private static void getTokens(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.get_tokens, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String token = jsCallBack.getToken();
                function.onCallBack(token);
            }
        });
    }

    private static void setTitle(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.SET_TITLE, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LoggerUtils.d("set nav title: " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    jsCallBack.setTitle(jsonObject.optString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoggerUtils.e(e);
                }
            }
        });
    }

    private static void refresh(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.RELOAD, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                jsCallBack.reload();
            }
        });
    }


    private static void copy(BridgeWebView webView, final JSCallBack jsCallBack) {
        webView.registerHandler(JsConstants.COPY_CONTENT, (data, function) -> {
            LoggerUtils.d(TagConstants.WebView + data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                String content = jsonObject.optString("copyString");
                if (!TextUtils.isEmpty(content)) {
                    ClipboardManager clipboard = (ClipboardManager) webView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("bank info", content);
                    clipboard.setPrimaryClip(clip);

                    ToastUtils.show(webView.getContext(), webView.getContext().getString(R.string.copy_success));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public interface JSCallBack {

        void setTitle(String title);

        String getToken();

        void share(String content);

        void chat();

        void call(String tel);

        void jump(String jump);

        void rightButton(String content);

        void navLeftType(String data);

        void goBackFunc(int backIndex);

        void openNewWebView(String openUrl);

        void reload();

        void eventAction(String content);



    }

}
