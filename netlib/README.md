
网路请求基础库

# 集成和使用

需要在 `Application` 中初始化：

```java
NetManager.init(this, "base url");
```

1. 如果需要对请求结果做同一处理，可以自己实现一个 `BaseNetObserver`

```java
public abstract class BaseNetObserver<T extends BaseResp> extends DisposableObserver<T> {
    // onNext
}
```


例子：

```
RxService.createApi(CommonService.class).login();
```



TODO: proguard-rule, 接口设计(data 对象和数组)