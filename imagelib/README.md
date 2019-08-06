
# ChangLog

## 1.0.4

1. 增加 加载圆角图片的时候设置站位图

## 1.0.3

1. 增加 load Drawable 资源函数
2. 增加 load File 资源函数

## 1.0.2

1. upgrade glide 4.6.1 -> 4.8.0
2. upgrade glide-transform 3.1.1 -> 3.3.0
3. 修改 缓存存储位置

## 1.0.1


# Glide 简单封装


>加载模式默认：fitXY, 需要配合 ImageView 属性 android:scaleType="fitXY" 使用

图片缓存地址：

```
sdcard/imgCache

或

sdcard/Android/data/<application package>/cache/imgCache
```


加载图片：

```
ImageManager.load(...)
```

加载圆形图：

```
ImageManager.loadCircle(..)
```

加载缩略图：

```
ImageManager.loadThumbnail(..)
```

加载图片获取加载进度：

```
ImageManager.loadWithProgress(..)
```

下载图片：

```
ImageManager.downloadImage()
```

清除缓存：

```
ImageManager.clearCache()
```

# 图片缓存地址

没有外部存储卡读写权限情况下：优先存储在应用外部文件夹，其次存储在应用内部文件夹

有外部存储卡读写权限下：存储在外部地址

# proguard

```
# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
```