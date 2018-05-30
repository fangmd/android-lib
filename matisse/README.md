# 知乎 mastisse 图片选择器

[mastisse](https://github.com/zhihu/Matisse)

基于 0.5.0-beta3 修改

改动： 增加 中融信 UI 设计，去除 picasso，更新 glide

带有 ZRX 字样的相关文件为 中融信定制UI。

## 使用

注意：使用前确认权限：Manifest.permission.WRITE_EXTERNAL_STORAGE

```
new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    .subscribe(new ErrorHandleObserver<Boolean>() {
        @Override
        public void onNext(Boolean aBoolean) {
            if (aBoolean) {
                SelectorUtils.getImg(MainActivity.this, "com.zrx.app.fileprovider", 12);
            } else {
                ToastUtils.shortShow(getString(R.string.permission_request_denied_sdcard));
            }
        }
    });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK) {
            List<Uri> uris = SelectorUtils.obtainResult(data);
            Log.d(TAG, "onActivityResult: " + uris.size());
            List<String> strings = SelectorUtils.obtainPathResult(data);
            Log.d(TAG, "onActivityResult: " + strings.size());
        }
    }
```



## 代码混淆

-dontwarn com.squareup.picasso.**
-dontwarn com.squareup.okhttp.**