
本项目用于存放各种 第三方库 和 可复用的功能模块

1. 第三方库以 Module 形式封装，每个 Module 中需要维护 `README.md` 文件。
2. `README.md` 文件需要包含以下内容：Module 介绍，使用和集成方法，已知 bug ，历史更新记录，proguard rule
3. 主工程中写测试代码
4. 命名规则：moduleName + lib
5. 包名：com.passon.xxxx


# 注意

manifest merge fail 错误修复：

```
<uses-sdk tools:overrideLibrary="com.lhjx.imagelib, com.lhjx.analysislib, com.lhjx.crashcollection, com.lhjx.webviewlib"/>
```


# 日志库 LogLib

日志库基于 Logger 库封装



