
本项目用于存放各种 第三方库 和 可复用的功能模块

1. 第三方库以 Module 形式封装，每个 Module 中需要维护 `README.md` 文件。
2. `README.md` 文件需要包含以下内容：Module 介绍，使用和集成方法，已知 bug ，历史更新记录，proguard rule
3. 主工程中写测试代码
4. 命名规则：moduleName + lib
5. 包名：com.lhjx.xxxx

>下面列举了最近工作涉及到的库，其他的大家可以补充

# 注意

manifest merge fail 错误修复：

```
<uses-sdk tools:overrideLibrary="com.lhjx.imagelib, com.lhjx.analysislib, com.lhjx.crashcollection, com.lhjx.webviewlib"/>
```


# release

文件夹存放历史版本包。

>这里保存的版本代码都是已经在项目中使用的。


# PushLibrary

介绍：推送库

相关项目：温度金服（已集成，未上线）

>备注：魅族渠道待测试

# CrashCollection

介绍：Fabric 崩溃收集

相关项目：旺财谷5.5

# OpenSourceLib

介绍：存放不需要封装的公共第三方库


# 图片视频选择库

介绍：基于知乎图库的图片视频选择库

# X5WebView + JsBridge 库

介绍：腾讯 X5WebView 和 JsBridge 库结合

>JSBridge github 上反馈有很多bug

相关项目：旺财谷5.5

# 图片加载库

介绍：基于 glide 的图片加载库

相关项目：旺财谷5.5

# 分享库

友盟分享

# 网络库

介绍：RxJava + Retrofit + OkHttp


# 统计库 AnalysisLib

umeng 统计

>待处理：ga, TalkingData

相关项目：旺财谷5.5

# 加密库

介绍：文件，字符串 加密库

>目前只支持字符串加解密


# 应用更新库



# 地址选择库 address_selector

>需要优化：增加 可以设置层级的设置


相关项目：旺财谷5.5

# 日志库 LogLib

日志库基于 Logger 库封装

