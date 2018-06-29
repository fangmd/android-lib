

# changelog

## 1.0.0 封版

旺财谷5.5 中使用：

```
api 'com.umeng.sdk:common:1.5.1'
api 'com.umeng.sdk:analytics:7.5.0'
api 'com.umeng.sdk:utdid:1.1.5.3'
debugApi 'com.umeng.sdk:debug:1.0.0'
```




# umeng 统计

## 集成和使用

初始化： 在 Application 中

```
AnalysisUtils.initUMeng(context, false, appKey, channel);
```

`BaseActivity`

```
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
```

## proguard

```
# umeng analysis
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class [您的应用包名].R$*{
public static final int *;
}
```




