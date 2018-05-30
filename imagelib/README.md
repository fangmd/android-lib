

# Glide 简单封装

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