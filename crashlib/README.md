
应用错误日志搜集库

fabric

# 集成与使用

`build.gradle`:

```groovy
buildscript {

    repositories {
        //...
        maven { url 'https://maven.fabric.io/public' }
    }
    dependencies {
        //...
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

```
CrashUtils.init(this);


```