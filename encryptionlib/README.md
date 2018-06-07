

加密库

使用 aes 加密字符串

# 集成与使用

调用 AESCryptor 类中的 crypt 实现加解密

# 测试代码

```Java
        String TESTDATA = "njfea--test";
        try {
            System.out.println("======jni-crypt-test======");
            byte[] data = TESTDATA.getBytes("UTF-8");
            data = AESCryptor.crypt(data, System.currentTimeMillis(), 0);
            String hexStr = AESCryptor.bytes2HexStr(data);
            System.out.println("encrypt:" + hexStr);

            data = AESCryptor.hexStr2Bytes(hexStr);
            data = AESCryptor.crypt(data, System.currentTimeMillis(), 1);
            System.out.println("decrypt:" + new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
```

```Java
    /**
     * 加密后的字符串做一次 Base64
     */
    private void oneBase64() {
        String TESTDATA = "njfea--test";
        try {
            System.out.println("======jni-crypt-test======");
            byte[] data = TESTDATA.getBytes("UTF-8");
            data = AESCryptor.crypt(data, System.currentTimeMillis(), 0);
            String hexStr = AESCryptor.bytes2HexStr(data);
            System.out.println("encrypt:" + hexStr);

            // base64 encode client
            String encode = Base64.encodeToString(hexStr.getBytes("UTF-8"), Base64.NO_WRAP);
            // base64 decode server
            byte[] decode = Base64.decode(encode.getBytes("UTF-8"), Base64.NO_WRAP);
            String serverStr = new String(decode, "UTF-8");

            data = AESCryptor.hexStr2Bytes(serverStr);
            data = AESCryptor.crypt(data, System.currentTimeMillis(), 1);

            System.out.println("decrypt:" + new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
```