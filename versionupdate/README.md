
# 更新库

实际上是一个下载库，并自动启动安装 apk 流程

## 例子

```java
        UpdateManager instance = UpdateManager.getInstance();
        DownloadBuilder builder = instance.createBuilder();

        builder.setContext(mBaseActivity)
                .setVersionCode(NumberConvert.parseInt(mUpdateAppResp.version_code))
                .setDownloadUrl(mUpdateAppResp.downloadurl)
                .setDownloadFileName("biyouche")
                .setAppName("必优车")
                .setAppLogoResource(R.mipmap.ic_launcher)
                .setDownloadListener(new DownloadListener() {
                    @Override
                    public void onDownloadStart() {
                        mUpdateDialog.showDownload();
                        LoggerUtils.d(Constants.Tag.UPDATE, "onDownloadStart");
                    }

                    @Override
                    public void onDownloadFail(String s) {
                        mUpdateDialog.downloadFail();
                        LoggerUtils.d(Constants.Tag.UPDATE, "onDownloadFail");
                    }

                    @Override
                    public void onProgressChange(int i) {
                        LoggerUtils.d(Constants.Tag.UPDATE, "onProgressChange");
                        mUpdateDialog.setProgress(i);
                    }

                    @Override
                    public void onDownloadCancel() {
                        mUpdateDialog.downloadFail();
                        LoggerUtils.d(Constants.Tag.UPDATE, "onDownloadCancel");
                    }

                    @Override
                    public void onDownloadFinish() {
                        mUpdateDialog.downloadFinish();
                        LoggerUtils.d(Constants.Tag.UPDATE, "onDownloadFinish");
                    }
                }).build();
```

下载后文件位置：`Android/data/<package name>/cache/<setDownloadFileName>/update/<setDownloadFileName>`