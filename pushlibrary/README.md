
# 推送 Library


# 信鸽 推送

api 'com.tencent.xinge:xinge:3.2.2-release'
api 'com.tencent.wup:wup:1.0.0.E-release'
api 'com.tencent.mid:mid:4.0.6-release'


## 使用和集成方式

'app/build.gradle'

```
defaultConfig{
        manifestPlaceholders = [
                XG_ACCESS_ID : "2100282816",
                XG_ACCESS_KEY: "A7F7NC88XM2A",
        ]
}
```

初始化：

```
    private void initPush() {
        XGPushConfig.enableDebug(this, BuildConfig.LOG_DEBUG);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                LoggerUtils.d(AppConstants.Tag.PUSH, "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                LoggerUtils.d(AppConstants.Tag.PUSH, "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
    }
```

# 问题记录

小米厂商推送实时性不好。



# Proguard

```
# xg
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep class com.tencent.android.tpush.** {* ;}
-keep class com.tencent.mid.** {* ;}
-keep class com.qq.taf.jce.** {*;}

# 华为推送混淆
-ignorewarning
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}


# meizu
-dontwarn com.meizu.cloud.pushsdk.**
-keep class com.meizu.cloud.pushsdk.**{*;}

# xiao mi
-keepclasseswithmembernames class com.xiaomi.**{*;}
-keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver
```
