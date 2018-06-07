#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_lhjx_encryption_encryptionlib_AESCryptor_getKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

// com.lhjx.encryption.encryptionlib
// com.lhjx.encryption.encrptionlib

