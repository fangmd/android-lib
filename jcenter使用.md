
# 资料

https://github.com/bintray/bintray-examples/blob/master/gradle-bintray-plugin-examples/android-gradle-3.0.0-maven-example/gradle.properties

https://github.com/bintray/gradle-bintray-plugin


# 手动上传模式

本地生成 zip 文件，手动上传到 jcenter 网站。

生成 zip 文件：

```
./gradlew clean build generateRelease
```


# 自动上传模式 （未调通）


```
# 编译代码代码
./gradlew build bintrayUpload --info
```
