
应用错误日志搜集库

fabric

# changelog

## 1.0.0 封版

旺财谷5.5 中使用：

```
api('com.crashlytics.sdk.android:crashlytics:2.9.4@aar') {
    transitive = true
}
classpath 'io.fabric.tools:gradle:1.25.4'
```

##



# 集成与使用

`build.gradle`:

```groovy
buildscript {

    repositories {
        //...
        maven { url 'https://maven.fabric.io/public' }
    }
    dependencies {
        //通过下方地址查看最新版本号
        //https://s3.amazonaws.com/fabric-artifacts/public/io/fabric/tools/gradle/maven-metadata.xml
        classpath 'io.fabric.tools:gradle:1.25.4'

    }
}

allprojects {
    repositories {
        //...
        maven { url 'https://maven.fabric.io/public' }
    }
}
```

`AndroidManifest.xml`

```
<meta-data
            android:name="io.fabric.ApiKey"
            android:value="d0df73d15725d3055fc73c199f8c056f1c239f92" />
```

```
CrashUtils.init(this);
// 设置用户信息
CrashUtils.logUser(userId, userEmail, userName);
```

# ---




